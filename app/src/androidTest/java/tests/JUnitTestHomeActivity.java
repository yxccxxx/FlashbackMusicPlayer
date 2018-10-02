package tests;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;

import com.example.jaygandhi.flashbackmusicteam34.AlbumActivity;
import com.example.jaygandhi.flashbackmusicteam34.HomeActivity;
import com.example.jaygandhi.flashbackmusicteam34.PlayFlashbackActivity;
import com.example.jaygandhi.flashbackmusicteam34.PlaySongActivity;
import com.example.jaygandhi.flashbackmusicteam34.PlaybackActivity;
import com.example.jaygandhi.flashbackmusicteam34.R;
import com.example.jaygandhi.flashbackmusicteam34.TrackListActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertEquals;

/**
 * Created by yidongluo on 2/16/18.
 */

public class JUnitTestHomeActivity {
    @Rule
    public ActivityTestRule<HomeActivity> homeActivity = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    @Test
    public void test_buttonCorrectDisplay(){

        Button trackBtn = homeActivity.getActivity().findViewById(R.id.songs_btn);
        String val = trackBtn.getText().toString();

        assertEquals( "songs", val );

        Button albumBtn = homeActivity.getActivity().findViewById(R.id.albums_btn);
        val = albumBtn.getText().toString();

        assertEquals( "albums",val );


        Button vibeModeButton = homeActivity.getActivity().findViewById(R.id.vibe_mode_btn);
        val = vibeModeButton.getText().toString();


        assertEquals( "Vibe Mode", val );

    }

    /*
     * Source: https://stackoverflow.com/questions/9405561/test-if-a-button-starts-a-new-activity-in-android-junit-pref-without-robotium
     */
    @Test
    public void test_launchVibeMode(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(PlayFlashbackActivity.class.getName(), null, false);

        HomeActivity myActivity = homeActivity.getActivity();
        final Button button = (Button) myActivity.findViewById(R.id.vibe_mode_btn);
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });

        PlaySongActivity nextActivity = (PlaySongActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNull(nextActivity);
    }

    @Test
    public void test_launchAlbum(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(AlbumActivity.class.getName(), null, false);

        HomeActivity myActivity = homeActivity.getActivity();
        final Button button = (Button) myActivity.findViewById(R.id.albums_btn);
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });

        AlbumActivity nextActivity = (AlbumActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void test_launchSong(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(PlaybackActivity.class.getName(), null, false);

        HomeActivity myActivity = homeActivity.getActivity();
        final Button button = (Button) myActivity.findViewById(R.id.songs_btn);
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });

        PlaybackActivity nextActivity = (PlaybackActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

}
