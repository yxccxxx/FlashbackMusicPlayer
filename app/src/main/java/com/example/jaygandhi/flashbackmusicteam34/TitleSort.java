package com.example.jaygandhi.flashbackmusicteam34;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by jaygandhi on 3/11/18.
 */

public class TitleSort implements SortBehavior {
    public TitleSort(){}

    public ArrayList<Track> sortSongs(PlaybackQueue pbq) {
        ArrayList<String> titles = pbq.getQueueTitles();
        Collections.sort(titles);

        ArrayList<Track> sortedList = new ArrayList<>();
        for(String s : titles) {
            for(Track t : pbq.getQueue()) {
                if(t.getTrackName().equals(s)) {
                    sortedList.add(t);
                }
            }
        }
        return sortedList;
    }
}
