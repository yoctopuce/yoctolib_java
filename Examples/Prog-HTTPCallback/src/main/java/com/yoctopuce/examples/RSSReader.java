/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.yoctopuce.examples;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author seb
 */
public class RSSReader
{
  

    private static RSSReader instance = null;
    private final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

    private RSSReader()
    {
    }

    public static RSSReader getInstance()
    {
        if (instance == null) {
            instance = new RSSReader();
        }
        return instance;
    }

    public ArrayList<RSSItem> getFeedItems(String feed)
    {
        ArrayList<RSSItem> allItems= new ArrayList<RSSItem>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            URL u = new URL(feed); // your feed url
            Document doc = builder.parse(u.openStream());
            NodeList channel = doc.getElementsByTagName("channel");
            int l= channel.getLength();
            Element el = (Element) channel.item(0);
            String feedname = getElementValue(el, "title");            
            NodeList nodes = doc.getElementsByTagName("item");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                RSSItem item = parseItem(feedname, element);
                allItems.add(item);
            }
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }
        return allItems;               
    }
     
    
      
    RSSItem parseItem(String feedname, Element element)
    {
        String feed = feedname;
        String title = getElementValue(element, "title");
        String link = getElementValue(element, "link");
        String datestr=getElementValue(element, "pubDate");
        Date date;        
        try {
            date = sdf.parse(datestr);
        } catch (ParseException ex) {
            date= new Date();
        }
        String description = getElementValue(element, "description");
        return new RSSItem(feedname, title, link, date, description);
    }
    
    
    private static String getCharacterDataFromElement(Element e)
    {
        try {
            Node child = e.getFirstChild();
            if (child instanceof CharacterData) {
                CharacterData cd = (CharacterData) child;
                return cd.getData();
            }
        } catch (Exception ignore) {}
        return "";
    }

    private static String getElementValue(Element parent, String label)
    {
        return getCharacterDataFromElement((Element) parent.getElementsByTagName(label).item(0));
    }

    
    public ArrayList<RSSItem> getAllItems()
    {
        return getFeedItems("http://www.yoctopuce.com/EN/rss.xml");
    }
}
