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
import com.example.jaygandhi.flashbackmusicteam34.TrackListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Yixin on 2/17/18.
 * Junit tester to test AlbumTrackActivity.java
 */

public class JUnitTestAlbumTrackActivity {

  /*  @Rule
    public ActivityTestRule<HomeActivity> homeActivity = new ActivityTestRule<HomeActivity>(HomeActivity.class);
    private AlbumActivity albumActivity;
    private AlbumTrackActivity albumTrackActivity;
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

        ListView listView = albumActivity.getListView();

        listView.performItemClick(
                listView.getAdapter().getView(1, null, null),
                1,
                listView.getAdapter().getItemId(1));

        activityMonitor = getInstrumentation().addMonitor(AlbumTrackActivity.class.getName(), null, false);
        albumTrackActivity = (AlbumTrackActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);

    }

    @Test
    public void test_header(){
        String header = albumTrackActivity.getHeader();
        String expectedHeader = albumTrackActivity.getCurAlbum().getAlbumName();
        assertEquals(expectedHeader, header);
    }

    @Test
    public void test_listView(){
        ListView listView = albumTrackActivity.getListView();
        assertNotNull(listView);
        ListAdapter adapter = listView.getAdapter();
        assertNotNull(adapter);
    }

    @Test
    public void test_itemClick(){
        ListView listView = albumTrackActivity.getListView();

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
        albumActivity.finish();
        albumTrackActivity.finish();
    }*/

}
