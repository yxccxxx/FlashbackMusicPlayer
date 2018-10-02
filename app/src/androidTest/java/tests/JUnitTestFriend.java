package tests;

import com.example.jaygandhi.flashbackmusicteam34.Friend;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Yixin on 3/15/18.
 */

public class JUnitTestFriend {
    private Friend friend;


    @Before
    public void setUp(){
        friend = new Friend("Yixin", "yic286@ucsd.edu");
    }

    @Test
    public void test_constructor(){
        assertEquals("Yixin", friend.getName());
        assertEquals("yic286@ucsd.edu", friend.getEmail());
    }
}
