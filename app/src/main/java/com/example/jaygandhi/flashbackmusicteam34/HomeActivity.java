package com.example.jaygandhi.flashbackmusicteam34;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.firebase.database.DatabaseReference;
//import com.google.api.services.people.v1.model.EmailAddress;
//import com.google.api.services.people.v1.model.Name;
//import com.google.api.services.people.v1.model.Person;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

/*
 * The Home activity, containing Buttons like Albums/Songs that lead users to music page
 */
public class HomeActivity extends AppCompatActivity {

    /****************************************
     * Declarations for HomeActivity
     ****************************************/

    private static final String TAG = "HomeActivity";

    private Button trackBtn;
    private Button albumBtn;
    private Button remoteLibBtn;
    private Button vibeBtn;
    private Switch timeSwitch;
    private EditText mockTimeMillis;

    private LocationManager locationManager;
    public static Location curLocation;

    private boolean vibeFlag = false;
    private boolean openVibeByDefault = false;
    private boolean hasLocation = false;

    private SharedPreferences statusPref;
    private SharedPreferences modeBeforeExitPref;
    //private ArrayList<Friend> friendList;
    private ArrayList<Name> names;
    private ArrayList<EmailAddress> emails;
    public static ArrayList<String> trackNames = new ArrayList<String>();

    public static iTime currentTimeType = new SystemTime();


    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                Log.d(TAG, "onLocationChanged: update location: " +
                        location.getLongitude() + ", " + location.getLatitude());
                curLocation = location;
                hasLocation = true;
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {}

        @Override
        public void onProviderDisabled(String s) {}
    };

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view == trackBtn) {
                Intent intent = new Intent();
                //intent.setClass(HomeActivity.this, TrackListActivity.class);
                intent.putExtra("mode", "track mode");
                intent.setClass(HomeActivity.this, PlaybackActivity.class);
                startActivity(intent);
                Log.d(TAG, "onClick: switch to track list");
            }

            // when click the albums button, switch to AlbumActivity
            if(view == albumBtn){
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this, AlbumActivity.class);
                startActivity(intent);
                Log.d(TAG, "onClick: switch to album list");
            }

            // when click the flashback button, switch to flashback mode
            if(view == vibeBtn) {
                if (hasLocation) {
                    Log.d(TAG, "onClick: flashback button clicked, update location: " + curLocation.getLatitude() + ", " + curLocation.getLongitude());
                    Log.d(TAG, "onClick: PlaySongActivity will only appear when there "
                            + "are songs inside. Untestable non-trival method.");
                    SharedPreferences.Editor editor = modeBeforeExitPref.edit();
                    editor.putBoolean("in_flashback_mode", true);
                    editor.apply();

                    DatabaseManager manager = new DatabaseManager(HomeActivity.this);
                    manager.readFromDatabase();

                } else {
                    Log.d(TAG, "onClick: location not updated yet");
                    //DatabaseManager manager = new DatabaseManager(HomeActivity.this);
                    //manager.readFromDatabase();
                }
            }


            if(view == remoteLibBtn){
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this, DownloadSongActivity.class);
                startActivity(intent);
                Log.d(TAG, "launching remote library for download");
            }
        }
    }


    /****************************************
     * On Create for HomeActivity
     ****************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
            Log.d("test1","ins");
        }


        // Load music into Library from raw folder
        Library.curActivity = this;
        Library.loadSongs();
        extractMDFromSongFiles();
        Library.loadAlbums();
        Bundle loginBundle = this.getIntent().getBundleExtra("name_bundle");
//        if(loginBundle != null){
//            Log.d(TAG, "bundle is not null");
//            //friendList = (ArrayList<Friend>)loginBundle.getSerializable("connections");
//            System.out.println("friends List looks like: " + friendList.getClass());
//        }


        for(Friend friend: LoginActivity.friendsList) {
            System.out.println(friend.getEmail());
            System.out.println(friend.getName());
        }

        setButtonListeners();

        if (! getCurLocationUpdate()) {
            getCurLocationUpdate();
        }
        modeBeforeExitPref = getSharedPreferences("last_mode", MODE_PRIVATE);

        if( modeBeforeExitPref.getBoolean("in_flashback_mode", false) ){
            //openFlashBackByDefault = true;
        }

        timeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    currentTimeType = new MockTime(Long.parseLong(mockTimeMillis.getText().toString()));
                } else {
                    currentTimeType = new SystemTime();
                }
            }
        });
    }

    /****************************************
     * Method implementations for Home Activity
     ****************************************/

    private boolean getCurLocationUpdate(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
            Log.d("test1","ins");
            return false;
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        return true;
    }

    /*
     * Launch the flashback intent
     */
    public void launchFlashback() {
        System.out.println("lat: " + curLocation.getLatitude());

        FlashbackQueue fbQueue = new FlashbackQueue(curLocation.getLatitude(), curLocation.getLongitude());
        fbQueue.createQueue();

        if(fbQueue.getPlaybackQueue().size() == 0){
            Toast.makeText(HomeActivity.this, "FlashBack Mode " +
                    "unavailable: play songs or albums first", Toast.LENGTH_LONG).show();
        } else {
            Intent flashbackIntent = new Intent(HomeActivity.this, PlaySongActivity.class);
            flashbackIntent.putExtra("track_list", fbQueue.getPlaybackQueue());
            flashbackIntent.putExtra("track_position", 0);
            flashbackIntent.putExtra("is_flashback_mode", true);
            startActivity(flashbackIntent);
            Log.d(TAG, "onClick: switch to Flashback mode");
        }
    }

    /*
     * Helper method to set button listeners
     */
    private void setButtonListeners(){
        // when click the button SONGS
        trackBtn = findViewById(R.id.songs_btn);
        ButtonListener listener = new ButtonListener();
        trackBtn.setOnClickListener(listener);

        // when click the button ALBUMS
        albumBtn = findViewById(R.id.albums_btn);
        albumBtn.setOnClickListener(listener);

        remoteLibBtn = (Button) findViewById(R.id.remote_library_btn);
        remoteLibBtn.setOnClickListener(listener);

        vibeBtn = (Button) findViewById(R.id.vibe_mode_btn);
        vibeBtn.setOnClickListener(listener);

        timeSwitch = (Switch) findViewById(R.id.time_switch);

        mockTimeMillis = (EditText) findViewById(R.id.mocktime_input);
    }

    private void extractMDFromSongFiles(){
        // Extract metadata from song files
        SharedPreferences statusPref = getSharedPreferences("track_status", MODE_PRIVATE);
        SharedPreferences timePref = getSharedPreferences("time_data", MODE_PRIVATE);
        SharedPreferences longitudePref = getSharedPreferences("longitude_data", MODE_PRIVATE);
        SharedPreferences latitudePref = getSharedPreferences("latitude_data", MODE_PRIVATE);

        for (Track track : Library.trackList) {
            Uri path = Uri.parse("android.resource://" + getPackageName() + "/" + track.getTrackId());
            System.out.println("extracting md from id : " + track.getTrackId());
            MediaMetadataRetriever meta = new MediaMetadataRetriever();
            try {
                meta.setDataSource(this, path);
            } catch (IllegalArgumentException e){
                //File currSong = null;
//                if(track.videdld){
//                    path = Uri.parse(Environment.getExternalStorageDirectory().toString() + "/Download" + "/str" + track.getTrackId() + ".mp3");
//                    currSong = new File(Environment.getExternalStorageDirectory().
//                            toString() + "/Download" + "/str" + track.getTrackId() + ".mp3");
//                } else{
//                    path = Uri.parse(Environment.getExternalStorageDirectory().toString() + "/Download" + "/" + track.getTrackId() + ".mp3");
//                    currSong = new File(Environment.getExternalStorageDirectory().
//                            toString() + "/Download" + "/" + track.getTrackId() + ".mp3");
//                    System.out.println(path);
//                    meta.setDataSource(this, path);
//                }

                path = Uri.parse(Environment.getExternalStorageDirectory().toString() + "/Download" + "/" + track.getTrackId() + ".mp3");
                File currSong = new File(Environment.getExternalStorageDirectory().
                        toString() + "/Download" + "/" + track.getTrackId() + ".mp3");
                System.out.println(path);
                meta.setDataSource(this, path);

            }

            track.setTrackName(meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
            trackNames.add(meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
            track.setAlbumName(meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
            track.setArtistName(meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
            track.setAlbumArtist(meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST));

            int status = statusPref.getInt(Integer.toString(track.getTrackId()), TrackStatus.NEUTRAL);
            long epochMillis = timePref.getLong(Integer.toString(track.getTrackId()), 0);
            double longitude = longitudePref.getFloat(Integer.toString(track.getTrackId()), 0);
            double latitude = latitudePref.getFloat(Integer.toString(track.getTrackId()), 0);
            track.setLastEpochMillis(epochMillis, timePref);
            track.setLastLocation(latitude, longitude, latitudePref, longitudePref);
            track.setUserVote(status);

            if (epochMillis == 0) {
                track.setHasBeenPlayed(false);
                track.setLastDate(null);
                track.setLastDay(-1);
                track.setLastTime(-1);
            } else {
                Calendar lastDate = Calendar.getInstance();
                lastDate.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
                lastDate.setTimeInMillis(epochMillis);

                track.setLastDay(lastDate.get(Calendar.DAY_OF_WEEK));
                track.setLastTime(lastDate.get(Calendar.HOUR_OF_DAY));
                track.setHasBeenPlayed(true);
                track.setLastDate(lastDate);
            }
        }
    }
}