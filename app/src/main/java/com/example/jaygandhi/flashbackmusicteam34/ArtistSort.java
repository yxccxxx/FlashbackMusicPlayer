package com.example.jaygandhi.flashbackmusicteam34;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by jaygandhi on 3/11/18.
 */

public class ArtistSort implements SortBehavior {
    public ArtistSort(){}

    public ArrayList<Track> sortSongs(PlaybackQueue pbq) {
        ArrayList<String> artistNames = new ArrayList<>();
        ArrayList<Track> noArtistTracks = new ArrayList<>();
        ArrayList<Track> artistTracks = new ArrayList<>();

        for(Track t : pbq.getQueue()) {
            if(t.getArtistName() != null) {
                artistNames.add(t.getArtistName());
                artistTracks.add(t);
            } else {
                noArtistTracks.add(t);
            }
        }

        Collections.sort(artistNames);

        HashSet<Track> set = new HashSet<>();
        ArrayList<Track> sortedList = new ArrayList<>();
        for(String s : artistNames) {
            for(Track t : artistTracks) {
                if((t.getArtistName().equals(s)) && !(set.contains(t))) {
                    sortedList.add(t);
                    set.add(t);
                }
            }
        }

        for(Track t : noArtistTracks) {
            sortedList.add(t);
        }

        return sortedList;
    }
}
