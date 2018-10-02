package com.example.jaygandhi.flashbackmusicteam34;

import android.content.res.AssetFileDescriptor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;
import java.util.Collections;

/*
 * This activity helps
 */
public class PlayFlashbackActivity extends AppCompatActivity {
    Location mlocation;
    private Button backButton;
    private MediaPlayer mediaPlayer;
    private Button like_dislike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_flashback);

        backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        like_dislike = (Button) findViewById(R.id.like_dislike);
        like_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(like_dislike.getText().toString().equals(R.string.status_neutral)) {
                    like_dislike.setText(R.string.status_like);
                } else if(like_dislike.getText().toString().equals(R.string.status_like)) {
                    like_dislike.setText(R.string.status_dislike);
                } else {
                    like_dislike.setText(R.string.status_neutral);
                }
            }
        });

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mlocation = location;
                //new loc
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

        // Create queue and fill queue with songs
     /*   FlashbackQueue activityFlashbackQueue = new FlashbackQueue();
        double rank_parameter_dist = 0.00;
        double rank_parameter_time = 0.00;
        double rank_parameter_user_vote = 0.00;
        double current_total_score = 0.00;
        for(Track track: FlashbackQueue.playbackQueue) {
            // this is stub code, a lot of shit needs to be implemented
            rank_parameter_dist = computeDist(getUserLocation(), track.getLastLocation());
            if(getTimeOfDay().equals(track.getLastTimeOfDay())) {
                rank_parameter_time = 1.00;
            } else {
                rank_parameter_time = 0.00;
            }

            if(track.getUserVote() == 1) {
                rank_parameter_user_vote = 1.00;
            } else {
                rank_parameter_user_vote = 0.00;
            }

            current_total_score =
                    rank_parameter_dist + rank_parameter_time + rank_parameter_user_vote;

            track.setTrackScore(current_total_score);
        }

        Collections.sort(FlashbackQueue.playbackQueue);
        for(Track track: FlashbackQueue.playbackQueue) {
            loadMedia(track.getTrackId());
            mediaPlayer.start();
        } */
    }

    public void loadMedia(int resourceId) {
        if(mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        AssetFileDescriptor assetFileDescriptor =
                this.getResources().openRawResourceFd(resourceId);
        try {
            mediaPlayer.setDataSource(assetFileDescriptor);
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(isChangingConfigurations() && mediaPlayer.isPlaying()) {
            ; // "do nothing"
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mediaPlayer.release();
    }

    /**
     *
     * @param firstLocation the first location
     * @param secondLocation the second location
     * @return the distance between the first and second location.
     */
    public double computeDist(Location firstLocation, Location secondLocation) {
        return 0.00;
    }

    public Location getUserLocation() {
        return (Location)null;
    }

    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }

}
