package com.example.jaygandhi.flashbackmusicteam34;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.webkit.MimeTypeMap;

import com.google.android.gms.plus.PlusOneButton;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by thinmintz17 on 3/16/18.
 */

public class VibeDownloadMediator extends AsyncTask<String, String, String> {
   private HashMap<String, String> downloadUrls;
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        // get reference to arraylist
        downloadUrls = VibeQueue.toDownload;
    }

    @Override
    protected String doInBackground(String... strings) {
        Iterator it = downloadUrls.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            new DownloadVibeSong().execute(pair.getKey().toString(), pair.getValue().toString());
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){


    }

    private class DownloadVibeSong extends AsyncTask<String, String, String> {
        DownloadManager downloadManager;
        DownloadSongActivity dlActivity;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dlActivity = new DownloadSongActivity();
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

            long Music_DownloadId = dlActivity.DownloadData(music_uri, song);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
        }


    }


}
