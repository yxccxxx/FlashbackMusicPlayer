package tests;

import android.location.Location;
import android.util.Log;

import com.example.jaygandhi.flashbackmusicteam34.Library;
import com.example.jaygandhi.flashbackmusicteam34.MockLocation;
import com.example.jaygandhi.flashbackmusicteam34.MockQueue;
import com.example.jaygandhi.flashbackmusicteam34.PlaybackQueue;
import com.example.jaygandhi.flashbackmusicteam34.Track;
import com.example.jaygandhi.flashbackmusicteam34.VibeQueue;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import static junit.framework.Assert.assertEquals;

/**
 * Created by thinmintz17 on 3/16/18.
 */

public class JUnitTestVibeQueue {

    @Before
    public void setup() {
        // Computer Science basement B260
        MockLocation.latitude = 32.882301;
        MockLocation.longitude = -117.233728;
    }

    // Test that sorting a queue of tracks works
    @Test
    public void test_SortQueue() {
        MockQueue pbq = new MockQueue();

        long count = 0;
        for (int i = 0; i < 5; ++i) {
            Track t = new Track(1);
            t.setTrackScore(1);
            t.setLastEpochMillis(count);
            t.setTrackName("Track " + i);

            pbq.addTrack(new Track(1));
            count += 1;
        }

        ArrayList<Track> oldQueue = pbq.getQueue();
        Collections.sort(pbq.getQueue());
        ArrayList<Track> newQueue = pbq.getQueue();

        for(int i = 0; i < 5; ++i) {
            assertEquals(newQueue.get(i).getTrackName(), oldQueue.get(4-i).getTrackName());
        }
    }

    @Test
    public void test_VibeQueue_Location() {
        for (int i = 0; i < 5; ++i) {
            return;
        }
    }
}
