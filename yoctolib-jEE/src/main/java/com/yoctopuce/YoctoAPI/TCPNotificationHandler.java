package com.yoctopuce.YoctoAPI;

import java.util.HashMap;
import java.util.Locale;

class TCPNotificationHandler extends NotificationHandler
{
    private HashMap<YDevice, yHTTPRequest> _httpReqByDev = new HashMap<>();


    TCPNotificationHandler(YHTTPHub hub)
    {
        super(hub);
    }

    @Override
    String getThreadLabel()
    {
        return "TCPNotHandler_" + _hub._runtime_http_params.toString();
    }


    public void run(long expiration)
    {
        int firt_try = 1;
        yHTTPRequest yreq = new yHTTPRequest(_hub, "Notification of " + _hub.getRootUrl());
        while (_hub.workerThreadMustContinue()) {
            if (_error_delay > 0) {
                try {
                    Thread.sleep(_error_delay);
                } catch (InterruptedException ex) {
                    _hub.set_connectionState(YHub.ABORTED);
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            try {
                if (firt_try > 0) {
                    if (System.currentTimeMillis() > expiration) {
                        throw new YAPI_Exception(YAPI.TIMEOUT, "Unable to start the request in time");
                    }
                    _hub.set_connectionState(YHub.TRYING);
                    firt_try = 0;
                } else {
                    _hub.set_connectionState(YHub.RECONNECTING);
                }
                yreq._requestReserve();
                String notUrl;
                if (_notifyPos < 0) {
                    notUrl = "GET /not.byn";
                    _hub.dbglog(4, "request /not.byn");
                } else {
                    notUrl = String.format(Locale.US, "GET /not.byn?abs=%d", _notifyPos);
                    _hub.dbglog(4, String.format("request /not.byn?abs=%d", _notifyPos));
                }
                // no request timeout for notifications
                yreq._requestStart(notUrl, null, 0, null, null);
                _hub.set_connectionState(YHub.CONNECTED);
                String fifo = "";
                do {
                    byte[] partial;
                    try {
                        _hub.testLogPull();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                        throw new YAPI_Exception(e.getLocalizedMessage());
                    }
                    yreq._requestProcesss();
                    partial = yreq.getPartialResult();
                    if (partial != null) {
                        fifo += new String(partial);
                    }
                    int pos;
                    do {
                        pos = fifo.indexOf("\n");
                        if (pos < 0) break;
                        String line = fifo.substring(0, pos + 1);
                        if (line.indexOf(27) == -1) {
                            // drop notifications that contain esc char
                            handleNetNotification(line);
                        }
                        fifo = fifo.substring(pos + 1);
                    } while (pos >= 0);
                    _error_delay = 0;
                } while (_hub.workerThreadMustContinue());
            } catch (YAPI_Exception ex) {
                if (ex.errorType == YAPI.UNAUTHORIZED || ex.errorType == YAPI.SSL_UNK_CERT) {
                    break;
                } else {
                    _hub.set_connectionState(YHub.RECONNECTING);
                }
                _notifRetryCount++;
                _hub._isNotifWorking = false;
                _error_delay = 100 << (_notifRetryCount > 4 ? 4 : _notifRetryCount);
            } finally {
                yreq._requestStop();
                yreq._requestRelease();
            }
        }
        _hub.set_connectionState(YHub.ABORTED);
        yreq._requestStop();
        yreq._requestRelease();
    }


    @Override
    byte[] hubRequestSync(String req_first_line, byte[] req_head_and_body, int mstimeout) throws YAPI_Exception
    {
        yHTTPRequest req = new yHTTPRequest(_hub, "request to " + _hub.getHost());
        return req.RequestSync(req_first_line, req_head_and_body, mstimeout);
    }

    @Override
    byte[] devRequestSync(YDevice device, String req_first_line, byte[] req_head_and_body, int mstimeout, YGenericHub.RequestProgress progress, Object context) throws YAPI_Exception
    {
        if (!_httpReqByDev.containsKey(device)) {
            _httpReqByDev.put(device, new yHTTPRequest(_hub, "Device " + device.getSerialNumber()));
        }
        yHTTPRequest req = _httpReqByDev.get(device);
        return req.RequestSync(req_first_line, req_head_and_body, mstimeout);
    }

    @Override
    void devRequestAsync(YDevice device, String req_first_line, byte[] req_head_and_body, YGenericHub.RequestAsyncResult asyncResult, Object asyncContext) throws YAPI_Exception
    {
        if (!_httpReqByDev.containsKey(device)) {
            _httpReqByDev.put(device, new yHTTPRequest(_hub, "Device " + device.getSerialNumber()));
        }
        yHTTPRequest req = _httpReqByDev.get(device);
        req.RequestAsync(req_first_line, req_head_and_body, asyncResult, asyncContext);
    }

    boolean waitAndFreeAsyncTasks(long timeout) throws InterruptedException
    {
        for (yHTTPRequest req : _httpReqByDev.values()) {
            req.WaitRequestEnd(timeout);
        }
        return false;
    }

    @Override
    void stopSocketsOfThread()
    {

    }

    @Override
    public boolean hasRwAccess()
    {
        return _hub._runtime_http_params.getUser().equals("admin");
    }
}
