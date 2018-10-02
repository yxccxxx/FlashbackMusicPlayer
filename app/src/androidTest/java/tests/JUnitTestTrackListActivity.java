package tests;

import android.content.ClipData;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;
import android.app.Instrumentation;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.jaygandhi.flashbackmusicteam34.AlbumActivity;
import com.example.jaygandhi.flashbackmusicteam34.AlbumTrackActivity;
import com.example.jaygandhi.flashbackmusicteam34.HomeActivity;
import com.example.jaygandhi.flashbackmusicteam34.PlayFlashbackActivity;
import com.example.jaygandhi.flashbackmusicteam34.PlaySongActivity;
import com.example.jaygandhi.flashbackmusicteam34.R;
import com.example.jaygandhi.flashbackmusicteam34.TrackListActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;


/**
 * Created by Yixin and Rajit on 2/17/18.
 * Junit test for TrackListActivity.java
 * The class Tracklist activity is not used in milestone 2
 */

public class JUnitTestTrackListActivity {

   /* @Rule
    public ActivityTestRule<HomeActivity> homeActivity = new ActivityTestRule<HomeActivity>(HomeActivity.class);
    private TrackListActivity trackListActivity;
    private Instrumentation.ActivityMonitor activityMonitor;

    @Before
    public void setUp(){

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

    }

    @Test
    public void test_header(){
        String header = trackListActivity.getHeader();
        assertEquals("Songs", header);
    }

    @Test
    public void test_listView(){
        ListView listView = trackListActivity.getListView();
        assertNotNull(listView);
        ListAdapter adapter = listView.getAdapter();
        assertNotNull(adapter);
    }

    @Test
    public void test_itemClick(){
        ListView listView = trackListActivity.getListView();

        listView.performItemClick(
                listView.getAdapter().getView(1, null, null),
                1,
                listView.getAdapter().getItemId(1));

        activityMonitor = getInstrumentation().addMonitor(PlaySongActivity.class.getName(), null, false);
        PlaySongActivity playSongActivity = (PlaySongActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(playSongActivity);
        playSongActivity.finish();
    }

    @After
    public final void tearDown() {
        trackListActivity.finish();
    }*/



}
