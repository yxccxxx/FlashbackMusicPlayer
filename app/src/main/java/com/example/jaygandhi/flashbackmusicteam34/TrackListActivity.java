package com.example.jaygandhi.flashbackmusicteam34;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Reference:
 * https://stackoverflow.com/questions/24354335/android-retrieve-music-file-information-from-raw-folder
 * This Activity displays a list of tracks and implements the on-click functionalities for them.
 */
public class TrackListActivity extends MusicListActivity {

    private static final String TAG = "TrackListActivity";

    private ArrayList<String> titles;
    private ArrayList<Integer> trackIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHeader("Songs");
        titles = new ArrayList<>();
        trackIDs = new ArrayList<>();

        for (Track track : Library.trackList) {
            titles.add(track.getTrackName());
            trackIDs.add(track.getTrackId());
        }
        Log.d(TAG, "onCreate: xxxxxxxxxx");
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this, R.layout.activity_track_list, titles);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        if (position == 0) {
            // header clicked
            return;
        }

        position--;     // exclude the header on the top

        final String temp = titles.get(position);
        final Context current = this;
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(current, temp, Toast.LENGTH_LONG).show();
            }
        });

        Log.d(TAG, "onListItemClick: " + titles.get(position) + " selected; id="
                + trackIDs.get(position));

        Intent intent = new Intent(this, PlaySongActivity.class);
        intent.putExtra("track_list", Library.trackList);
        intent.putExtra("track_position", position);
        intent.putExtra("is_flashback_mode", false);
        startActivity(intent);
        Log.d(TAG, "onClick: switch to track play view");
    }


}
