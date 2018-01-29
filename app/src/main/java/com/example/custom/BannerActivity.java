package com.example.custom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.custom.adapter.BannerImageAdapter;
import com.example.custom.widget.viewpager.AutoPlayView;

public class BannerActivity extends AppCompatActivity {
    private AutoPlayView autoPlayView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        autoPlayView = (AutoPlayView) findViewById(R.id.auto_player_view);
        autoPlayView.setAdapter(new BannerImageAdapter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoPlayView.pausePlay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoPlayView.resumePlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        autoPlayView.destroy();
    }
}
