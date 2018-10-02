package com.example.jaygandhi.flashbackmusicteam34;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.lang.reflect.Array;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by jaygandhi on 2/13/18.
 * This class provides helper methods to load files/music/albums.
 */
public class Library {

    public static ArrayList<Track> trackList = new ArrayList<Track>();
    public static ArrayList<Album> albumList = new ArrayList<Album>();
    public static HashSet<String> albumNameSet = new HashSet<>();
    public static HashMap<Integer, Track> idsToSongs = new HashMap<>();
    public static Context curActivity;

    private static final String TAG = "LibraryClass";

    private static HashMap<Integer, Integer> albumSongIds = new HashMap<>();
    /*
     * Load music from raw files in the raw folder.
     */
    public static void loadSongs() {
        trackList.clear();
        unzipFiles();
        loadSongsAndAlbums();

    }

    public static void unzipFiles(){
        File path = new File(Environment.getExternalStorageDirectory().toString() + "/Download");
        File[] downloadedFiles = path.listFiles();

        if(downloadedFiles != null){
            for(File zipFile: downloadedFiles){
                String fileNameString = zipFile.getAbsolutePath();
                if(fileNameString.endsWith(".zip")){
                    if(!zipFile.canRead()){
                        //throw new IOException("unreadable file");
                        continue;
                    }

                    try{
                        DataInputStream stream = new DataInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
                        int zipText = stream.readInt();
                        stream.close();
                        if(zipText == 0x504b0304){
                            unZipFile(zipFile);
                            //loadAlbumSong();
                        }
                    } catch (IOException e){
                    }
                }
            }
        }

    }


    public static void unZipFile(File zip){
        String absPath = zip.getAbsolutePath();
        String targetLocation = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        System.out.println("curr targetLocation: " + targetLocation + "---------------------------->");
        try{

            File targetDirFile = new File(targetLocation);

            FileInputStream fileInputStream = new FileInputStream(absPath);
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(zipInputStream);
            ZipEntry currEntry = null;

            String[] split = zip.getName().split(".zip");
            String idStr = split[0];
            int albumId = Integer.parseInt(idStr);

            while((currEntry = zipInputStream.getNextEntry()) != null){


                String path = targetLocation + "/" + currEntry.getName();
                if(currEntry.getName().endsWith(".mp3")){
                    int randomID = (int)(Math.random() * 1000000);
                    path = targetLocation + "/" + randomID + ".mp3";
                    albumSongIds.put(randomID, albumId);
                }



                FileOutputStream fileOutputStream = new FileOutputStream(path);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                //System.out.println("buffer value: " + zipInputStream.read());
                try{

                    byte b[] = new byte[1024];
                    int n;
                    while((n = bufferedInputStream.read(b, 0, 1024)) >= 0){
                        bufferedOutputStream.write(b, 0, n);
                    }
                } catch (Exception e){

                } finally {
                    bufferedOutputStream.close();
                    fileOutputStream.close();
                }
            }

            zipInputStream.close();
        }catch(Exception e){

        } finally{
            File currZip = new File(zip.getAbsolutePath());
            boolean deleted = currZip.delete();
        }
    }

    public static void loadSongsAndAlbums(){
        SharedPreferences sp = curActivity.getSharedPreferences("urlPref", Context.MODE_PRIVATE);

        File path = new File(Environment.getExternalStorageDirectory().toString() + "/Download");
        File[] downloadedFiles = path.listFiles();

        if (downloadedFiles != null) {
            for (File songFile : downloadedFiles) {


                String fileNameString = songFile.getAbsolutePath();

                if(fileNameString.endsWith(".mp3")){
                    String format = songFile.getAbsolutePath().
                            substring(songFile.getAbsolutePath().lastIndexOf(".") + 1);


                    if (songFile.isFile()) {

                        String[] split = songFile.getName().split(".mp3");
                        String idStr = split[0];

                        if(idStr == null){
                            System.out.println("ID is null -------------------------------->");
                        }

                        int id = Integer.parseInt(idStr);;
                        Track t = t = new Track(id);

                        if(albumSongIds.containsKey(t.getTrackId())){
                            t.setIsAlbumSong(true);
                            t.setAlbumId(albumSongIds.get(t.getTrackId()));
                        }
                        trackList.add(t);

                        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                        mmr.setDataSource(songFile.getAbsolutePath());

                        String albumName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                        String artistName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                        t.setAlbumName(albumName);
                        t.setArtistName(artistName);

                        if(!albumNameSet.contains(albumName)){
                            albumNameSet.add(albumName);
                            albumList.add(new Album(albumName, artistName));
                        }

                        idsToSongs.put(id, t);
                        if(t.getIsAlbumSong()){
                            String url = sp.getString(Integer.toString(t.getAlbumId()), "FailedToFindURL");
                            t.setTrackURL(url);
                        } else{
                            String url = sp.getString(Integer.toString(id), "FailedToFindURL");
                            t.setTrackURL(url);
                        }

                        Log.i(TAG, "loadMusic: load file: " + songFile.getName() + "; id=" + id);
                    }
                }
            }
        }
    }

    /*
     * Loads tracks and albums from the lists into our HashSet
     */
    public static void loadAlbums() {


        HashSet<String> s = new HashSet<>();

        for (Album album : albumList) {
            for (Track track : trackList) {
                if(track.getAlbumName() == null){
                    track.setAlbumName("Singles--No Album");
                    track.setAlbumArtist("Multiple Artists");
                } else {
                    if(track.getAlbumArtist() == null)
                        track.setAlbumArtist("Anonymous Album Artist");
                    if (track.getAlbumName().equals(album.getAlbumName())) {
                        album.addTrack(track);
                    }
                }

            }
        }

        /*
        for (Track track : trackList) {
            if (!s.contains(track.getAlbumName())){
                albumList.add(new Album(track.getAlbumName(), track.getAlbumArtist()));
                s.add(track.getAlbumName());
            }
        }
        */

    }

    //2.0
}


//        Field[] fields = R.raw.class.getFields();
//
//        Log.d(TAG, "loadMusic: begin loading music");
//
//        try {
//            for (Field field : fields) {
//                int id = field.getInt(field);
//                Track t = new Track(id);
//                trackList.add(t);
//                idsToSongs.put(id, t);
//                Log.i(TAG, "loadMusic: load file: " + field.getName() + "; id=" + id);
//            }
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//            Log.e(TAG, "loadMusic: Error occurs when loading raw music files");
//        }
//
//        Log.d(TAG, "loadMusic: finish loading music");




//    public static void loadAlbumSong(){
//        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath());
//        File[] unzippedFiles = path.listFiles();
//
//        int count = 0;
//        for(File songFile: unzippedFiles){
//            String fileNameString = songFile.getAbsolutePath();
//            if(fileNameString.endsWith(".mp3")){
//                count++;
//                System.out.print("current count: " + count);
//                String format = songFile.getAbsolutePath().
//                        substring(songFile.getAbsolutePath().lastIndexOf(".") + 1);
//
//                System.out.println("current file format: " + songFile.getAbsolutePath());
//                if (format.equals("mp3")) {
//                    System.out.println("song file read ---> its path: " +
//                            " " + songFile.getAbsolutePath().toString());
//                }
//                if (songFile.isFile()) {
//
//                    String[] split = songFile.getName().split(".mp3");
//                    String idStr = split[0];
//
//                    if(idStr == null){
//                        System.out.println("ID is null -------------------------------->");
//                    }
//                    int id = -1;
//                    try {
//                        id = Integer.parseInt(idStr);
//                    } catch (NumberFormatException e){
//                        int randomID = (int)(Math.random() * 1000000);
//                        songFile.renameTo( new File(Integer.toString(randomID) + ".mp3") );
//                        id = randomID;
//
//                    }
//                    Track t = new Track(id);
//                    trackList.add(t);
//                    idsToSongs.put(id, t);
//                    Log.i(TAG, "loadMusic: load file: " + songFile.getName() + "; id=" + id);
//                }
//            }
//        }
//    }