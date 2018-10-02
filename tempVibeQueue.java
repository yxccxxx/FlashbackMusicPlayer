package com.example.jaygandhi.flashbackmusicteam34;

import android.app.DownloadManager;
import android.location.Location;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by thinmintz on 3/14/18.
 */

public class VibeQueue extends PlaybackQueue {

    public VibeQueue() {
        // create vibe queue based on track scores
        super.setSortBehavior(new VibeSort());
        this.queue = createQueue();
    }


    private ArrayList<Track> createQueue () {
        ArrayList<Map<String, Object>> database = PlaybackActivity.databaseList;
        ArrayList<Track> queue = new ArrayList<>();

        for(Map<String, Object> record: database) {
            ArrayList<String> toDownload = new ArrayList<String>();

            double latitude = Double.parseDouble(record.get("latitude").toString());
            double longitude = Double.parseDouble(record.get("latitude").toString());
            String resourceID = (String)record.get("resourceID");
            long time = Long.parseLong(record.get("time").toString());
            String title  = (String)record.get("title");
            String url= (String)record.get("url");
            String user = (String)record.get("user");

            double current_total_score = 0.00;
            Track trackToScore = null;

            if(!HomeActivity.trackNames.contains(title)) {
                toDownload.add(url);
                current_total_score = -1;
                continue;
            }

            for(Track t : Library.trackList) {
                if(t.getTrackName().equals(title)) {
                    trackToScore = t;
                }
            }

            // compute location score
            Location userLoc = new Location("");
            userLoc.setLatitude(32.881955);
            userLoc.setLongitude(-117.234254);

            Location trackLoc = new Location("");
            trackLoc.setLatitude(latitude);
            trackLoc.setLongitude(longitude);

            float distance = userLoc.distanceTo(trackLoc);
            if(distance < 1000) {
                current_total_score += 1.2;
            }

            // compute time score
            iTime systemTime = new SystemTime();
            systemTime.setTimeStamp(0);
            if((systemTime.getTimeStamp() - time) > 604800000) {
                current_total_score += 1.10;
            }

            // compute friend score
            if(LoginActivity.emailAddressList.contains(user)) {
                current_total_score += 1.00;
            } else {
                if(!LoginActivity.displayNames.containsKey(user)) {
                    Random rand = new Random();
                    LoginActivity.displayNames.put(user, "User" + rand.nextInt(99999999) + 1);
                }
            }

            trackToScore.setTrackScore(current_total_score);
            queue.add(trackToScore);
        }

        Collections.sort(queue);
        for(Track t : queue) {
            System.out.println("Vibe Queue Track : " + t.getTrackName());
        }
        return queue;
    }

}

