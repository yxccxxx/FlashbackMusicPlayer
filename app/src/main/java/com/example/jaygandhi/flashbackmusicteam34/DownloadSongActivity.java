package com.example.jaygandhi.flashbackmusicteam34;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;


import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadSongActivity extends AppCompatActivity {

    EditText urlBox;
    Button downloadSong;
    Button downloadAlbum;

    DownloadManager downloadManager;
    ProgressDialog progressBar;

    private static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private static final String TAG = "Http Connection Status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_song);

        urlBox = (EditText) findViewById(R.id.URL_box);
        downloadSong = (Button) findViewById(R.id.download_btn);
        downloadAlbum = (Button) findViewById(R.id.download_btn_album);

        downloadSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlBox.getText().toString();
                if(url != null){
                    startDownload(url, true);
                } else{
                    Toast.makeText(DownloadSongActivity.this, "Enter URL before download"
                            , Toast.LENGTH_LONG).show();
                }
            }
        });

        downloadAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlBox.getText().toString();
                if(url != null){
                    startDownload(url, false);
                } else{
                    Toast.makeText(DownloadSongActivity.this, "Enter URL before download"
                            , Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void startDownload(String url, boolean song){
        if(song){
            new DownloadFileAsync().execute(url, "true");
        } else{
            new DownloadFileAsync().execute(url, "false");
        }

    }

    public void dld(String url, String bool) {
        new DownloadFileAsync().execute(url, "true");
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {

        //ProgressBar progressBar;
        boolean song = true;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            //progressBar = new ProgressDialog(DownloadSongActivity.this);
//            progressBar.setMessage("Downloading Song content");
//            progressBar.setIndeterminate(true);
//            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressBar.setMax(100);
//            progressBar.setProgress(0);
            //progressBar.show();
            //TODO: implement a progressBar/ProgressDialog

        }

        @Override
        protected  String doInBackground(String... aurl){
            InputStream input = null;
            OutputStream output = null;

            Uri music_uri = Uri.parse(aurl[0]);

            if(aurl[1].equals("false")){
                song = false;
            }

            long Music_DownloadId = DownloadData(music_uri, song);
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            //progressBar.dismiss();
        }
    }

    public long DownloadData (Uri uri, boolean song) {

        String type = getMimeType(uri.toString());

        long downloadReference;

        // Create request for android download manager
        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
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
            SharedPreferences sp = this.getSharedPreferences("urlPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Integer.toString(randomID), uri.toString());
            editor.apply();
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS , Integer.toString(randomID)+ ".mp3");
        } else {
            SharedPreferences sp = this.getSharedPreferences("urlPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Integer.toString(randomID), uri.toString());
            editor.apply();
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS , Integer.toString(randomID) + ".zip");
        }


        // File tempFile = new File(Environment.getExternalStorageDirectory().toString() + "/Download/tempName");

        System.out.println("this type is : " + type); // doesn't work
        try {
            System.out.println("this name is : " + new URL(uri.toString()).getFile());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }



        downloadReference = downloadManager.enqueue(request);

        return downloadReference;
    }

    public static String getMimeType(String url) {
        try {
            String type = null;
            String extension = url.substring(url.lastIndexOf(".") + 1, url.length());
            if (extension != null) {
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            }
            return type;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
//link to downloadable Song via URL
//https://www.dropbox.com/s/fg7gc1tmdl1jre8/transmission-002-the-blackhole.mp3?dl=1
//https://www.dropbox.com/s/ilvs4t50l2rxxzz/spiraling-stars.mp3?dl=1
//Album link:
//https://mp3d.jamendo.com/download/a168142/mp32/

//Song: https://mp3d.jamendo.com/download/track/1528165/mp32/