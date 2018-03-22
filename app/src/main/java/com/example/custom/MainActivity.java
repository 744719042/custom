package com.example.custom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.custom.widget.FlowLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button tab;
    private Button horizontal;
    private Button gallery;
    private Button vertical;
    private Button transform;
    private Button calendar;
    private Button refresh;
    private Button zoom;
    private Button indicator;
    private Button flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tab = (Button) findViewById(R.id.tabIndicator);
        tab.setOnClickListener(this);
        horizontal = (Button) findViewById(R.id.tabHorizontalSlide);
        horizontal.setOnClickListener(this);
        gallery = (Button) findViewById(R.id.gallery);
        gallery.setOnClickListener(this);
        vertical = (Button) findViewById(R.id.tabVerticalSlide);
        vertical.setOnClickListener(this);
        transform = (Button) findViewById(R.id.transform);
        transform.setOnClickListener(this);
        calendar = (Button) findViewById(R.id.calendar);
        calendar.setOnClickListener(this);
        refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
        zoom = (Button) findViewById(R.id.zoom);
        zoom.setOnClickListener(this);
        indicator = (Button) findViewById(R.id.viewpagerIndicator);
        indicator.setOnClickListener(this);
        flowLayout = (Button) findViewById(R.id.flowLayout);
        flowLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tab) {
            Intent intent = new Intent(this, TabIndicatorActivity.class);
            startActivity(intent);
        } else if (v == horizontal) {
            Intent intent = new Intent(this, BannerActivity.class);
            startActivity(intent);
        } else if (v == gallery) {
            Intent intent = new Intent(this, GalleryActivity.class);
            startActivity(intent);
        } else if (v == vertical) {
            Intent intent = new Intent(this, VerticalScrollActivity.class);
            startActivity(intent);
        } else if (v == transform) {
            Intent intent = new Intent(this, TransformActivity.class);
            startActivity(intent);
        } else if (v == calendar) {
            Intent intent = new Intent(this, CalendarActivity.class);
            startActivity(intent);
        } else if (v == refresh) {
            Intent intent = new Intent(this, SwipeActivity.class);
            startActivity(intent);
        } else if (v == zoom) {
            Intent intent = new Intent(this, ZoomHeaderActivity.class);
            startActivity(intent);
        } else if (v == indicator) {
            Intent intent =new Intent(this, IndicatorActivity.class);
            startActivity(intent);
        } else if (v == flowLayout) {
            Intent intent = new Intent(this, FlowLayoutActivity.class);
            startActivity(intent);
        }
    }
}
