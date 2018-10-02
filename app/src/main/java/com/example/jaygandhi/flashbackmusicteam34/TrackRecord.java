package com.example.jaygandhi.flashbackmusicteam34;

public class TrackRecord {

    private String resourceID;
    private String url;
    private String title;
    private String user;
    private long time;
    private double longitude;
    private double latitude;
    private String isAlbumSong;

    public TrackRecord(){
        resourceID = null;
        url = null;
        title = null;
        user = null;
        time = 0;
        longitude = 0.0;
        latitude = 0.0;
    }

    public String getIsAlbumSong() {
        return isAlbumSong;
    }

    public void setIsAlbumSong(String isAlbumSong) {
        this.isAlbumSong = isAlbumSong;
    }

    public String getResourceID() {
        return resourceID;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getUser() {
        return user;
    }

    public long getTime() {
        return time;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
