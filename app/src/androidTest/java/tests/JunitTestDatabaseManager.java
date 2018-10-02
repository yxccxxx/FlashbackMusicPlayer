package tests;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;

import com.example.jaygandhi.flashbackmusicteam34.DatabaseManager;
import com.example.jaygandhi.flashbackmusicteam34.HomeActivity;
import com.example.jaygandhi.flashbackmusicteam34.TrackRecord;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class JunitTestDatabaseManager {

    @Rule
    public ActivityTestRule<HomeActivity> homeActivity = new ActivityTestRule<HomeActivity>(HomeActivity.class);
    HomeActivity myActivity;

    @Before
    public void setUp(){
        myActivity = homeActivity.getActivity();
    }


    @Test
    public void testWriteAndRead(){
        TrackRecord record = new TrackRecord();
        record.setResourceID("TEST ARTIST - TEST TITLE");
        record.setLatitude(11);
        record.setLongitude(22);
        record.setTime(System.currentTimeMillis());
        record.setTitle("Test Title");
        record.setUser("user");
        record.setUrl("http://test.com");
        DatabaseManager manager = new DatabaseManager(myActivity);
        manager.writeToDatabase(record);
        assertNotNull(manager);

        TrackRecord record2 = new TrackRecord();
        record2.setResourceID("TEST ARTIST 2 - TEST TITLE 222");
        record2.setLatitude(11);
        record2.setLongitude(22);
        record2.setTime(System.currentTimeMillis());
        record2.setTitle("Test Title 222");
        record2.setUser("user");
        record2.setUrl("http://test.com");
        manager.writeToDatabase(record2);
        assertNotNull(manager);

        //manager.readFromDatabase();

    }

    @After
    public void tearDown(){
        myActivity.finish();
    }

}
