package tests;

import android.support.test.rule.ActivityTestRule;

import com.example.jaygandhi.flashbackmusicteam34.Album;
import com.example.jaygandhi.flashbackmusicteam34.Track;
import com.example.jaygandhi.flashbackmusicteam34.TrackListActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Yixin and Rajit on 2/17/18.
 * Junit tester to test Album.java
 */

public class JUnitTestAlbum {
    private Album album;
    private Track track;

    @Before
    public void setUp(){
        album = new Album("go to the moon", "yixin");
        track = new Track(12345);
    }

    @Test
    public void test_constructor(){
        assertEquals("go to the moon", album.getAlbumName());
        assertEquals("yixin",album.getArtistName());
        assertEquals(new ArrayList<>(), album.getAlbumTracks());
    }

    @Test
    public void test_addTrack(){
        album.addTrack(track);
        assertEquals(true, album.getAlbumTracks().contains(track));
        album.addTrack(track);
        assertEquals(1, album.getAlbumTracks().size());
    }

    @Test
    public void test_removeTrack(){
        album.addTrack(track);
        album.removeTrack(track);
        assertEquals(album.getAlbumTracks().size(), 0);
        assertEquals(false, album.getAlbumTracks().contains(track));
    }

}
