package tests;

import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jaygandhi.flashbackmusicteam34.HomeActivity;
import com.example.jaygandhi.flashbackmusicteam34.Library;
import com.example.jaygandhi.flashbackmusicteam34.PlaySongActivity;
import com.example.jaygandhi.flashbackmusicteam34.R;
import com.example.jaygandhi.flashbackmusicteam34.Track;
import com.example.jaygandhi.flashbackmusicteam34.TrackListActivity;
import com.example.jaygandhi.flashbackmusicteam34.TrackStatus;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

/**
 * Created by Yixin on 2/17/18.
 * Junit Test to test PlaySongActivity.java
 * we don't use PlaySongActivity in milestone 2
 */

public class JUnitTestPlaySongActivity {

  /*  @Rule
    public ActivityTestRule<HomeActivity> homeActivity = new ActivityTestRule<HomeActivity>(HomeActivity.class);
    private TrackListActivity trackListActivity;
    private PlaySongActivity playSongActivity;
    private Instrumentation.ActivityMonitor activityMonitor;

    private Track curTrack;
    private Calendar testDate;

    @Before
    public void setUp(){

        testDate = Calendar.getInstance();
        curTrack = Library.trackList.get(0);

        curTrack.setUserVote(TrackStatus.LIKE);
        curTrack.setLastDay(2);
        curTrack.setLastTime(10);
        curTrack.setLastDate(testDate);

        HomeActivity myActivity = homeActivity.getActivity();
        final Button button = (Button) myActivity.findViewById(R.id.songs_btn);
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });
        activityMonitor = getInstrumentation().addMonitor(TrackListActivity.class.getName(), null, false);
        trackListActivity = (TrackListActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);

        SharedPreferences tempPrefLat = trackListActivity.getSharedPreferences("test_lat", Context.MODE_PRIVATE);
        SharedPreferences tempPrefLong = trackListActivity.getSharedPreferences("test_long", Context.MODE_PRIVATE);
        curTrack.setLastLocation(12, 34, tempPrefLat, tempPrefLong);

        ListView listView = trackListActivity.getListView();

        listView.performItemClick(
                listView.getAdapter().getView(1, null, null),
                1,
                listView.getAdapter().getItemId(1));

        activityMonitor = getInstrumentation().addMonitor(PlaySongActivity.class.getName(), null, false);
        playSongActivity = (PlaySongActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
    }

    @Test
    public void testMetaData() {
        TextView titleText = playSongActivity.findViewById(R.id.trackname_view);
        TextView albumText = playSongActivity.findViewById(R.id.albumname_view);
        TextView artistText = playSongActivity.findViewById(R.id.artistname_view);
        assertEquals(titleText.getText().toString(), curTrack.getTrackName());
        assertEquals(albumText.getText().toString(), curTrack.getAlbumName());
        assertEquals(artistText.getText().toString(), curTrack.getAlbumArtist());
    }

    @Test
    public void testHistoricalData() {
        String latitudeInfo = "Lat: " + Double.toString(12);
        String longitudeInfo = "Long: " + Double.toString(34);
        String timeInfo = String.format("%d--%d--%d  %d:%d", testDate.get(Calendar.YEAR), testDate.get(Calendar.MONTH),
                testDate.get(Calendar.DAY_OF_MONTH), testDate.get(Calendar.HOUR_OF_DAY), testDate.get(Calendar.MINUTE));
        TextView latitudeView = playSongActivity.findViewById(R.id.latitude);
        TextView longitudeView = playSongActivity.findViewById(R.id.longitude);
        TextView timeView = playSongActivity.findViewById(R.id.time_day);
        assertEquals(latitudeView.getText().toString(), latitudeInfo);
        assertEquals(longitudeView.getText().toString(), longitudeInfo);
        assertEquals(timeView.getText().toString(), timeInfo);
    }

    @Test
    public void testLikeDislike() {
        // current status: like
        Button likeDislikeBtn = playSongActivity.findViewById(R.id.like_dislike);
        assertEquals(likeDislikeBtn.getText().toString(), playSongActivity.getString(R.string.status_like));

        // click once, dislike
        likeDislikeBtn.performClick();
        //assertEquals(likeDislikeBtn.getText().toString(), playSongActivity.getString(R.string.status_neutral));
        assertEquals(curTrack.getUserVote(), TrackStatus.DISLIKE);

        // click again, neutral
        likeDislikeBtn.performClick();
        assertEquals(likeDislikeBtn.getText().toString(), playSongActivity.getString(R.string.status_neutral));
        assertEquals(curTrack.getUserVote(), TrackStatus.DISLIKE);
    }

    @Test
    public void testPlayPause() {

        // reset the status for testing playing/pausing
        curTrack.setUserVote(TrackStatus.LIKE);

        Button playPauseBtn = playSongActivity.findViewById(R.id.pauseplay_button);
        assertEquals(playSongActivity.getString(R.string.pause), playPauseBtn.getText().toString());

        playPauseBtn.performClick();
        assertEquals(playSongActivity.getString(R.string.pause), playPauseBtn.getText().toString());

        playPauseBtn.performClick();
        assertEquals(playSongActivity.getString(R.string.play), playPauseBtn.getText().toString());
    }

    @After
    public void tearDown() {
        trackListActivity.finish();
        playSongActivity.finish();
    }*/

}
