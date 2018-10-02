package tests;

import com.example.jaygandhi.flashbackmusicteam34.Library;

import org.junit.Test;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Yixin on 2/18/18.
 * This is used to test the Library and its loading music functions.
 */

public class JUnitTestLibrary {
    @Test
    public void test_loadSongs(){
        Library.loadSongs();
        assertNotNull(Library.trackList);
        assertNotNull(Library.idsToSongs);
    }

    @Test
    public void test_loadAlbums(){
        Library.loadAlbums();
        assertNotNull(Library.albumList);
    }
}
