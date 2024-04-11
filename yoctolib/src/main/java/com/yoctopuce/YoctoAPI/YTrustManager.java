package com.yoctopuce.YoctoAPI;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

class YTrustManager implements X509TrustManager
{

    private final ArrayList<X509TrustManager> _defaultTM;
    private X509TrustManager _yoctoTM;
    private final int _flags;

    YTrustManager(KeyStore keyStore, int flags) throws Exception
    {
        TrustManagerFactory tmf = null;
        tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init((KeyStore) null);
        _defaultTM = new ArrayList<>();
        for (TrustManager tm : tmf.getTrustManagers()) {
            if (tm instanceof X509TrustManager) {
                _defaultTM.add((X509TrustManager) tm);
            }
        }
        if (keyStore.size() > 0) {
            _yoctoTM = trustManagerFor(keyStore);
        } else {
            _yoctoTM = null;
        }
        _flags = flags;
    }

    private X509TrustManager trustManagerFor(KeyStore keyStore) throws YAPI_Exception
    {
        TrustManagerFactory tmf = null;
        try {
            tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            throw new YAPI_Exception(YAPI.SSL_ERROR, e.getLocalizedMessage(), e);
        }
        try {
            tmf.init(keyStore);
        } catch (KeyStoreException e) {
            throw new YAPI_Exception(YAPI.SSL_ERROR, e.getLocalizedMessage(), e);
        }
        TrustManager[] trustManagers = tmf.getTrustManagers();
        if (trustManagers.length != 1) {
            throw new YAPI_Exception(YAPI.SSL_ERROR, "Unexpected number of trust managers");
        }
        TrustManager trustManager = trustManagers[0];
        if (trustManager instanceof X509TrustManager) {
            return (X509TrustManager) trustManager;
        }
        throw new YAPI_Exception(YAPI.SSL_ERROR, "not a X509TrustManager");
    }


    private static final Pattern CERT_PATTERN = Pattern.compile(
            "-+BEGIN\\s+.*CERTIFICATE[^-]*-+(?:\\s|\\r|\\n)+" + // Header
                    "([a-z0-9+/=\\r\\n]+)" +                    // Base64 text
                    "-+END\\s+.*CERTIFICATE[^-]*-+",            // Footer
            CASE_INSENSITIVE);

    static ArrayList<X509Certificate> parsePemCert(String pem_str) throws CertificateException
    {
        Matcher matcher = CERT_PATTERN.matcher(pem_str);
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        ArrayList<X509Certificate> certificates = new ArrayList<>();
        int start = 0;
        while (matcher.find(start)) {
            byte[] buffer = YAPI.Base64Decode(matcher.group(1));
            certificates.add((X509Certificate) certificateFactory.generateCertificate(new ByteArrayInputStream(buffer)));
            start = matcher.end();
        }
        return certificates;
    }

    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException
    {
        for (X509TrustManager tm : _defaultTM) {
            try {
                tm.checkClientTrusted(chain, authType);
                return;
            } catch (CertificateException ignored) {
            }

        }
        try {
            if (_yoctoTM != null) {
                _yoctoTM.checkClientTrusted(chain, authType);
            } else {
                throw new CertificateException("None of the TrustManagers trust this certificate chain");
            }
        } catch (CertificateException ex) {
            if ((_flags & YAPI.NO_TRUSTED_CA_CHECK) == 0) {
                throw ex;
            }
        }
    }

    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException
    {

        for (X509TrustManager tm : _defaultTM) {
            try {
                tm.checkServerTrusted(chain, authType);
                return;
            } catch (CertificateException ignored) {
            }
        }

        try {
            if (_yoctoTM != null) {
                _yoctoTM.checkServerTrusted(chain, authType);
            } else {
                throw new CertificateException("None of the TrustManagers trust this certificate chain");
            }
        } catch (CertificateException ex) {
            if ((_flags & YAPI.NO_TRUSTED_CA_CHECK) == 0) {
                throw ex;
            }
        }

    }

    public X509Certificate[] getAcceptedIssuers()
    {
        ArrayList<X509Certificate> allCerts = new ArrayList<>(_defaultTM.size() + 1);
        for (X509TrustManager tm : _defaultTM) {
            allCerts.addAll(Arrays.asList(tm.getAcceptedIssuers()));
        }
        if (_yoctoTM != null) {
            allCerts.addAll(Arrays.asList(_yoctoTM.getAcceptedIssuers()));
        }
        X509Certificate[] res = new X509Certificate[allCerts.size()];
        res = allCerts.toArray(res);
        return res;
    }
}
