package tests;

import android.location.Location;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.example.jaygandhi.flashbackmusicteam34.FlashbackQueue;
import com.example.jaygandhi.flashbackmusicteam34.HomeActivity;
import com.example.jaygandhi.flashbackmusicteam34.Library;
import com.example.jaygandhi.flashbackmusicteam34.Track;
import com.example.jaygandhi.flashbackmusicteam34.TrackListActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.TimeZone;

import static junit.framework.Assert.assertEquals;

/**
 * Created by jaygandhi on 2/17/18.
 * The class FlashbackQueue is not used in milestone 2
 */

public class JUnitTestFlashbackQueue {

   /* private Location mockLoc;
    private static final String TAG = "JUnitTestFlashbackQueue Class";

    @Before
    public void setup() {
        Library.loadSongs();

        // Computer Science basement B260
        mockLoc = new Location("");
        mockLoc.setLatitude(32.882301);
        mockLoc.setLongitude(-117.233728);

        for (Track t : Library.trackList) {
            t.setHasBeenPlayed(true);
        }
    }

    // Test that sorting a queue of tracks works
    @Test
    public void test_SortQueue() {
        double count = 1.00;
        for (int i = 0; i < 5; ++i) {
            Library.trackList.get(i).setTrackScore(count);
            count += 1.00;
        }

        ArrayList<Track> playbackQueue = (ArrayList<Track>)Library.trackList.clone();
        Collections.sort(playbackQueue);

        assertEquals(playbackQueue.get(4).getTrackId(), Library.trackList.get(0).getTrackId());
        assertEquals(playbackQueue.get(3).getTrackId(), Library.trackList.get(1).getTrackId());
        assertEquals(playbackQueue.get(2).getTrackId(), Library.trackList.get(2).getTrackId());
        assertEquals(playbackQueue.get(1).getTrackId(), Library.trackList.get(3).getTrackId());
        assertEquals(playbackQueue.get(0).getTrackId(), Library.trackList.get(4).getTrackId());
    }

    // test that flashback queue creation works with likes and dislikes alone.
    @Test
    public void test_FlashbackCreateQueue_likesDislikes() {

        for (int i = 0; i < Library.trackList.size(); ++i) {
            if (i < 5) {
                Library.trackList.get(i).setUserVote(1);
            }

            if (i < 10) {
                Library.trackList.get(i).setLastEpochMillis(i);
            }

            if (i >= 10) {
                Library.trackList.get(i).setUserVote(-1);
            }
        }

        FlashbackQueue fbq = new FlashbackQueue(mockLoc.getLatitude(), mockLoc.getLongitude());
        fbq.createQueue();

        assertEquals(10, fbq.getPlaybackQueue().size());

        assertEquals(fbq.getPlaybackQueue().get(0).getTrackId(), Library.trackList.get(4).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(1).getTrackId(), Library.trackList.get(3).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(2).getTrackId(), Library.trackList.get(2).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(3).getTrackId(), Library.trackList.get(1).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(4).getTrackId(), Library.trackList.get(0).getTrackId());

        assertEquals(fbq.getPlaybackQueue().get(5).getTrackId(), Library.trackList.get(9).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(6).getTrackId(), Library.trackList.get(8).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(7).getTrackId(), Library.trackList.get(7).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(8).getTrackId(), Library.trackList.get(6).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(9).getTrackId(), Library.trackList.get(5).getTrackId());
    }

    // test that flashback queue creation works with time and day alone.
    @Test
    public void test_FlashbackCreateQueue_timeDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

        for (int i = 0; i < Library.trackList.size(); ++i) {
            if (i==0 || i==1) {
                Library.trackList.get(i).setLastTime(cal.get(Calendar.HOUR_OF_DAY));
                Library.trackList.get(i).setLastEpochMillis(i);
            }

            if (i==2 || i==3) {
                Library.trackList.get(i).setLastDay(cal.get(Calendar.DAY_OF_WEEK));
                Library.trackList.get(i).setLastTime(cal.get(Calendar.HOUR_OF_DAY));
                Library.trackList.get(i).setLastEpochMillis(i);
            }

            if (i==4 || i==5) {
                Library.trackList.get(i).setLastEpochMillis(i);
            }

            if (i==6 || i==7) {
                Library.trackList.get(i).setLastDay(cal.get(Calendar.DAY_OF_WEEK));
                Library.trackList.get(i).setLastEpochMillis(i);
            }

            if (i==8 || i==9) {
                Library.trackList.get(i).setLastDay(cal.get(Calendar.DAY_OF_WEEK)+1);
                Library.trackList.get(i).setLastTime(cal.get(Calendar.HOUR_OF_DAY)+1);
                Library.trackList.get(i).setLastEpochMillis(i);
            }

            if (i>=10) {
                Library.trackList.get(i).setUserVote(-1);
            }
        }

        FlashbackQueue fbq = new FlashbackQueue(mockLoc.getLatitude(), mockLoc.getLongitude());
        fbq.createQueue();

        assertEquals(fbq.getPlaybackQueue().size(), 10);

        assertEquals(fbq.getPlaybackQueue().get(0).getTrackId(), Library.trackList.get(3).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(1).getTrackId(), Library.trackList.get(2).getTrackId());

        assertEquals(fbq.getPlaybackQueue().get(2).getTrackId(), Library.trackList.get(9).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(3).getTrackId(), Library.trackList.get(8).getTrackId());

        assertEquals(fbq.getPlaybackQueue().get(4).getTrackId(), Library.trackList.get(1).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(5).getTrackId(), Library.trackList.get(0).getTrackId());

        assertEquals(fbq.getPlaybackQueue().get(6).getTrackId(), Library.trackList.get(7).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(7).getTrackId(), Library.trackList.get(6).getTrackId());

        assertEquals(fbq.getPlaybackQueue().get(8).getTrackId(), Library.trackList.get(5).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(9).getTrackId(), Library.trackList.get(4).getTrackId());
    }

    // test that flashback queue creation works with location.
    @Test
    public void test_FlashbackCreateQueue_location() {

        for (int i = 0; i < Library.trackList.size(); ++i) {
            if (i==0 || i==1) {
                Library.trackList.get(i).setLastEpochMillis(i);
            }

            // UTC Mall
            if (i==2 || i==3) {
                Library.trackList.get(i).setLastLocation(32.870806, -117.210921);
                Library.trackList.get(i).setLastEpochMillis(i);
            }

            // Warren Bear
            if (i==4 || i==5) {
                Library.trackList.get(i).setLastLocation(32.881955, -117.234254);
                Library.trackList.get(i).setLastEpochMillis(i);
            }

            // Puesto La Jolla
            if (i==6 || i==7) {
                Library.trackList.get(i).setLastLocation(32.846871, -117.273222);
                Library.trackList.get(i).setLastEpochMillis(i);
            }

            // Price Center
            if (i==8 || i==9) {
                Library.trackList.get(i).setLastLocation(32.880177, -117.236002);
                Library.trackList.get(i).setLastEpochMillis(i);
            }

            if (i>=10) {
                Library.trackList.get(i).setUserVote(-1);
            }
        }

        FlashbackQueue fbq = new FlashbackQueue(mockLoc.getLatitude(), mockLoc.getLongitude());
        fbq.createQueue();

        assertEquals(fbq.getPlaybackQueue().size(), 10);

        assertEquals(fbq.getPlaybackQueue().get(0).getTrackId(), Library.trackList.get(5).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(1).getTrackId(), Library.trackList.get(4).getTrackId());

        assertEquals(fbq.getPlaybackQueue().get(2).getTrackId(), Library.trackList.get(9).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(3).getTrackId(), Library.trackList.get(8).getTrackId());

        assertEquals(fbq.getPlaybackQueue().get(4).getTrackId(), Library.trackList.get(3).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(5).getTrackId(), Library.trackList.get(2).getTrackId());

        assertEquals(fbq.getPlaybackQueue().get(6).getTrackId(), Library.trackList.get(7).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(7).getTrackId(), Library.trackList.get(6).getTrackId());

        assertEquals(fbq.getPlaybackQueue().get(8).getTrackId(), Library.trackList.get(1).getTrackId());
        assertEquals(fbq.getPlaybackQueue().get(9).getTrackId(), Library.trackList.get(0).getTrackId());
    }*/


}
