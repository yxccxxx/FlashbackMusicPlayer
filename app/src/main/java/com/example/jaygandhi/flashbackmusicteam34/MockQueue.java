package com.example.jaygandhi.flashbackmusicteam34;

import java.util.ArrayList;

/**
 * Created by Yixin on 3/16/18.
 * mocking class used to test the sort functions
 */

public class MockQueue extends PlaybackQueue{
    public MockQueue(){
        this.queue = new ArrayList<Track>();
    }

    public void addTrack(Track track){
        this.queue.add(track);
    }
}
