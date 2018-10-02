package tests;

import com.example.jaygandhi.flashbackmusicteam34.AlbumSort;
import com.example.jaygandhi.flashbackmusicteam34.FavoriteSort;
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

public class JUnitTestFavoriteSort {
    private MockQueue pbq;
    private Track track1;
    private Track track2;
    private Track track3;
    private Track track4;
    private FavoriteSort favoriteSort;

    @Before
    public void setUp(){
        pbq = new MockQueue();
        track1 = new Track(2333);
        track2 = new Track(6666);
        track3 = new Track(9999);
        track4 = new Track(345234);
        favoriteSort = new FavoriteSort();
    }

    @Test
    public void testSortSongs(){
        track1.setTrackName("on the beautiful land");
        track1.setUserVote(-1);

        track2.setTrackName("afternoon California");
        track2.setUserVote(0);

        track3.setTrackName("i'm wanna go back home");
        track3.setUserVote(1);

        track4.setTrackName("chuang qian ming yue guang, yi shi di shang shuang");
        track4.setUserVote(0);
        pbq.addTrack(track1);
        pbq.addTrack(track2);
        pbq.addTrack(track3);
        pbq.addTrack(track4);

        ArrayList<Track> result = favoriteSort.sortSongs(pbq);

        assertEquals(4, result.size());
        assertEquals("i'm wanna go back home", result.get(0).getTrackName());
        assertEquals(1, result.get(0).getUserVote());

        assertEquals("afternoon California",result.get(1).getTrackName());
        assertEquals(0, result.get(1).getUserVote());

        assertEquals("chuang qian ming yue guang, yi shi di shang shuang", result.get(2).getTrackName());
        assertEquals(0, result.get(2).getUserVote());

        assertEquals("on the beautiful land",result.get(3).getTrackName());
        assertEquals(-1, result.get(3).getUserVote());
    }
}
