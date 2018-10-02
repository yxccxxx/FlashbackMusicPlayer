package com.example.jaygandhi.flashbackmusicteam34;

import android.*;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class PlaybackActivity extends AppCompatActivity {

    private static final String TAG = "PlaybackActivity";

    private PlaybackQueue pbq;
    private ListView listView;
    private MediaAdapter mediaAdapter;

    private TextView trackNameView;
    private TextView artistNameView;
    private TextView albumNameView;
    private TextView latView;
    private TextView longView;
    private TextView timeView;
    private TextView userView;

    private Button pauseplayButton;
    private Button likeDislikeButton;
    private Button skipSongButton;
    private DatabaseManager databaseManager;

    private LocationManager locationManager;
    private Location curLocation;

    public static ArrayList<Map<String, Object>> databaseList;
    private static MediaPlayer mediaPlayer;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Track curTrack = pbq.getCurTrack();
            if (curTrack == null || location == null) {
                return;
            }
            curLocation = location;
            curTrack.setLastLocation(location.getLatitude(), location.getLongitude());
            Log.d(TAG, "onLocationChanged: update location " +
                    location.getLatitude() + ", " + location.getLongitude());
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
        setContentView(R.layout.activity_playback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        String mode = i.getStringExtra("mode");
        if(mode.equals("track mode")) {
            pbq = new TrackQueue();
        } else {
            // For debugging and checking
            Bundle bund = this.getIntent().getBundleExtra("list_bundle");
            databaseList = (ArrayList<Map<String, Object>>)
                    bund.getSerializable("record_list");

            for(Map<String, Object> record: databaseList) {
                System.out.println(record.get("title"));
            }

            pbq = new VibeQueue();

        }

        viewList();
        setupComponents();

        //databaseManager = new DatabaseManager(this);

        // get user permission for loaction
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
            Log.d("PlaybackActivity get Location Permission","Ask for permission from user");
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }

    private void setupComponents() {
        trackNameView = (TextView) findViewById(R.id.trackname_view);
        artistNameView = (TextView) findViewById(R.id.artistname_view);
        albumNameView = (TextView) findViewById(R.id.albumname_view);
        latView = (TextView) findViewById(R.id.lastlat_view);
        longView = (TextView) findViewById(R.id.lastlong_view);
        timeView = (TextView) findViewById(R.id.lasttime_view);
        userView = (TextView) findViewById(R.id.lastuser_view);

        pauseplayButton = (Button) findViewById(R.id.play_btn);
        pauseplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Just for testing DB
                // writeToDataBase();

                if(pauseplayButton.getText().equals(">")) {
                    pauseplayButton.setText("||");
                    PlaybackActivity.mediaPlayer.start();
                } else {
                    pauseplayButton.setText(">");
                    PlaybackActivity.mediaPlayer.pause();
                }
            }
        });

        likeDislikeButton = findViewById(R.id.like_dislike_btn);
        likeDislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(likeDislikeButton.getText().equals("+")) {
                    likeDislikeButton.setText("✓");
                    pbq.getCurTrack().setUserVote(TrackStatus.LIKE);
                } else if(likeDislikeButton.getText().equals("✓")) {
                    likeDislikeButton.setText("X");
                    pbq.getCurTrack().setUserVote(TrackStatus.DISLIKE);
                    // skip to next song
                } else {
                    likeDislikeButton.setText("+");
                    pbq.getCurTrack().setUserVote(TrackStatus.NEUTRAL);
                }
            }
        });

        skipSongButton = findViewById(R.id.skip_btn);
        skipSongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // skip to next song
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_playback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sort_title) {
            pbq.setSortBehavior(new TitleSort());
        } else if (id == R.id.sort_album) {
            pbq.setSortBehavior(new AlbumSort());
        } else if (id == R.id.sort_artist) {
            pbq.setSortBehavior(new ArtistSort());
        } else if (id == R.id.sort_favorites) {
            pbq.setSortBehavior(new FavoriteSort());
        }

        pbq.sortQueue();
        viewList();

        return super.onOptionsItemSelected(item);
    }

    // https://stackoverflow.com/questions/4709870/setonitemclicklistener-on-custom-listview
    public void viewList() {
        final ArrayList<String> queueTitles = pbq.getQueueTitles();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.activity_listview,queueTitles);

        listView = (ListView) findViewById(R.id.number_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateTrack(position);
                pbq.setCurTrack(position);
                viewList();


                /**
                mediaAdapter = new MediaAdapter();
                try {
                    System.out.println(Environment.getExternalStorageDirectory().toString() +
                            "/Download/" + Integer.toString(pbq.getCurTrack().getTrackId()) + ".mp3");
                    AssetFileDescriptor assetFileDescriptor = PlaybackActivity.this.getAssets().openFd(Environment.getExternalStorageDirectory().toString() +
                            "/Download/" + Integer.toString(pbq.getCurTrack().getTrackId()) + ".mp3");
                    mediaAdapter.playSong(pbq, assetFileDescriptor);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                 */
            }
        });
    }

    public void writeToDataBase() {
        Track curTrack = pbq.getCurTrack();
        TrackRecord record = new TrackRecord();
        record.setResourceID(curTrack.getArtistName() + "-" + curTrack.getTrackName());
        record.setTitle(curTrack.getTrackName());
        record.setLatitude(curTrack.getLastLatitude());
        record.setLongitude(curTrack.getLastLongitude());
        record.setTime(curTrack.getLastEpochMillis());
        record.setUser(LoginActivity.userEmail);
        curTrack.setTrackURL("test.com");
        record.setUrl(curTrack.getTrackURL());

        databaseManager.writeToDatabase(record);
    }

    public void updateTrack(int position) {
        pauseplayButton.setText("||");
        pbq.setCurTrack(position);
        viewList();
        displaySongInfo();
        loadMedia(pbq.getCurTrack().getTrackId());
        prepareMediaPlayer();
    }

    /*
     * Load a song by its resourceId
     */
    public void loadMedia(int resourceId) {
        if (this.mediaPlayer == null) {
            Log.d("STATE", "mediaPlayer is null!");
            this.mediaPlayer = new MediaPlayer();
        } else {
            this.mediaPlayer.stop();
            this.mediaPlayer.release();
            this.mediaPlayer = new MediaPlayer();
        }

        AssetFileDescriptor assetFileDescriptor = null;
        //AssetFileDescriptor assetFileDescriptor = this.getResources().openRawResourceFd(resourceId);

//        try {
//            System.out.println("creating asset file descriptor");
//            assetFileDescriptor = PlaybackActivity.this.getAssets().openFd(Environment.getExternalStorageDirectory().toString() +
//                    "/Download/" + Integer.toString(pbq.getCurTrack().getTrackId()) + ".mp3");
//        } catch(Exception e) {
//            e.printStackTrace();
//        }

        try {
            this.mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().toString() +
                    "/Download/" + Integer.toString(pbq.getCurTrack().getTrackId()) + ".mp3");
            this.mediaPlayer.prepareAsync();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    // Get the media player ready
    private void prepareMediaPlayer() {

        this.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub

                mp.start();
                if(pbq.getCurTrack().getUserVote() == TrackStatus.DISLIKE){
                    Log.d("STATE", "This song is disliked");
                    mp.stop();
                    PlaybackActivity.mediaPlayer.release();
                    PlaybackActivity.mediaPlayer = null;
                    pauseplayButton.setText("PLAY"); // Stop the song if it's disliked
                } else {
                    // Neutral or liked
                    // Make sure the oncompletion is set after start
                    PlaybackActivity.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Log.d("STATE", "compeleted");
                        }
                    });
                }

            }
        });
    }

    private void displaySongInfo() {
        return;
    }


}

