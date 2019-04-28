package com.example.custom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ZoomHeaderActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_header);
//        listView = (ListView) findViewById(R.id.listView);
//        listView.setAdapter(new UserListAdapter(this));
    }
}
