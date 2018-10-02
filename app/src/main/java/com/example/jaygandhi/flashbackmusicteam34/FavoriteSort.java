package com.example.jaygandhi.flashbackmusicteam34;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by jaygandhi on 3/11/18.
 */

public class FavoriteSort implements SortBehavior {
    public FavoriteSort(){}

    public ArrayList<Track> sortSongs(PlaybackQueue pbq) {
        ArrayList<Track> likedTracks = new ArrayList<>();
        ArrayList<Track> neutralTracks = new ArrayList<>();
        ArrayList<Track> dislikedTracks = new ArrayList<>();

        for(Track t : pbq.getQueue()) {
            if(t.getUserVote() == 1) {
                likedTracks.add(t);
            } else if(t.getUserVote() == 0) {
                neutralTracks.add(t);
            } else {
                dislikedTracks.add(t);
            }
        }

        HashSet<Track> set = new HashSet<>();
        ArrayList<Track> sortedList = new ArrayList<>();

        for(Track t : likedTracks) {
            sortedList.add(t);
        }

        for(Track t : neutralTracks) {
            sortedList.add(t);
        }

        for(Track t : dislikedTracks) {
            sortedList.add(t);
        }

        return sortedList;
    }
}
