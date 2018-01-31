package com.example.custom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.custom.adapter.VerticalAdapter;
import com.example.custom.widget.vertical.VerticalScrollView;

public class VerticalScrollActivity extends AppCompatActivity {
    private VerticalScrollView verticalScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_scroll);
        verticalScrollView = (VerticalScrollView) findViewById(R.id.verticalScrollView);
        verticalScrollView.setAdapter(new VerticalAdapter(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        verticalScrollView.pausePlay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        verticalScrollView.resumePlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        verticalScrollView.destroy();
    }
}
