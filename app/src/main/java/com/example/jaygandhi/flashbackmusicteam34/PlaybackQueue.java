package com.example.jaygandhi.flashbackmusicteam34;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by jaygandhi on 2/14/18.
 * A play back queue
 */
public abstract class PlaybackQueue {
    protected ArrayList<Track> queue;
    protected SortBehavior sortBehavior = null;
    protected Track curTrack = null;
    private static final String TAG = "PlaybackQueue";

    // get the current track playing
    public Track getCurTrack() {
        return curTrack;
    }

    // set the current track playing
    public void setCurTrack(int position) {
        curTrack = this.queue.get(position);
    }

    public void setCurTrack(Track newtrack) {
        curTrack = newtrack;
    }

    public ArrayList<Track> getQueue() {
        return queue;
    }

    public ArrayList<String> getQueueTitles() {
        ArrayList<String> queueTitles = new ArrayList<>();
        for(Track t : queue) {
            if((curTrack != null) && (t.getTrackName().equals(curTrack.getTrackName()))) {
                queueTitles.add("> " + t.getTrackName());
            } else {
                queueTitles.add(t.getTrackName());
            }
        }
        return queueTitles;
    }

    // set the sorting behavior based on how this queue is being used
    public void setSortBehavior(SortBehavior sortBehavior) {
        this.sortBehavior = sortBehavior;
    }

    // strategy pattern: delegate to sorting behavior classes
    public void sortQueue() {
        queue = sortBehavior.sortSongs(this);
        Log.d(TAG, "sortQueue is already tested in sort classes.");
    }

    public Track getNextTrack(){
        int position = queue.indexOf(curTrack);
        if((position + 1) >= queue.size()) {

            return queue.get(0);
        } else {
            return queue.get(position+1);
        }
    }


    /**
    public ArrayList<String> getQueueTitles() {
        ArrayList<String> queueTitles = new ArrayList<>();
        for(Track t : this.getQueue()) {
            queueTitles.add(t.getTrackName());
        }
        return queueTitles;
    }

    // Fetch a list of upcoming tracks in the album
    public ArrayList<Track> viewUpcoming() {
        ArrayList<Track> upcoming = new ArrayList<>();
        boolean startAdding = false;

        for(Track t : queue) {
            if(curTrack == t) {
                startAdding = true;
            }
            if(startAdding == true) {
                upcoming.add(t);
            }
        }

        return upcoming;

    }

    public void createQueue();
    public void addTrackToQueue(Track trackToAdd);
    public void removeTrackFromQueue(Track trackToRemove);
     */
}
