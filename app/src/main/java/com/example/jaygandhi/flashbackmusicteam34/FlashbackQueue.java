package com.example.jaygandhi.flashbackmusicteam34;

import android.location.Location;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.TimeZone;


/**
 * Created by Rajit Dang on 2/14/18.
 * A queue of songs that is supposed to be played under flash back mode
 */
public class FlashbackQueue {

    private ArrayList<Track> playbackQueue;
    private Double userLatitude;
    private Double userLongitude;

    /**
     * Construct a flashback queue object.
     */
    public FlashbackQueue(Double userLatitude, Double userLongitude) {
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
    }

    /*
     * Create a queue
     */
    public void createQueue() {
        playbackQueue = (ArrayList<Track>)Library.trackList.clone();
        ArrayList<Track> toRemove = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

        for(Track track : playbackQueue) {
            double rank_parameter_dist = 0.00;
            double rank_parameter_time = 0.00;
            double rank_parameter_day = 0.00;
            double rank_parameter_user_vote = 0.00;
            double current_total_score = 0.00;

            if (track.getUserVote() == -1 || !track.getHasBeenPlayed()) {
                toRemove.add(track);
            }

            // compute distance score
            if (track.getLastLatitude() != 90.00 && track.getLastLongitude() != 0.00) {
                Location userLoc = new Location("");
                userLoc.setLatitude(userLatitude);
                userLoc.setLongitude(userLongitude);

                Location trackLoc = new Location("");
                trackLoc.setLatitude(track.getLastLatitude());
                trackLoc.setLongitude(track.getLastLongitude());

                float distance = userLoc.distanceTo(trackLoc);

                if (distance < 300) { // 1000 feet
                    rank_parameter_dist += 30;
                } else if (distance < 2000) { // 1 mile
                    rank_parameter_dist += 20;
                } else if (distance < 5000) { // 3 miles
                    rank_parameter_dist += 10;
                } else if (distance < 10000) { // 6 miles
                    rank_parameter_dist += 5;
                }
            }

            // compute day score
            if (track.getLastDay() != -1) {
                rank_parameter_day = 7 - Math.abs(track.getLastDay() - cal.get(Calendar.DAY_OF_WEEK));
            }

            // compute time score
            if (track.getLastTime() != -1) {
                rank_parameter_time = 24 - Math.abs(track.getLastTime() - cal.get(Calendar.HOUR_OF_DAY));
            }

            // compute like/dislike score
            rank_parameter_user_vote = track.getUserVote() * 30;

            current_total_score = rank_parameter_dist + rank_parameter_time + rank_parameter_day + rank_parameter_user_vote;
            track.setTrackScore(current_total_score);

            System.out.println("Song Title: " + track.getTrackName() +
                    "\n, Total score: " + track.getTrackScore() +
                    "\n, Location score: " + rank_parameter_dist +
                    "\n, Day score: " + rank_parameter_day +
                    "\n, Time score: " + rank_parameter_time +
                    "\n, User vote score: " + rank_parameter_user_vote +
                    "\n, Has been played: " + track.getHasBeenPlayed());
        }

        playbackQueue.removeAll(toRemove);

        Collections.sort(playbackQueue);
    }

    /* Add a given track to the queue */
    public void addTrackToQueue(Track trackToAdd) {
        this.playbackQueue.add(trackToAdd);
    }

    /* Remove a given track from the queue */
    public void removeTrackFromQueue(Track trackToRemove) { this.playbackQueue.remove(trackToRemove); }

    public ArrayList<Track> getPlaybackQueue() { return this.playbackQueue; }
}
