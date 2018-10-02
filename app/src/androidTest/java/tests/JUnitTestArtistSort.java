package tests;

import com.example.jaygandhi.flashbackmusicteam34.AlbumSort;
import com.example.jaygandhi.flashbackmusicteam34.ArtistSort;
import com.example.jaygandhi.flashbackmusicteam34.MockQueue;
import com.example.jaygandhi.flashbackmusicteam34.Track;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Created by Yixin on 3/16/18.
 */

public class JUnitTestArtistSort {
    private MockQueue pbq;
    private Track track1;
    private Track track2;
    private Track track3;
    private Track track4;
    private Track noalbum;
    private ArtistSort artistSort;

    @Before
    public void setUp(){
        pbq = new MockQueue();
        track1 = new Track(2333);
        track2 = new Track(6666);
        track3 = new Track(9999);
        track4 = new Track(1243326534);
        noalbum = new Track(0);
        artistSort = new ArtistSort();
    }

    @Test
    public void testSortSongs(){
        track1.setArtistName("cindy");
        track2.setArtistName("dOPIMAN");
        track3.setArtistName("akita");
        track4.setArtistName("akit");
        pbq.addTrack(track1);
        pbq.addTrack(track2);
        pbq.addTrack(track3);
        pbq.addTrack(track4);
        pbq.addTrack(noalbum);

        ArrayList<Track> result = artistSort.sortSongs(pbq);
        assertEquals(5, result.size());
        assertEquals("akit", result.get(0).getArtistName());
        assertEquals("akita",result.get(1).getArtistName());
        assertEquals("cindy", result.get(2).getArtistName());
        assertEquals("dOPIMAN", result.get(3).getArtistName());
        assertNotNull(result.get(4));
        assertNull(result.get(4).getArtistName());
    }
}
