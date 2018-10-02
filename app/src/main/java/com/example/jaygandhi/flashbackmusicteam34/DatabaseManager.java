package com.example.jaygandhi.flashbackmusicteam34;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {

    private static final String TAG = "DatabaseManager";

    private DatabaseReference databaseRef;
    private Context context;
    private boolean flag= false;

    private static FirebaseDatabase database;

    public DatabaseManager(Context context) {

        this.context = context;

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:560864550567:android:7097cbaa9639eee8")
                .setDatabaseUrl("https://cse-110-team-project-team-34.firebaseio.com/")
                .build();

        if (database == null) {
            database = FirebaseDatabase.getInstance(FirebaseApp.initializeApp(context, options, "secondary"));
        }
        databaseRef = database.getReferenceFromUrl("https://cse-110-team-project-team-34.firebaseio.com/");
    }

    public void writeToDatabase(TrackRecord record) {
        databaseRef.child("records").child(record.getResourceID()).setValue(record);
    }

    public void readFromDatabase() {

        final ArrayList<Map<String, Object>> list = new ArrayList<>();
        Query queryRef = databaseRef.child("records");


        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot == null || snapshot.getValue() == null) {
                    Toast.makeText(context, "No record found", Toast.LENGTH_SHORT).show();

                } else {
                    if(flag == false) {
                        flag = true;
                        Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            list.add((Map<String, Object>) entry.getValue());
                            Log.d(TAG, "onDataChange: " + ((Map<String, Object>) entry.getValue()).get("title"));
                            Log.d(TAG, "Readfromdatabase is too complex to test in JUnit");
                        }
                        // TODO: ADD AN UPDATE FUNCTION IN ANY ACTIVITY CLASS THAT USES DATABASE MANAGER,
                        // NOTIFY THE ACTIVITIES WHEN LIST IS UPDATED
                        Intent intent = new Intent(context, PlaybackActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("record_list", list);
                        intent.putExtra("list_bundle", bundle);
                        intent.putExtra("mode", "vibe_mode");
                        context.startActivity(intent);

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public DatabaseReference getRef(){
        return databaseRef;
    }

}
