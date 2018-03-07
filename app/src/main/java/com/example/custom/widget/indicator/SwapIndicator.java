package com.example.custom.widget.indicator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.custom.R;
import com.example.custom.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class SwapIndicator extends LinearLayout {
    private final List<View> mViews = new ArrayList<>();
    private int mPreSelected = -1;
    public SwapIndicator(Context context) {
        this(context, null);
    }

    public SwapIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwapIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    public void setupViewPager(final ViewPager viewPager) {
        PagerAdapter pagerAdapter = viewPager.getAdapter();
        if (pagerAdapter == null || pagerAdapter.getCount() == 0) {
            return;
        }

        int count = pagerAdapter.getCount();
        mPreSelected = viewPager.getCurrentItem();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayout.LayoutParams params = new LayoutParams(CommonUtils.dp2px(10), CommonUtils.dp2px(10));
            params.leftMargin = CommonUtils.dp2px(5);
            params.rightMargin = CommonUtils.dp2px(5);
            if (i == mPreSelected) {
                imageView.setBackgroundResource(R.drawable.normal_dot);
            } else {
                imageView.setBackgroundResource(R.drawable.black_dot);
            }
            addView(imageView, params);
            mViews.add(imageView);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                View view = mViews.get(position);
                View preView = mViews.get(mPreSelected);
                float preX = preView.getX();
                float curX = view.getX();
                ObjectAnimator pre = ObjectAnimator.ofFloat(preView, "x", preX, curX);
                ObjectAnimator cur = ObjectAnimator.ofFloat(view, "x", curX, preX);
                AnimatorSet set = new AnimatorSet();
                set.setDuration(300).play(pre).with(cur);
                set.start();
                Collections.swap(mViews, position, mPreSelected);
                mPreSelected = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
