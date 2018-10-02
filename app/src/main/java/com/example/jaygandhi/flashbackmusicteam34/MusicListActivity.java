package com.example.jaygandhi.flashbackmusicteam34;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by XY Gong on 2/9/2018.
 * A list of music
 */
public abstract class MusicListActivity extends ListActivity {

    private static final String TAG = "MusicListActivity";
    protected ListView listView;
    private TextView header;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = getListView();
        header = new TextView(this);
        header.setTextSize(30);
        listView.addHeaderView(header);
        Log.d(TAG, "onCreate: set up list view; already tested in subclasses.");
    }

    protected void setHeader(String title) {
        header.setText(title);
        Log.d(TAG, "setHeader: already tested in subclasses.");
    }

    public String getHeader(){
        Log.d(TAG, "getHeader: already tested in subclasses.");
        return header.getText().toString();
    }


}
