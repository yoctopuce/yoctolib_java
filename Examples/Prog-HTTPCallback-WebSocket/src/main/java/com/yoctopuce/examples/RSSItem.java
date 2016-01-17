package com.yoctopuce.examples;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;

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
