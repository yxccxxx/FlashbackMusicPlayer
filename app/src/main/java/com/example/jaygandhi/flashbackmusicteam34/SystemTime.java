package com.example.jaygandhi.flashbackmusicteam34;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by jaygandhi on 3/16/18.
 */

public class SystemTime implements iTime, Serializable {
    private long epochMillis;
    private Calendar cal;

    public SystemTime() {
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        epochMillis = cal.getTimeInMillis();
    }

    public void setTimeStamp(long epochTime) {
        epochMillis = cal.getTimeInMillis();
    }

    public long getTimeStamp() {
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        epochMillis = cal.getTimeInMillis();
        return epochMillis;
    }
}
