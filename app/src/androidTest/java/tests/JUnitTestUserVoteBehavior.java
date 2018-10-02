package tests;

import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.example.jaygandhi.flashbackmusicteam34.HomeActivity;
import com.example.jaygandhi.flashbackmusicteam34.Library;
import com.example.jaygandhi.flashbackmusicteam34.PlayFlashbackActivity;
import com.example.jaygandhi.flashbackmusicteam34.PlaySongActivity;
import com.example.jaygandhi.flashbackmusicteam34.PlaybackActivity;
import com.example.jaygandhi.flashbackmusicteam34.PlaybackQueue;
import com.example.jaygandhi.flashbackmusicteam34.R;
import com.example.jaygandhi.flashbackmusicteam34.Track;
import com.example.jaygandhi.flashbackmusicteam34.TrackListActivity;
import com.example.jaygandhi.flashbackmusicteam34.TrackStatus;
import com.example.jaygandhi.flashbackmusicteam34.UserVoteBehavior;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Yixin and Rajit on 2/17/18.
 * Junit test for UserVoteBehavior.java
 */

public class JUnitTestUserVoteBehavior {

    @Rule
    public ActivityTestRule<HomeActivity> homeActivity = new ActivityTestRule<HomeActivity>(HomeActivity.class);
    HomeActivity myActivity;
    private UserVoteBehavior userVoteBehavior;
    private Track track;


    @Before
    public void setUp(){

        myActivity = homeActivity.getActivity();
        track = new Track(23333);

    }

    @Test
    public void test_constructor(){
        userVoteBehavior = new UserVoteBehavior(track);
        assertEquals(userVoteBehavior.getCurTrack().getTrackId(), 23333);
    }

    @Test
    public void test_updateVoteButton(){
        track.setUserVote(1);
        userVoteBehavior = new UserVoteBehavior(track);
        Button button = new Button(myActivity);
        userVoteBehavior.updateVoteButton(button);
        String val = button.getText().toString();
        assertEquals( "✓", val );


        track.setUserVote(0);
        userVoteBehavior = new UserVoteBehavior(track);
        button = new Button(myActivity);
        userVoteBehavior.updateVoteButton(button);
        val = button.getText().toString();
        assertEquals( "+", val );


        track.setUserVote(-1);
        userVoteBehavior = new UserVoteBehavior(track);
        button = new Button(myActivity);
        userVoteBehavior.updateVoteButton(button);
        val = button.getText().toString();
        assertEquals( "X", val );

    }

    @After
    public void tearDown(){
        myActivity.finish();
    }

}
