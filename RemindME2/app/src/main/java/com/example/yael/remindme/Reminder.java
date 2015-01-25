package com.example.yael.remindme;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by yael on 12/21/2014.
 */
public class Reminder implements Serializable {

    private String Title;
    private RemindLocationOrTime LocationOrTime;
    private Date Date;
    private Time Time;

    public Reminder(String title, RemindLocationOrTime remindWhenOrWhere, Date date, Time time)
    {
        this.Title = title;
        this.LocationOrTime = remindWhenOrWhere;
        this.Date = date;
        this.Time = time;
    }

    public String GetTiTle()
    {
        return Title;
    }

    public Date GetDate()
    {
        return Date;
    }

    public Time GetTime()
    {
        return Time;
    }

    public RemindLocationOrTime GetRemindLocationOrTime()
    {
        return LocationOrTime;
    }
    public static enum RemindLocationOrTime
    {
        Time,
        Location
    }
}


