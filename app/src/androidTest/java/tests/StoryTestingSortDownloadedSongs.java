package tests;


import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.example.jaygandhi.flashbackmusicteam34.DownloadSongActivity;
import com.example.jaygandhi.flashbackmusicteam34.HomeActivity;
import com.example.jaygandhi.flashbackmusicteam34.Library;
import com.example.jaygandhi.flashbackmusicteam34.PlaybackActivity;
import com.example.jaygandhi.flashbackmusicteam34.R;
import com.example.jaygandhi.flashbackmusicteam34.Track;
import com.example.jaygandhi.flashbackmusicteam34.TrackStatus;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertEquals;

public class StoryTestingSortDownloadedSongs {

    @Rule
    public ActivityTestRule<HomeActivity> homeActivityActivityTestRule = new ActivityTestRule<HomeActivity>(HomeActivity.class);
    private Instrumentation.ActivityMonitor activityMonitor;
    private PlaybackActivity playbackActivity;

    @Before
    public void setup() {
        // start the home activity
        HomeActivity homeActivity = homeActivityActivityTestRule.getActivity();

        // load the library for testing
        loadLibrary();

        // click the tracks button
        final Button button = (Button) homeActivity.findViewById(R.id.songs_btn);
        homeActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });

        activityMonitor = getInstrumentation().addMonitor(PlaybackActivity.class.getName(), null, false);
        playbackActivity = (PlaybackActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 15000);
    }


    @Test
    public void testDefaultSortingOption() {
        ListView listView = playbackActivity.findViewById(R.id.number_list);
        String firstTitle = listView.getItemAtPosition(0).toString();
        assertEquals(firstTitle, "aaa1");
    }

    /*

    @Test
    public void testTitleSortingOption() {
        final MenuItem titleSort = (MenuItem) playbackActivity.findViewById(R.id.sort_title);
        playbackActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                titleSort.expandActionView();
            }
        });
        ListView listView = playbackActivity.findViewById(R.id.number_list);
        String firstTitle = listView.getItemAtPosition(0).toString();
        assertEquals(firstTitle, "aaa1");
    }
*/

    private void loadLibrary() {
        Library.trackList.clear();
        Track track1 = new Track(1);
        Track track2 = new Track(2);
        Track track3 = new Track(3);
        Track track4 = new Track(4);

        long time = System.currentTimeMillis();

        track1.setTrackName("aaa1");
        track1.setArtistName("zzz1");
        track1.setUserVote(TrackStatus.DISLIKE);
        track1.setAlbumName("zzz1");
        track1.setLastEpochMillis(time);

        track2.setAlbumName("aaa2");
        track2.setTrackName("zzz2");
        track2.setArtistName("zzz2");
        track2.setUserVote(TrackStatus.DISLIKE);
        track2.setLastEpochMillis(time - 10);

        track3.setArtistName("aaa3");
        track3.setTrackName("zzz3");
        track3.setAlbumName("zzz3");
        track3.setUserVote(TrackStatus.DISLIKE);
        track3.setLastEpochMillis(time - 30);

        track4.setArtistName("zzz4");
        track4.setAlbumName("zzz4");
        track4.setTrackName("zzz4");
        track4.setUserVote(TrackStatus.LIKE);
        track4.setLastEpochMillis(time - 50);

        Library.trackList.add(track1);
        Library.trackList.add(track2);
        Library.trackList.add(track3);
        Library.trackList.add(track4);
    }

}
