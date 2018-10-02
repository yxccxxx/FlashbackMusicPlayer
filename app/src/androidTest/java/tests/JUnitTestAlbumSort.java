package tests;

import com.example.jaygandhi.flashbackmusicteam34.Album;
import com.example.jaygandhi.flashbackmusicteam34.AlbumSort;
import com.example.jaygandhi.flashbackmusicteam34.MockQueue;
import com.example.jaygandhi.flashbackmusicteam34.PlaybackQueue;
import com.example.jaygandhi.flashbackmusicteam34.Track;
import com.example.jaygandhi.flashbackmusicteam34.VibeSort;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import java.util.ArrayList;

/**
 * Created by Yixin on 3/16/18.
 */

public class JUnitTestAlbumSort {
    private MockQueue pbq;
    private Track track1;
    private Track track2;
    private Track track3;
    private Track noalbum;
    private AlbumSort albumSort;

    @Before
    public void setUp(){
        pbq = new MockQueue();
        track1 = new Track(2333);
        track2 = new Track(6666);
        track3 = new Track(9999);
        noalbum = new Track(0);
        albumSort = new AlbumSort();
    }

    @Test
    public void testSortSongs(){
        track1.setAlbumName("A beautiful morning");
        track2.setAlbumName("lkkkkk");
        track3.setAlbumName("kkkkk");
        pbq.addTrack(track1);
        pbq.addTrack(track2);
        pbq.addTrack(track3);
        pbq.addTrack(noalbum);

        ArrayList<Track> result = albumSort.sortSongs(pbq);
        assertEquals(4, result.size());
        assertEquals("A beautiful morning", result.get(0).getAlbumName());
        assertEquals("kkkkk",result.get(1).getAlbumName());
        assertEquals("lkkkkk", result.get(2).getAlbumName());
        assertNotNull(result.get(3));
        assertNull(result.get(3).getAlbumName());
    }

}
