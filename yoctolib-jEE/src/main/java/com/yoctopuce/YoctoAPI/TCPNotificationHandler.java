package com.yoctopuce.YoctoAPI;

import java.util.HashMap;
import java.util.Locale;

class TCPNotificationHandler extends NotificationHandler
{
    private volatile boolean _connected = false;
    private volatile boolean _connecting = true;
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


    @Override
    public void run()
    {
        yHTTPRequest yreq = new yHTTPRequest(_hub, "Notification of " + _hub.getRootUrl());
        while (!Thread.currentThread().isInterrupted()) {
            if (_error_delay > 0) {
                try {
                    Thread.sleep(_error_delay);
                } catch (InterruptedException ex) {
                    _connected = false;
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            try {
                yreq._requestReserve();
                String notUrl;
                if (_notifyPos < 0) {
                    notUrl = "GET /not.byn";
                } else {
                    notUrl = String.format(Locale.US, "GET /not.byn?abs=%d", _notifyPos);
                }
                yreq._requestStart(notUrl, null, 0, null, null);
                _connected = true;
                _connecting = false;
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
                            // drop notification that contain esc char
                            handleNetNotification(line);
                        }
                        fifo = fifo.substring(pos + 1);
                    } while (pos >= 0);
                    _error_delay = 0;
                } while (!Thread.currentThread().isInterrupted());
                yreq._requestStop();
                yreq._requestRelease();
            } catch (YAPI_Exception ex) {
                _connected = false;
                yreq._requestStop();
                yreq._requestRelease();
                _notifRetryCount++;
                _hub._isNotifWorking = false;
                _error_delay = 100 << (_notifRetryCount > 4 ? 4 : _notifRetryCount);
            }
        }
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


    public boolean isConnected()
    {
        if (_sendPingNotification) {
            return (_lastPing + NET_HUB_NOT_CONNECTION_TIMEOUT) > System.currentTimeMillis();
        } else {
            return _connecting || _connected;
        }
    }

    @Override
    public boolean hasRwAccess()
    {
        return _hub._runtime_http_params.getUser().equals("admin");
    }
}
