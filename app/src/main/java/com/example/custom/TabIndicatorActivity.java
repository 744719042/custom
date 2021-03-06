package com.example.custom;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.custom.adapter.LargeImageAdapter;
import com.example.custom.widget.tab.TabIndicator;

public class TabIndicatorActivity extends AppCompatActivity {
    private static final String TAG = "TabIndicatorActivity";
    private TabIndicator arrowTabIndicator;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_indicator);
        arrowTabIndicator = (TabIndicator) findViewById(R.id.arrow_indicator);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new LargeImageAdapter());
        arrowTabIndicator.setViewPager(viewPager);
    }
}
