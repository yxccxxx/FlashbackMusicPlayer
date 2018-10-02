package com.example.jaygandhi.flashbackmusicteam34;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Yixin on 2/11/18.
 *
 * This activity happens when click ALBUMS button under HomeActivity.
 * it is already tested in the espresso test
 */
public class AlbumActivity extends MusicListActivity {
    private static final String TAG = "AlbumActivity";

    private ArrayList<String> albumNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHeader("Albums");
        albumNames = new ArrayList<>();

        Log.d(TAG, "onCreate: xxxxxxxxxx");

        // put album names into an arraylist correspondingly
        for(int i = 0; i < Library.albumList.size(); i++){
            albumNames.add(Library.albumList.get(i).getAlbumName());
        }

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this, R.layout.activity_track_list, albumNames);
        listView.setAdapter(arrayAdapter);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

      /*  if (position == 0) {
            // header clicked
            return;
        }

        position--;     // exclude the header on the top


        final int tempPos = position;
        final String temp = Library.albumList.get(position).getAlbumName();
        final Context current = this;
        this.runOnUiThread(new Runnable() {
            public void run() {

                Log.d("STATE", Integer.toString(tempPos));
                Toast.makeText(current, temp, Toast.LENGTH_LONG).show();
            }
        });


        Intent intent = new Intent(this, AlbumTrackActivity.class);
        intent.putExtra("album_obj", Library.albumList.get(position));
        startActivity(intent);
        Log.d(TAG, "onClick: switch to songs view");*/
    }


}
