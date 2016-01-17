    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package com.yoctopuce.examples;

    import com.yoctopuce.YoctoAPI.*;

    import javax.websocket.Session;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;

    public class WebSockRSSReader implements Runnable
    {

        private static final SimpleDateFormat ft = new SimpleDateFormat("d MMM");

        private YAPIContext _yctx;
        private final Session _session;

        private RSSReader _rssReader;
        private ArrayList<String> _rssFeeds = new ArrayList<>();
        private ArrayList<ArrayList<RSSItem>> _rssFeedsCache;
        private long _rssCacheExpiration = 0;
        private int _feedIndex;
        private int _itemIndex;
        private YDisplay _display;

        public WebSockRSSReader(Session session)
        {
            _session = session;
            _rssReader = new RSSReader();
            _rssFeeds.add("http://www.yoctopuce.com/EN/rss.xml");
            _rssFeeds.add("http://rss.slashdot.org/Slashdot/slashdotMain");
            _feedIndex = 0;
            _itemIndex = 0;

        }


        private void refreshDisplay()
        {
            checkForNewArticles();
            try {
                if (_feedIndex >= _rssFeedsCache.size()) {
                    _feedIndex = 0;
                }
                ArrayList<RSSItem> feedItems = _rssFeedsCache.get(_feedIndex);
                if (_itemIndex >= feedItems.size()) {
                    _itemIndex = 0;
                }

                RSSItem item = feedItems.get(_itemIndex);
                if (item == null) {
                    return;
                }
                System.out.println("Display :" + item.getTitle());
                int w = _display.get_displayWidth();
                int h = _display.get_displayHeight();
                YDisplayLayer repaintLayer = _display.get_displayLayer(1);
                repaintLayer.clear();
                repaintLayer.selectGrayPen(0);
                repaintLayer.drawBar(0, 0, w - 1, h - 1);
                repaintLayer.selectGrayPen(255);
                repaintLayer.selectFont("Small.yfm");
                String title = String.format("%s (%d/%d)", item.getFeed(), _itemIndex + 1, feedItems.size());
                repaintLayer.drawText(0, 0, YDisplayLayer.ALIGN.TOP_LEFT, title);
                String datestr = ft.format(item.getDate());
                repaintLayer.drawText(w - 1, 0, YDisplayLayer.ALIGN.TOP_RIGHT, datestr);
                repaintLayer.moveTo(0, 9);
                repaintLayer.lineTo(w - 1, 9);
                repaintLayer.setConsoleMargins(0, 11, w - 1, h - 1);
                repaintLayer.setConsoleBackground(0);
                repaintLayer.setConsoleWordWrap(true);
                repaintLayer.consoleOut(item.getTitle());
                _display.swapLayerContent(1, 2);
            } catch (YAPI_Exception e) {
                e.printStackTrace();
            }
        }

        private boolean checkForNewArticles()
        {
            if (_rssCacheExpiration < System.currentTimeMillis()) {
                System.out.println("Refresh Feeds");
                // show "Loading..." on YoctoDisplay
                try {
                    int w = _display.get_displayWidth();
                    int h = _display.get_displayHeight();
                    YDisplayLayer repaintLayer = _display.get_displayLayer(1);
                    repaintLayer.clear();
                    repaintLayer.selectGrayPen(0);
                    repaintLayer.drawBar(0, 0, w - 1, h - 1);
                    repaintLayer.selectGrayPen(255);
                    repaintLayer.selectFont("Medium.yfm");
                    repaintLayer.drawText(w / 2, h / 2, YDisplayLayer.ALIGN.CENTER, "Loading...");
                    // put it on top of displayed content
                    _display.swapLayerContent(1, 3);
                } catch (YAPI_Exception e) {
                    e.printStackTrace();
                }
                // refresh RSS cache
                _rssFeedsCache = _rssReader.getAll(_rssFeeds);
                _rssCacheExpiration = System.currentTimeMillis() + 3600000;// 1 hour
                // remove "Loading..."
                try {
                    // clear loading layer
                    YDisplayLayer repaintLayer = _display.get_displayLayer(3);
                    repaintLayer.clear();
                } catch (YAPI_Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        }

        public int debounce(YAnButton button, String value)
        {
            int res = 0;
            Integer currentValue = Integer.valueOf(value);
            Integer lastValue = (Integer) button.get_userData();
            if (lastValue == null) {
                // the very first time the userData is empty
                lastValue = 999;
            }
            if (currentValue < 500 && lastValue >= 500) {
                res = 1;
            } else if (currentValue >= 500 && lastValue < 500) {
                res = -1;
            }
            button.set_userData(currentValue);
            return res;
        }


        private void setupYoctoDisplay() throws YAPI_Exception
        {

            _display = YDisplay.FirstDisplayInContext(_yctx);
            if (_display == null) {
                System.out.println("No display connected");
                return;
            }

            // reset screen
            _display.resetAll();
            YModule module = _display.module();
            String serial = module.get_serialNumber();
            YAnButton left = YAnButton.FindAnButtonInContext(_yctx, serial + ".anButton1");
            left.registerValueCallback(new YAnButton.UpdateCallback()
            {
                public void yNewValue(YAnButton function, String functionValue)
                {
                    if (debounce(function, functionValue) == 1) {
                        if (_itemIndex > 0) {
                            _itemIndex -= 1;
                            refreshDisplay();
                        }
                    }
                }
            });

            YAnButton right = YAnButton.FindAnButtonInContext(_yctx, serial + ".anButton3");
            right.registerValueCallback(new YAnButton.UpdateCallback()
            {
                public void yNewValue(YAnButton function, String functionValue)
                {
                    if (debounce(function, functionValue) == 1) {
                        if (_rssFeedsCache != null && _itemIndex < _rssFeedsCache.get(_feedIndex).size() - 1) {
                            _itemIndex += 1;
                            refreshDisplay();
                        }
                    }
                }
            });

            YAnButton up = YAnButton.FindAnButtonInContext(_yctx, serial + ".anButton2");
            up.registerValueCallback(new YAnButton.UpdateCallback()
            {
                public void yNewValue(YAnButton function, String functionValue)
                {
                    if (debounce(function, functionValue) == 1) {
                        if (_feedIndex > 0) {
                            _feedIndex -= 1;
                            _itemIndex = 0;
                            refreshDisplay();
                        }
                    }
                }
            });

            YAnButton down = YAnButton.FindAnButtonInContext(_yctx, serial + ".anButton4");
            down.registerValueCallback(new YAnButton.UpdateCallback()
            {
                public void yNewValue(YAnButton function, String functionValue)
                {
                    if (debounce(function, functionValue) == 1) {
                        if (_rssFeedsCache != null && _feedIndex < _rssFeedsCache.size() - 1) {
                            _feedIndex += 1;
                            _itemIndex = 0;
                            refreshDisplay();
                        }
                    }
                }
            });
        }

        public void run()
        {
            _yctx = new YAPIContext();
            try {
                _yctx.RegisterHubWebSocketCallback(_session);
                setupYoctoDisplay();
                // run while WebSocket is connected
                while (_session.isOpen()) {
                    if (checkForNewArticles()) {
                        refreshDisplay();
                    }
                    _yctx.Sleep(1000);
                }
            } catch (YAPI_Exception e) {
                e.printStackTrace();
            }
            _yctx.FreeAPI();

        }


    }
