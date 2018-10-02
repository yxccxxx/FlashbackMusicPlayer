package tests;

import com.example.jaygandhi.flashbackmusicteam34.TrackRecord;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Yixin on 3/15/18.
 */

public class JUnitTestTrackRecord {
    public TrackRecord trackRecord;

    @Before
    public void setUp(){
        trackRecord = new TrackRecord();
    }

    @Test
    public void test_constructor(){
        assertEquals(null, trackRecord.getResourceID());
        assertEquals(null, trackRecord.getUrl());
        assertEquals(null, trackRecord.getTitle());
        assertEquals(null, trackRecord.getUser());
        assertEquals(0.0, trackRecord.getLatitude());
        assertEquals(0.0, trackRecord.getLongitude());
        assertEquals(0, trackRecord.getTime());
    }
}
