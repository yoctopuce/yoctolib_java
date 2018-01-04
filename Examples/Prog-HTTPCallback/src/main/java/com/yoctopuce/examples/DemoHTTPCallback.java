/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yoctopuce.examples;

import com.yoctopuce.YoctoAPI.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@WebServlet(name = "DemoHTTPCallback", urlPatterns = {"/"})
public class DemoHTTPCallback extends HttpServlet
{

    //nb millisecond for the transition
    private static final int TRANSITION_DURATION = 500;
    //nb millisecond for the transition
    private static final int DISPLAY_DURATION = 20000;

    private static final SimpleDateFormat ft = new SimpleDateFormat("d MMM");

    @Override
    public void init() throws ServletException
    {
        System.out.println("Setup Yoctpuce API to use thread specific cache");
        try {
            YAPI.SetThreadSpecificMode();
        } catch (YAPI_Exception ex) {
            System.out.println("Unable to configure Yoctopuce API:" + ex.getMessage());
        }
    }


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try {
            // Setup the Yoctopuce API in HTTP Callback
            YAPI.RegisterHub("callback",
                    request.getInputStream(),
                    response.getOutputStream());
            doRSSReader();
        } catch (YAPI_Exception ex) {
            System.out.println("Yoctopuce API error:" + ex.getMessage());
        } finally {
            YAPI.FreeAPI();
        }
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        httpServletResponse.setContentType("text/html");
        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter out = httpServletResponse.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\" />");
        out.println("<title>DemoHTTPCallback</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<p>Configure Your YoctoHub or VirtualHub to post on this page.</p>");
        out.println("</body>");
        out.println("</html>");
    }

    private void doRSSReader() throws YAPI_Exception
    {

        ArrayList<RSSItem> rssItems = RSSReader.getInstance().getAllItems();
        // create an array with all connected display
        YDisplay display = YDisplay.FirstDisplay();
        while (display != null) {
            // set the beacon at it max luminosity during
            // sequence recording. Used only for debug purpose
            YModule module = display.module();
            module.set_luminosity(100);
            module.set_beacon(YModule.BEACON_ON);
            // start recording a Sequence
            display.newSequence();
            // execute command for all rss items
            for (RSSItem item : rssItems) {
                String product = module.get_productName();
                if ("Yocto-MaxiDisplay".equals(product)) {
                    OutputMaxiDisplay(display, item);
                } else if ("Yocto-Display".equals(product)) {
                    OutputMaxiDisplay(display, item);
                } else if ("Yocto-MiniDisplay".equals(product)) {
                    OutputMiniDisplay(display, item);
                }
            }
            display.playSequence("rss");
            display.saveSequence("rss");
            display.playSequence("rss");
            // stop the beacon 
            module.set_luminosity(0);
            module.set_beacon(YModule.BEACON_OFF);
            // look if we get another display connected
            display = display.nextDisplay();
        }
    }


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

    private void OutputMaxiDisplay(YDisplay display, RSSItem item) throws YAPI_Exception
    {
        int w = display.get_displayWidth();
        int h = display.get_displayHeight();
        //clear background layer
        YDisplayLayer layer1 = display.get_displayLayer(1);
        layer1.clear();
        //move current layer into the background (layer 2 . layer 1)
        display.swapLayerContent(1, 2);
        // from now work on
        YDisplayLayer layer2 = display.get_displayLayer(2);
        layer2.clear();
        layer2.selectGrayPen(0);
        layer2.drawBar(0, 0, w - 1, h - 1);
        layer2.selectGrayPen(255);
        layer2.setLayerPosition(w, h, 0);
        layer2.selectFont("Small.yfm");
        layer2.drawText(0, 0, YDisplayLayer.ALIGN.TOP_LEFT, item.getFeed());
        String datestr = ft.format(item.getDate());
        layer2.drawText(w - 1, 0, YDisplayLayer.ALIGN.TOP_RIGHT, datestr);
        layer2.moveTo(0, 9);
        layer2.lineTo(w - 1, 9);
        layer2.setConsoleMargins(0, 11, w - 1, h - 1);
        layer2.setConsoleBackground(0);
        layer2.setConsoleWordWrap(true);
        layer2.consoleOut(item.getTitle());
        layer2.setLayerPosition(0, 0, TRANSITION_DURATION);
        display.pauseSequence(TRANSITION_DURATION + DISPLAY_DURATION);
    }

    private void OutputMiniDisplay(YDisplay display, RSSItem item) throws YAPI_Exception
    {
        int w = display.get_displayWidth();
        int h = display.get_displayHeight();
        //clear background layer
        YDisplayLayer layer1 = display.get_displayLayer(1);
        layer1.clear();
        //move current layer into the background (layer 2 . layer 1)
        display.swapLayerContent(1, 2);
        // from now work on
        YDisplayLayer layer2 = display.get_displayLayer(2);
        layer2.clear();
        layer2.selectGrayPen(0);
        layer2.drawBar(0, 0, w - 1, h - 1);
        layer2.selectGrayPen(255);
        layer2.setLayerPosition(w, h, 0);
        layer2.selectFont("Small.yfm");
        layer2.drawText(0, 0, YDisplayLayer.ALIGN.TOP_LEFT, item.getFeed());
        String datestr = ft.format(item.getDate());
        layer2.drawText(w - 1, 0, YDisplayLayer.ALIGN.TOP_RIGHT, datestr);
        layer2.drawText(0, 8, YDisplayLayer.ALIGN.TOP_LEFT, item.getTitle());
        layer2.setLayerPosition(0, 0, TRANSITION_DURATION);
        display.pauseSequence(TRANSITION_DURATION + DISPLAY_DURATION);
    }

}
