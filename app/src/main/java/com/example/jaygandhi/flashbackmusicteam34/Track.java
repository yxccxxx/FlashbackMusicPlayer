package com.example.jaygandhi.flashbackmusicteam34;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.location.Location;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by jaygandhi on 2/13/18.
 */

//enum TimeOfDay {MORNING, EVENING, NIGHT}
public class Track implements Serializable, Comparable {

    private Integer trackId;
    private String trackURL;

    private boolean isAlbumSong;
    private Integer albumId;

    private String artistName;
    private String trackName;
    private String albumName;
    private String albumArtist;

    private int userVote;
    private double trackScore;
    private double lastLatitude;
    private double lastLongitude;

    private int lastDay;
    private int lastTime;
    private Calendar lastDate;
    private long epochMillis;

    private String lastUser;

    private boolean hasBeenPlayed;

    public Track(Integer trackId) {
        this.trackId = trackId;
        this.trackScore = 0.00;
        this.userVote = 0;
        this.lastDate = null;
        this.lastLongitude = 0.00;
        this.lastLatitude = 90.00;
        this.hasBeenPlayed = false;
        this.epochMillis = 0;
        this.lastDay = -1;
        this.lastTime = -1;
        this.lastUser = "You";
        this.isAlbumSong = false;
        this.albumId = null;
    }

    public String getTrackURL() {
        return trackURL;
    }

    public long getEpochMillis() {
        return epochMillis;
    }

    public void setTrackURL(String trackURL) {
        this.trackURL = trackURL;
    }

    public void setTrackName(String tran) {
        this.trackName = tran;
    }

    public void setAlbumName(String albn) {
        this.albumName = albn;
    }

    public void setArtistName(String artn) {
        this.artistName = artn;
    }

    public void setTrackScore(double trackScore) {
        this.trackScore = trackScore;
    }

    public void setLastLocation(double latitude, double longitude, SharedPreferences latitudePref,
                                SharedPreferences longitudePref) {
        this.lastLatitude = latitude;
        this.lastLongitude = longitude;

        SharedPreferences.Editor editor = latitudePref.edit();
        editor.putFloat(Integer.toString(trackId), (float)latitude);
        editor.apply();

        editor = longitudePref.edit();
        editor.putFloat(Integer.toString(trackId), (float)longitude);
        editor.apply();
    }

    public void setLastLocation(double latitude, double longitude) {
        this.lastLatitude = latitude;
        this.lastLongitude = longitude;
    }

    public void setUserVote(int userVote) {
        this.userVote = userVote;
    }

    public double getLastLatitude() {
        return this.lastLatitude;
    }

    public double getLastLongitude() {
        return this.lastLongitude;
    }

    public double getTrackScore() {
        return this.trackScore;
    }

    public int getTrackId() {
        return this.trackId;
    }

    public int getUserVote() {
        return this.userVote;
    }

    public void setAlbumArtist(String albart) { this.albumArtist = albart; }

    public String getTrackName() { return this.trackName; }

    public String getAlbumName() { return this.albumName; }

    public String getArtistName() { return this.artistName; }

    public String getAlbumArtist() { return this.albumArtist; }

    public void setLastEpochMillis(long epochMillis, SharedPreferences pref) {
        this.epochMillis = epochMillis;
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(Integer.toString(trackId), epochMillis);
        editor.apply();
    }

    public void setLastEpochMillis(long epochMillis) {
        this.epochMillis = epochMillis;
    }

    public Calendar getLastDate() {
        return lastDate;
    }

    public int getLastDay() {
        return lastDay;
    }

    public int getLastTime() { return lastTime; }

    public void setLastDate(Calendar lastDate) { this.lastDate = lastDate; }

    public void setLastDay(int lastDay) { this.lastDay = lastDay; }

    public void setLastTime(int lastTime) { this.lastTime = lastTime; }


    public long getLastEpochMillis() { return this.epochMillis; }

    public void setHasBeenPlayed(boolean playedFlag) { this.hasBeenPlayed = playedFlag; }

    public boolean getHasBeenPlayed() { return this.hasBeenPlayed; }

    /**
     *
     * @param anotherTrack track being compared to
     * @return 1 if calling track has higher score, 0  if equal, -1 if lesser
     * @throws ClassCastException
     */
    public int compareTo(Object anotherTrack) throws ClassCastException {
        if (!(anotherTrack instanceof Track))
            throw new ClassCastException("A track object expected.");
        double anotherTrackScore = ((Track) anotherTrack).getTrackScore();
        if(this.trackScore - anotherTrackScore > 0) {
            return -1;
        } else if(this.trackScore - anotherTrackScore < 0) {
            return 1;
        } else {
            /*
            // check user vote
            if(this.getUserVote() > ((Track) anotherTrack).getUserVote()){
                return 1;
            }else if(this.getUserVote() < ((Track) anotherTrack).getUserVote()){
                return -1;
            }
            */

            // check recently played
            if(this.getLastEpochMillis() - ((Track) anotherTrack).getLastEpochMillis() > 0){
                return -1;
            }else {
                return 1;
            }
        }
    }

    /*
    // setters and getters for time behavior
    public iTime getLastTimeStamp() {
        return lastTimeStamp;
    }

    public void setLastTimeStamp(iTime lastTimeStamp) {
        this.lastTimeStamp = lastTimeStamp;
    }

    public void setTimeBehavior(iTime timeObj) {
        lastTimeStamp = timeObj;
    }

    // setters and getters for location behavior
    public iLocation getLastLocationStamp() {
        return lastLocationStamp;
    }

    public void setLastLocationStamp(iLocation lastLocationStamp) {
        this.lastLocationStamp = lastLocationStamp;
    }

    public void setLocationBehavior(iLocation locationObj) {
        lastLocationStamp = locationObj;
    }
    */

    public void setLastUser(String username) {
        this.lastUser = username;
    }

    public String getLastUser() {
        return this.lastUser;
    }

    public boolean getIsAlbumSong(){
        return this.isAlbumSong;
    }

    public void setIsAlbumSong(boolean isAlbumSong){
        this.isAlbumSong = isAlbumSong;
    }

    public Integer getAlbumId(){
        return this.albumId;
    }

    public void setAlbumId(Integer albumId){
        this.albumId = albumId;
    }
}
