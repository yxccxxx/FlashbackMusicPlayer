package com.example.jaygandhi.flashbackmusicteam34;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.DrawableWrapper;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by thinmintz on 3/14/18.
 */

public class VibeQueue extends PlaybackQueue {
    public Context context;
    public static HashMap<String, String> toDownload = new HashMap<>(); // List of urls
    private DownloadManager downloadManager;
    public VibeQueue(Context context) {
        // create vibe queue based on track scores
        this.context = context;
        super.setSortBehavior(new VibeSort());
        this.queue = createQueue();
    }


    public ArrayList<Track> createQueue () {
        ArrayList<Map<String, Object>> database = PlaybackActivity.databaseList;
        ArrayList<Track> queue = new ArrayList<>();

        for(Map<String, Object> record: database) {
            double latitude = Double.parseDouble(record.get("latitude").toString());
            double longitude = Double.parseDouble(record.get("longitude").toString());
            String resourceID = (String)record.get("resourceID");
            long time = Long.parseLong(record.get("time").toString());
            String title  = (String)record.get("title");
            String url= (String)record.get("url");
            String user = (String)record.get("user");
            String isAlbumSong = (String)record.get("isAlbumSong");

            double current_total_score = 0.00;
            Track trackToScore = null;

            if(!HomeActivity.trackNames.contains(title)) {
                System.out.println("url --------->" + url);
                System.out.println("is album song ----->" + isAlbumSong );
                toDownload.put(url, isAlbumSong);
                // TODO: Call vibe mediator appropriately

                current_total_score = -1;
                continue;
            }

            for(Track t : Library.trackList) {
                if(t.getTrackName().equals(title)) {
                    trackToScore = t;
                }
            }


            // compute location score
            Location userLoc = HomeActivity.curLocation;
            //Location userLoc = new Location("");
            //userLoc.setLatitude(MockLocation.latitude);
            //userLoc.setLongitude(MockLocation.longitude);

            Location trackLoc = new Location("");
            trackLoc.setLatitude(latitude);
            trackLoc.setLongitude(longitude);

            float distance = userLoc.distanceTo(trackLoc);
            if(distance < 1000) {
                current_total_score += 1.2;
            }
            trackToScore.setLastLocation(latitude, longitude);

            // compute time score
            if((HomeActivity.currentTimeType.getTimeStamp() - time) < 604800000) {
                current_total_score += 1.10;
            }
            trackToScore.setLastEpochMillis(time);

            // compute friend score
            if((LoginActivity.emailAddressList.contains(user)) || (LoginActivity.userEmail.equals(user))) {
                current_total_score += 1.00;
            } else {
                if(!LoginActivity.displayNames.containsKey(user)) {
                    LoginActivity.displayNames.put(user, "User" + user.hashCode());
                }
            }
            trackToScore.setLastUser(LoginActivity.displayNames.get(user));

            trackToScore.setTrackScore(current_total_score);
            queue.add(trackToScore);
        }

        Collections.sort(queue);
        for(Track t : queue) {
            System.out.println("Vibe Queue Track : " + t.getTrackName());
        }

        System.out.println("size --------------------------->" + toDownload.size());
        Iterator it = toDownload.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(pair.getValue().toString().equals("FailedToFindURL") || pair.getKey().toString().equals("FailedToFindURL")){

            } else if (pair.getValue().toString().equals("true")){
                new VibeAsync().execute(pair.getKey().toString(), pair.getValue().toString());
                //new DownloadSongActivity().dld(pair.getKey().toString(), pair.getValue().toString());
                //new DownloadSongActivity.DownloadFileAsync().execute(pair.getKey().toString(), pair.getValue().toString());
            }

            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }


        return queue;
    }


    class VibeAsync extends AsyncTask<String, String, String> {

        String path = "";

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... aurl) {
            boolean song = true;
            InputStream input = null;
            OutputStream output = null;

            Uri music_uri = Uri.parse(aurl[0]);

            if(aurl[1].equals("false")){
                song = false;
            }

            String Music_DownloadId = DownloadData(music_uri, song);
            this.path = Music_DownloadId;
            return Music_DownloadId;
        }

        @Override
        protected void onPostExecute(String result) {
            /**
             * Find the song and put the song in vibe mode
             */

//            File songFile = new File(path);
//
//            String[] split = songFile.getName().split(".mp3");
//            System.out.println("absolute path ----->" + songFile.getAbsolutePath());
//            System.out.println("----------" + result);
//            for(String i: split) {
//                System.out.println(i);
//            }
//
//            String idStr = split[0];
//            int id = Integer.parseInt(idStr);
//
//            Track t = new Track(id);
//            queue.add(t);
        }


    }

    public String DownloadData (Uri uri, boolean song) {

        //String type = getMimeType(uri.toString());

        long downloadReference;
        String path = "0";
        // Create request for android download manager
        downloadManager = (DownloadManager)context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setTitle("Data Download");
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);
        request.setDescription("Android Data download using DownloadManager.");
        //request.setMimeType("audio/mpeg3");
        //request.setDestinationInExternalFilesDir(DownloadSongActivity.this,
        //Environment.DIRECTORY_DOWNLOADS ,"doesn'twork.mp3");


        // Assign a random number as its id(name)
        int randomID = (int)(Math.random() * 1000000);

        if(song){
            // Playback
            SharedPreferences sp = context.getSharedPreferences("urlPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Integer.toString(randomID), uri.toString());
            editor.apply();
            String path_1 = Environment.DIRECTORY_MUSIC;
            String path_2 = "str" + Integer.toString(randomID)+ ".mp3";
            path = Environment.getExternalStorageDirectory().toString() + "/Download/" + path_2;
            request.setDestinationInExternalPublicDir(path_1, path_2);
        } else {
            //SharedPreferences sp = context.getSharedPreferences("urlPref", MODE_PRIVATE);
            //SharedPreferences.Editor editor = sp.edit();
            //editor.putString(Integer.toString(randomID), uri.toString());
            //editor.apply();
            //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS , Integer.toString(randomID) + ".zip");
        }


        // File tempFile = new File(Environment.getExternalStorageDirectory().toString() + "/Download/tempName");

        //System.out.println("this type is : " + type); // doesn't work
        try {
            System.out.println("this name is : " + new URL(uri.toString()).getFile());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }



        downloadReference = downloadManager.enqueue(request);

        return path;
    }
    @Override
    public void setSortBehavior(SortBehavior sortBehavior) {
        return;
    }

}
