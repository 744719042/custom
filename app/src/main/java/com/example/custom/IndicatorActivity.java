package com.example.custom;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.custom.adapter.BannerPicAdapter;
import com.example.custom.widget.indicator.PagerIndicator;
import com.example.custom.widget.indicator.SwapIndicator;

public class IndicatorActivity extends AppCompatActivity {
    private ViewPager viewPager1;
    private PagerIndicator indicator;
    private ViewPager viewPager2;
    private SwapIndicator swapIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        viewPager1 = (ViewPager) findViewById(R.id.view_pager_1);
        indicator = (PagerIndicator) findViewById(R.id.widthIndicator);
        viewPager1.setAdapter(new BannerPicAdapter());
        indicator.setupViewPager(viewPager1);

        viewPager2 = (ViewPager)findViewById(R.id.view_pager_2);
        swapIndicator = (SwapIndicator) findViewById(R.id.swapIndicator);
        viewPager2.setAdapter(new BannerPicAdapter());
        swapIndicator.setupViewPager(viewPager2);
    }
}
