package com.example.jaygandhi.flashbackmusicteam34;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by jaygandhi on 3/11/18.
 */

public class AlbumSort implements SortBehavior {
    public AlbumSort(){}

    public ArrayList<Track> sortSongs(PlaybackQueue pbq) {
        ArrayList<String> albumNames = new ArrayList<>();
        ArrayList<Track> noAlbumTracks = new ArrayList<>();
        ArrayList<Track> albumTracks = new ArrayList<>();

        for(Track t : pbq.getQueue()) {
            if(t.getAlbumName() != null) {
                albumNames.add(t.getAlbumName());
                albumTracks.add(t);
            } else {
                noAlbumTracks.add(t);
            }
        }

        Collections.sort(albumNames);

        HashSet<Track> set = new HashSet<>();
        ArrayList<Track> sortedList = new ArrayList<>();
        for(String s : albumNames) {
            for(Track t : albumTracks) {
                if((t.getAlbumName().equals(s)) && !(set.contains(t))) {
                    sortedList.add(t);
                    set.add(t);
                }
            }
        }

        for(Track t : noAlbumTracks) {
            sortedList.add(t);
        }

        return sortedList;
    }
}
