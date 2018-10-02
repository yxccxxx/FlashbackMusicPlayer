package com.example.jaygandhi.flashbackmusicteam34;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

/**
 * Created by yidongluo on 3/15/18.
 */

public class MediaAdapter {

    private static MediaPlayer mediaPlayer;


    public MediaAdapter(){
        mediaPlayer = new MediaPlayer();
    }

    public void playSong(PlaybackQueue pbq, AssetFileDescriptor afd){
        int curTrackID = (Integer) pbq.getCurTrack().getTrackId();
        loadMedia(curTrackID, afd);
        prepareMediaPlayer(pbq.getCurTrack());
    }

    private void prepareMediaPlayer(final Track curTrack) {

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub

                mp.start();
                if(curTrack.getUserVote() == TrackStatus.DISLIKE){
                    Log.d("STATE", "This song is disliked");
                    mp.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    // pauseplayButton.setText("PLAY"); // Stop the song if it's disliked
                } else {
                    // Neutral or liked
                    // Make sure the oncompletion is set after start
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Log.d("STATE", "compeleted");
                        }
                    });
                }

            }
        });
    }

    public void loadMedia(int resourceId, AssetFileDescriptor assetFileDescriptor) {
        if (mediaPlayer == null) {
            Log.d("STATE", "mediaPlayer is null!");
            mediaPlayer = new MediaPlayer();
        } else {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = new MediaPlayer();
        }

        try {
            mediaPlayer.setDataSource(assetFileDescriptor);
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
