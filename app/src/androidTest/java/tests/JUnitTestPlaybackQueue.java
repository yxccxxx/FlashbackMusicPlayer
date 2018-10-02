package tests;

import com.example.jaygandhi.flashbackmusicteam34.MockQueue;
import com.example.jaygandhi.flashbackmusicteam34.TitleSort;
import com.example.jaygandhi.flashbackmusicteam34.Track;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Yixin on 3/16/18.
 */

public class JUnitTestPlaybackQueue {
    private MockQueue pbq;
    private Track track1;
    private Track track2;
    private Track track3;
    private Track track4;

    @Before
    public void setUp(){
        pbq = new MockQueue();
        track1 = new Track(2333);
        track2 = new Track(6666);
        track3 = new Track(9999);
        track4 = new Track(1243326534);
    }

    @Test
    public void testSortedTitles(){
        track1.setTrackName("lol");
        track2.setTrackName("great");
        track3.setTrackName("what TMD is this");
        track4.setTrackName("weird CSE 110");
        pbq.addTrack(track1);
        pbq.addTrack(track2);
        pbq.addTrack(track3);
        pbq.addTrack(track4);

        ArrayList<String> result = pbq.getQueueTitles();

        assertEquals(4, result.size());
        assertEquals("lol", result.get(0));
        assertEquals("great",result.get(1));
        assertEquals("what TMD is this", result.get(2));
        assertEquals("weird CSE 110", result.get(3));

    }

    @Test
    public void testNextTrack(){
        track1.setTrackName("lol");
        track2.setTrackName("great");
        track3.setTrackName("what TMD is this");
        track4.setTrackName("weird CSE 110");
        pbq.addTrack(track1);
        pbq.addTrack(track2);
        pbq.addTrack(track3);
        pbq.addTrack(track4);

        pbq.setCurTrack(2);
        assertEquals("what TMD is this", pbq.getCurTrack().getTrackName());

        Track next = pbq.getNextTrack();
        assertEquals("weird CSE 110", next.getTrackName());

        pbq.setCurTrack(3);
        next = pbq.getNextTrack();
        assertEquals("lol", next.getTrackName());
    }

    @After
    public void tearDown(){
        pbq = null;
        track1 = null;
        track2 = null;
        track3 = null;
        track4 = null;
    }
}
