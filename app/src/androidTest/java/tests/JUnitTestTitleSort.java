package tests;

import android.drm.DrmStore;

import com.example.jaygandhi.flashbackmusicteam34.ArtistSort;
import com.example.jaygandhi.flashbackmusicteam34.MockQueue;
import com.example.jaygandhi.flashbackmusicteam34.PlaybackQueue;
import com.example.jaygandhi.flashbackmusicteam34.TitleSort;
import com.example.jaygandhi.flashbackmusicteam34.Track;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Created by Yixin on 3/15/18.
 */

public class JUnitTestTitleSort {
    private MockQueue pbq;
    private Track track1;
    private Track track2;
    private Track track3;
    private Track track4;
    private Track notitle;
    private TitleSort titleSort;

    @Before
    public void setUp(){
        pbq = new MockQueue();
        track1 = new Track(2333);
        track2 = new Track(6666);
        track3 = new Track(9999);
        track4 = new Track(1243326534);
        titleSort = new TitleSort();
    }

    @Test
    public void testSortSongs(){
        track1.setTrackName("lol");
        track2.setTrackName("great");
        track3.setTrackName("what TMD is this");
        track4.setTrackName("weird CSE 110");
        pbq.addTrack(track1);
        pbq.addTrack(track2);
        pbq.addTrack(track3);
        pbq.addTrack(track4);

        ArrayList<Track> result = titleSort.sortSongs(pbq);
        assertEquals(4, result.size());
        assertEquals("great", result.get(0).getTrackName());
        assertEquals("lol",result.get(1).getTrackName());
        assertEquals("weird CSE 110", result.get(2).getTrackName());
        assertEquals("what TMD is this", result.get(3).getTrackName());

    }
}
