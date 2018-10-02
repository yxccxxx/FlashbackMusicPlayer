package com.example.jaygandhi.flashbackmusicteam34;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Yixin on 2/13/18.
 *
 * This activity happens when click album name in album list.
 */
public class AlbumTrackActivity extends MusicListActivity {

    private static final String TAG = "AlbumTrackActivity";

    private ArrayList<Integer> trackIDs;
    private ArrayList<String> titles;
    private Album curAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        trackIDs = new ArrayList<>();
        titles = new ArrayList<>();

        Intent intent = getIntent();
        curAlbum = (Album) intent.getSerializableExtra("album_obj");

        setHeader(curAlbum.getAlbumName());

        for(int i = 0; i < curAlbum.getAlbumTracks().size(); i++){
            int trackID = curAlbum.getAlbumTracks().get(i).getTrackId();
            String title = curAlbum.getAlbumTracks().get(i).getTrackName();
            trackIDs.add(trackID);
            titles.add(title);
        }
        Log.d(TAG, "Get tracks inside the album");

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this, R.layout.activity_track_list, titles);
        listView.setAdapter(arrayAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        position--;     // exclude the header on the top // Now we need to include the header on the top
        if( position < 0 ){
            // User clicks on the album title, play all songs in this album starting from the first
            Toast.makeText(this, curAlbum.getAlbumName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, PlaySongActivity.class);
            intent.putExtra("track_list", curAlbum.getAlbumTracks());
            int curPos = -1;
            curPos = PlaySongActivity.skipOverDislikedSongs(curPos, curAlbum.getAlbumTracks());
            intent.putExtra("track_position", curPos);
            intent.putExtra("is_flashback_mode", false);
            startActivity(intent);
            Log.d(TAG, "onClick: switch to track play view");
            return;
        }

        final int tempPos = position;
        final String temp = titles.get(position);
        final Context current = this;
        this.runOnUiThread(new Runnable() {
            public void run() {

                Toast.makeText(current, temp, Toast.LENGTH_SHORT).show();
            }
        });


        Log.d(TAG, "onListItemClick: " + titles.get(position) + " selected; id="
                + trackIDs.get(position));

        Intent intent = new Intent(this, PlaySongActivity.class);
        intent.putExtra("track_list", curAlbum.getAlbumTracks());
        intent.putExtra("track_position", position);
        intent.putExtra("is_flashback_mode", false);
        startActivity(intent);
        Log.d(TAG, "onClick: switch to track play view");
    }

    public Album getCurAlbum(){
        return curAlbum;
    }
}