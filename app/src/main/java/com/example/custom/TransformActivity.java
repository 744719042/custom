package com.example.custom;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.custom.adapter.TransformImageAdapter;

public class TransformActivity extends AppCompatActivity {
    private static final String TAG = "TransformActivity";
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new TransformImageAdapter());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                Log.d(TAG, "page = " + page.getTag() + ", position = " + position);
            }
        });
    }
}
