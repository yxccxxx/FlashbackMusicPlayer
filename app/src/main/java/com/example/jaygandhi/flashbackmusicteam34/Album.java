package com.example.jaygandhi.flashbackmusicteam34;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Created by jaygandhi on 2/13/18.
 * This class defines the object Album
 */
public class Album implements Serializable {

    private String artistName;
    private String albumName;
    private ArrayList<Track> albumTracks;

    /*
     * Cntr that sets the album name & artist name
     */
    public Album(String albumName, String artistName) {
        this.albumName = albumName;
        this.artistName = artistName;
        albumTracks = new ArrayList<>();
    }

    /*
     * Add a new track to the album ( if it wasn't in the album before )
     */
    public void addTrack(Track t) {
        if (!albumTracks.contains(t)) {
            albumTracks.add(t);
        }
    }

    /* Remove a certain track from this album */
    public void removeTrack(Track t) { albumTracks.remove(t); }

    public void setArtistName(String artn) { this.artistName = artn; }

    public void setAlbumName(String albn) { this.albumName = albn; }

    public String getArtistName() { return this.artistName; }

    public String getAlbumName() { return this.albumName; }

    public ArrayList<Track> getAlbumTracks() { return this.albumTracks; }
}