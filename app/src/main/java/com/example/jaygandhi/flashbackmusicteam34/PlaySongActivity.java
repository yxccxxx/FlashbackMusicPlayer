package com.example.jaygandhi.flashbackmusicteam34;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/*
 * This Activity plays songs and display the related information about the song.
 */
public class PlaySongActivity extends AppCompatActivity {

    private static final String TAG = "PlaySongActivity";

    private TextView trackNameView;
    private TextView artistNameView;
    private TextView albumNameView;
    private TextView latView;
    private TextView longView;
    private TextView timeView;
    private Button pauseplayButton;
    private Button like_dislike;

    private UserVoteBehavior userVoteBehavior;
    private LocationManager locationManager;

    private static MediaPlayer mediaPlayer;

    private SharedPreferences statusPref;
    private ArrayList<Track> trackArrayList;
    private int curTrackPosition;
    private int curTrackID;
    private Track curTrack;
    private boolean isFlashbackMode;

    private Location curLocation;
    private SharedPreferences modeBeforeExitPref;
    private boolean curLocSet;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null && ! curLocSet) {
                curLocation = location;
                SharedPreferences latitudePref = getSharedPreferences("latitude_data", MODE_PRIVATE);
                SharedPreferences longitudePref = getSharedPreferences("longitude_data", MODE_PRIVATE);
                curTrack.setLastLocation(curLocation.getLatitude(), curLocation.getLongitude(),
                        latitudePref, longitudePref);
                Log.d(TAG, "onLocationChanged: update location for track " + curTrack.getTrackName());
                Log.d(TAG, "onLocationChanged: updated location is " +
                        location.getLongitude() + ", " + location.getLatitude());
                curLocSet = true;
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        Intent i = getIntent();
        trackArrayList = (ArrayList<Track>) i.getSerializableExtra("track_list");

        curTrackPosition = (int) i.getSerializableExtra("track_position");
        isFlashbackMode = (boolean) i.getSerializableExtra("is_flashback_mode");
        modeBeforeExitPref = getSharedPreferences("last_mode", MODE_PRIVATE);


        updateTrack();
        prepareMediaPlayer();


        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);

            Log.d("test1", "in");
        }
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.GPS_PROVIDER;
        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.d(TAG, "onCreate: check location: " + loc.getLatitude() + ", " + loc.getLongitude());

        trackNameView = findViewById(R.id.trackname_view);
        artistNameView = findViewById(R.id.artistname_view);
        albumNameView = findViewById(R.id.albumname_view);
        like_dislike = findViewById(R.id.like_dislike);
        statusPref = getSharedPreferences("track_status", MODE_PRIVATE);
        setupLikeDislike();
        setupPlayPause();
        setVolumeBar();

        latView = findViewById(R.id.latitude);
        longView = findViewById(R.id.longitude);
        timeView = findViewById(R.id.time_day);

        displaySongInfo();

        // make sure the song's historical info updates after displaySongInfo is called
        // handleSongLocationUpdates();
        handleSongTimeUpdates();

        Log.d(TAG, "onCreate: getting gps data");
        if (! getGPSData()) {
            Log.d(TAG, "onCreate: trying to get gps data again after gaining permission");
            getGPSData();
        }

    }

    private void updateTrack() {
        curTrackID = (Integer) trackArrayList.get(curTrackPosition).getTrackId();
        curTrack = Library.idsToSongs.get(curTrackID);
        curTrack.setHasBeenPlayed(true);
        curLocSet = false;

        loadMedia(curTrackID);

        Log.d(TAG, "updateTrack: updated " + curTrackID + "  " + curTrack.getTrackName());
    }

    private void handleSongTimeUpdates() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

        SharedPreferences timePref = getSharedPreferences("time_data", MODE_PRIVATE);

        curTrack.setLastEpochMillis(cal.getTimeInMillis(), timePref);
        curTrack.setLastDay(cal.get(Calendar.DAY_OF_WEEK));
        Log.d(TAG, "handleSongTimeUpdates: update track " + curTrack.getTrackName() + " lastDay=" + curTrack.getLastDay());
        curTrack.setLastTime(cal.get(Calendar.HOUR_OF_DAY));
        Log.d(TAG, "handleSongTimeUpdates: update track " + curTrack.getTrackName() + " lastTime=" + curTrack.getLastTime());
        curTrack.setHasBeenPlayed(true);
        Log.d(TAG, "handleSongTimeUpdates: update track " + curTrack.getTrackName() + " hasBeenPlayed=true");
        curTrack.setLastDate(cal);
        Log.d(TAG, "handleSongTimeUpdates: update track " + curTrack.getTrackName() + " lastDate");
    }


    /*
     * Load a song by its resourceId
     */
    public void loadMedia(int resourceId){
        if (PlaySongActivity.mediaPlayer == null) {
            Log.d("STATE", "mediaPlayer is null!");
            PlaySongActivity.mediaPlayer = new MediaPlayer();
        } else {
            PlaySongActivity.mediaPlayer.stop();
            PlaySongActivity.mediaPlayer.release();
            PlaySongActivity.mediaPlayer = new MediaPlayer();
        }

        try{
            this.mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().toString() +
                    "/Download/" + resourceId + ".mp3");
            this.mediaPlayer.prepareAsync();
        } catch (Exception e){}


        /*
        AssetFileDescriptor assetFileDescriptor;
        try {
            assetFileDescriptor = this.getResources().openRawResourceFd(resourceId);

            try {
                PlaySongActivity.mediaPlayer.prepareAsync();
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        } catch (Exception e){
            // Read songs in download folder
            try {
                assetFileDescriptor = this.getAssets().openFd(Environment.getExternalStorageDirectory().toString() +
                        "/Download/" + Integer.toString(resourceId) + ".mp3");
                PlaySongActivity.mediaPlayer.setDataSource(assetFileDescriptor);
                PlaySongActivity.mediaPlayer.prepareAsync();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        */

    }


    /*
     * launches the flashback mode
     */
    public void launchFlashBackMode() {
        Intent flashbackIntent = new Intent(this, PlayFlashbackActivity.class);
        startActivity(flashbackIntent);
    }

    // Get the media player ready
    private void prepareMediaPlayer() {

        PlaySongActivity.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub

                mp.start();
                if(trackArrayList.get(curTrackPosition).getUserVote() == TrackStatus.DISLIKE){
                    Log.d("STATE", "This song is disliked");
                    mp.stop();
                    PlaySongActivity.mediaPlayer.release();
                    PlaySongActivity.mediaPlayer = null;
                    pauseplayButton.setText("PLAY"); // Stop the song if it's disliked
                } else {
                    // Neutral or liked
                    // Make sure the oncompletion is set after start
                    PlaySongActivity.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Log.d("STATE", "compeleted");
                        }
                    });
                }

            }
        });
    }

    private void playNextSong(){
        int oldTrackPosition = curTrackPosition;
        if (curTrackPosition < trackArrayList.size() - 1) {
            curTrackPosition = skipOverDislikedSongs(curTrackPosition, trackArrayList);
            updateTrack();
            prepareMediaPlayer();
            displaySongInfo();
        } else {
            curTrackPosition = -1; // add 1 in the helper method below --> 0 --> first track in album
            curTrackPosition = skipOverDislikedSongs(curTrackPosition, trackArrayList);
            updateTrack();
            prepareMediaPlayer();
            displaySongInfo();
        }
    }

    /*
     * A helper method to skip over the disliked songs inside a track
     */
    public static int skipOverDislikedSongs(int oldTrackPosition, ArrayList<Track> trackArrayList ){
        int newTrackPosition = oldTrackPosition;
        do{
            newTrackPosition++;
            if(newTrackPosition >= trackArrayList.size()){
                newTrackPosition = 0;
            }
            Log.d("STATE", "skip");
            // Keep looping when it's disliked or the same song as the last one.
        }while( newTrackPosition != oldTrackPosition &&
                trackArrayList.get(newTrackPosition).getUserVote() == TrackStatus.DISLIKE );

        if( trackArrayList.get(newTrackPosition).getUserVote() == TrackStatus.DISLIKE ){
            // All song are disliked,
            return oldTrackPosition;
        }
        return newTrackPosition;
    }

    private void setVolumeBar() {
        final SeekBar bar = (SeekBar) findViewById(R.id.volume_bar);
        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        bar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        bar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        Log.d(TAG, "setVolumeBar: set up the volume bar. the max volume is " + bar.getMax()
                + "; current volume is " + bar.getProgress());

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
                Log.d(TAG, "onProgressChanged: volume changed to " + bar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    /*
     * Helper method to set song info texts
     */
    private void displaySongInfo() {
        trackNameView.setText(curTrack.getTrackName());
        artistNameView.setText(curTrack.getArtistName());
        albumNameView.setText(curTrack.getAlbumName());

        if (curTrack.getLastLatitude() != 0.00 && curTrack.getLastLongitude() != 0.00) {
            latView.setText(String.format("Lat: %s", Double.toString(curTrack.getLastLatitude())));
            longView.setText(String.format("Long: %s", Double.toString(curTrack.getLastLongitude())));
        }

        if (curTrack.getLastDate() != null) {
            Log.d(TAG, "displaySongInfo: Setting TextViews for Time");
            Calendar cal = curTrack.getLastDate();
            cal.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
            String date = String.format("%d--%d--%d  %d:%d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
            timeView.setText(date);
        } else {
            Log.d(TAG, "displaySongInfo: NOT setting Textviews for Time\n" +
                    "Song Time: " + curTrack.getLastTime() + "\nSong Day: " + curTrack.getLastDay());
        }
    }

    /*
     * Helper method to set up like and dislike components
     */
    private void setupLikeDislike() {
        userVoteBehavior = new UserVoteBehavior(curTrack);
        userVoteBehavior.updateVoteButton(like_dislike);
        LikeDislikeButtonListener likeDislikeButtonListener = new LikeDislikeButtonListener();
        like_dislike.setOnClickListener(likeDislikeButtonListener);
    }

    /*
     * Helper method to set up play-pause button
     */
    private void setupPlayPause() {
        pauseplayButton = findViewById(R.id.pauseplay_button);
        pauseplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(trackArrayList.get(curTrackPosition).getUserVote() == TrackStatus.DISLIKE)
                    return;
                if (PlaySongActivity.mediaPlayer.isPlaying()) {
                    PlaySongActivity.mediaPlayer.pause();
                    pauseplayButton.setText(R.string.play);
                } else {
                    if( PlaySongActivity.mediaPlayer != null ){
                        PlaySongActivity.mediaPlayer.start();
                    } else {
                        updateTrack(); // If a song gets liked/neturalized from disliked, we should be able to play it again
                        prepareMediaPlayer();
                    }
                    pauseplayButton.setText(R.string.pause);
                }

            }
        });
    }

    private class LikeDislikeButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Log.d("STATE", "Clicked on Like/Dislike Button");
            String curStatusStr = like_dislike.getText().toString();
            int nextStatusID;
            int nextStatus;
            if (curStatusStr.equals(getString(R.string.status_neutral))) {
                nextStatus = TrackStatus.LIKE;
                nextStatusID = R.string.status_like;
                Log.d("STATE", "This song changes to " + nextStatus);
            } else if (curStatusStr.equals(getString(R.string.status_like))) {
                nextStatus = TrackStatus.DISLIKE;
                nextStatusID = R.string.status_dislike;
                Log.d("STATE", "This song changes to " + nextStatus);
            } else {
                nextStatus = TrackStatus.NEUTRAL;
                nextStatusID = R.string.status_neutral;
                Log.d("STATE", "This song changes to " + nextStatus);
            }
            like_dislike.setText(nextStatusID);
            curTrack.setUserVote(nextStatus);
            SharedPreferences.Editor editor = statusPref.edit();
            editor.putInt(Integer.toString(curTrackID), nextStatus);
            editor.apply();
            Log.d(TAG, "onClick: the song " + curTrack.getTrackName() + " changes status to " + like_dislike.getText().toString());
            if (nextStatus == TrackStatus.DISLIKE){
                playNextSong(); // When a song gets disliked when being played, immediately play the next one
                like_dislike.setText(R.string.status_neutral);
                userVoteBehavior = new UserVoteBehavior(trackArrayList.get(curTrackPosition));
                userVoteBehavior.updateVoteButton(like_dislike);
            }
        }
    }

    private boolean getGPSData() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
            Log.d("PlaySongActivity get Location Permission","Ask for permission from user");
            return false;
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        Log.d(TAG, "getGPSData: set up the location manager and location listener, wait for the location update");
        return true;
    }

    @Override
    public void onBackPressed(){
        if(isFlashbackMode){
            SharedPreferences.Editor editor = modeBeforeExitPref.edit();
            editor.putBoolean("in_flashback_mode", false);
            editor.apply();
        }
        super.onBackPressed();
    }


}
