package com.example.jaygandhi.flashbackmusicteam34;

import java.io.Serializable;

/**
 * Created by jaygandhi on 3/16/18.
 */

public class MockTime implements iTime, Serializable {
    private long epochMillis;

    public MockTime(long epochMillis) {
        this.epochMillis = epochMillis;
    }

    public void setTimeStamp(long epochTime) {
        epochMillis = epochTime;
    }

    public long getTimeStamp() {
        return epochMillis;
    }
}
