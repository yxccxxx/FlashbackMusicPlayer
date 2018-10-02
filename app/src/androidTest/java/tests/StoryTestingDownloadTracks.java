package tests;

import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.EditText;

import com.example.jaygandhi.flashbackmusicteam34.AlbumActivity;
import com.example.jaygandhi.flashbackmusicteam34.DownloadSongActivity;
import com.example.jaygandhi.flashbackmusicteam34.HomeActivity;
import com.example.jaygandhi.flashbackmusicteam34.Library;
import com.example.jaygandhi.flashbackmusicteam34.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

/**
 * Created by TonyLiu on 3/16/18.
 */

public class StoryTestingDownloadTracks {

    @Rule
    public ActivityTestRule<HomeActivity> homeActivity = new ActivityTestRule<HomeActivity>(HomeActivity.class);
    private Instrumentation.ActivityMonitor activityMonitor;
    private DownloadSongActivity downloadSongActivity;

    @Before
    public void setup() {
        // enter home activity
        HomeActivity myActivity = homeActivity.getActivity();
        // click the remote library button
        final Button button = (Button) myActivity.findViewById(R.id.remote_library_btn);
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });

        // Then we should jump into the download song activity
        activityMonitor = getInstrumentation().addMonitor(DownloadSongActivity.class.getName(), null, false);
        downloadSongActivity = (DownloadSongActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 15000);
    }

    @Test
    public void testDownload(){
        Uri temp = Uri.parse("https://www.dropbox.com/s/fg7gc1tmdl1jre8/transmission-002-the-blackhole.mp3?dl=1");
        String downloadReturnValue = downloadSongActivity.getMimeType(temp.toString());
        // test if the tracklist is empty
        boolean songDownloaded = Library.trackList != null;
        assertEquals(true, songDownloaded);
        assertEquals(null, downloadReturnValue);
    }


    public void test() {
        // enter the text
        EditText input = downloadSongActivity.findViewById(R.id.URL_box);
        input.setText("https://www.dropbox.com/s/fg7gc1tmdl1jre8/transmission-002-the-blackhole.mp3?dl=1");
        // click the down song button
        final Button button = (Button) downloadSongActivity.findViewById(R.id.download_btn);
        downloadSongActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.performClick();
            }
        });

        // test whether the song is loaded in the Library
        boolean songDownloaded = ! Library.trackList.isEmpty();
        assertEquals(songDownloaded, true);
    }

    @After
    public void tearDown() {
        homeActivity.finishActivity();
        downloadSongActivity.finish();
    }

}
