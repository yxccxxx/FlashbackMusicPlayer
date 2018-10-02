package tests;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.jaygandhi.flashbackmusicteam34.Album;
import com.example.jaygandhi.flashbackmusicteam34.AlbumActivity;
import com.example.jaygandhi.flashbackmusicteam34.AlbumTrackActivity;
import com.example.jaygandhi.flashbackmusicteam34.HomeActivity;
import com.example.jaygandhi.flashbackmusicteam34.PlaySongActivity;
import com.example.jaygandhi.flashbackmusicteam34.R;
import com.example.jaygandhi.flashbackmusicteam34.Track;
import com.example.jaygandhi.flashbackmusicteam34.TrackListActivity;
import com.example.jaygandhi.flashbackmusicteam34.UserVoteBehavior;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertEquals;


/**
 * Created by Yixin on 2/17/18.
 * Junit tester to test AlbumActivity.java
 * the pump-up of album list activity is tested in the espresso test
 */

public class JUnitTestAlbumActivity {
   /* @Rule
    public ActivityTestRule<HomeActivity> homeActivity = new ActivityTestRule<HomeActivity>(HomeActivity.class);
    private AlbumActivity albumActivity;
    private Instrumentation.ActivityMonitor activityMonitor;

    @Before
    public void setUp(){

        HomeActivity myActivity = homeActivity.getActivity();
        final Button button = (Button) myActivity.findViewById(R.id.albums_btn);
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });
        activityMonitor = getInstrumentation().addMonitor(AlbumActivity.class.getName(), null, false);
        albumActivity = (AlbumActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);

    }

    @Test
    public void test_header(){
        String header = albumActivity.getHeader();
        assertEquals("Albums", header);
    }

    @Test
    public void test_listView(){
        ListView listView = albumActivity.getListView();
        assertNotNull(listView);
        ListAdapter adapter = listView.getAdapter();
        assertNotNull(adapter);
    }

    @Test
    public void test_itemClick(){
        ListView listView = albumActivity.getListView();

        listView.performItemClick(
                listView.getAdapter().getView(1, null, null),
                1,
                listView.getAdapter().getItemId(1));

        activityMonitor = getInstrumentation().addMonitor(AlbumTrackActivity.class.getName(), null, false);
        AlbumTrackActivity albumTrackActivity = (AlbumTrackActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(albumTrackActivity);
        albumTrackActivity.finish();

    }

    @After
    public final void tearDown() {
        albumActivity.finish();
    }*/
}
