package tests;

import com.example.jaygandhi.flashbackmusicteam34.TrackStatus;

import org.junit.Test;

import org.junit.Assert;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Yixin on 2/18/18.
 */

public class JUnitTestTrackStatus {
    @Test
    public void test_neutral(){
        assertEquals(0, TrackStatus.NEUTRAL);
    }

    @Test
    public void test_like(){
        assertEquals(1, TrackStatus.LIKE);
    }

    @Test
    public void test_dislike(){
        assertEquals(-1, TrackStatus.DISLIKE);
    }
}
