/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.yoctopuce.examples;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author seb
 */
public class RSSItem
{
    private final String _title;
    private final String _link;
    private final Date   _date;
    private final String _description;
    private final String _feed;
    
  

    public RSSItem(String feed, String title, String link, Date date, String description)
    {
        _title = title;
        _link = link;
        _date = date;
        _description = description;
        _feed = feed;
    }

    public String getTitle()
    {
        return _title;
    }

    public String getLink()
    {
        return _link;
    }

    public Date getDate()
    {
        return _date;
    }

    public String getDescription()
    {
        return _description;
    }
    
    public String getFeed()
    {
        return _feed;
    }
   
    
}
