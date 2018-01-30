package com.example.custom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.custom.adapter.GalleryImageAdapter;
import com.example.custom.widget.gallery.MyGalleryView;

public class GalleryActivity extends AppCompatActivity {
    private MyGalleryView myGalleryView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        myGalleryView = (MyGalleryView) findViewById(R.id.gallery_view);
        myGalleryView.setAdapter(new GalleryImageAdapter());
    }
}
