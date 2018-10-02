package tests;

import com.example.jaygandhi.flashbackmusicteam34.Track;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Yixin and Rajit on 2/17/18.
 * Junit test for Track.java
 */

public class JUnitTestTrack {

    // mocking
    private Track track1;
    private Track track2;

    @Before
    public void setUp(){
        track1 = new Track(12345);
        track2 = new Track(23456);
    }

    @Test
    public void test_constructor(){
        assertEquals(12345, track1.getTrackId());
        assertEquals(23456, track2.getTrackId());

        assertEquals(0.0, track1.getTrackScore());
        assertEquals(0.0, track2.getTrackScore());

        assertEquals(0, track1.getUserVote());
        assertEquals(0, track2.getUserVote());

        assertEquals(null, track1.getLastDate());
        assertEquals(null, track2.getLastDate());

        assertEquals(0.00, track1.getLastLongitude());
        assertEquals(0.00, track2.getLastLongitude());

        assertEquals(90.00, track1.getLastLatitude());
        assertEquals(90.00, track2.getLastLatitude());

        assertEquals(false, track1.getHasBeenPlayed());
        assertEquals(false, track2.getHasBeenPlayed());

        assertEquals(0, track1.getEpochMillis());
        assertEquals(0, track2.getEpochMillis());

        assertEquals(-1, track1.getLastDay());
        assertEquals(-1, track2.getLastDay());

        assertEquals(-1, track1.getLastTime());
        assertEquals(-1, track2.getLastTime());

    }

    @Test
    public void test_compareTo(){
        track1.setTrackScore(5);
        track2.setTrackScore(3);
        assertEquals(-1, track1.compareTo(track2));

        track1.setTrackScore(5);
        track2.setTrackScore(8);
        assertEquals(1, track1.compareTo(track2));

        track1.setTrackScore(5);
        track2.setTrackScore(5);
     /*   track1.setUserVote(1);
        track2.setUserVote(0);
        assertEquals( 1, track1.compareTo(track2));

        track1.setUserVote(0);
        track2.setUserVote(1);
        assertEquals(1, track1.compareTo(track2));

        track1.setUserVote(0);
        track2.setUserVote(0);*/

        // it is mocking
        track1.setLastEpochMillis(800);
        track2.setLastEpochMillis(400);
        assertEquals(-1, track1.compareTo(track2));

        track1.setLastEpochMillis(800);
        track2.setLastEpochMillis(1200);
        assertEquals(1, track1.compareTo(track2));
    }
}
