package com.example.custom.widget.indicator;

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
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class PagerIndicator extends LinearLayout {
    private static final int SELECTED_WIDTH = CommonUtils.dp2px(30);
    private static final int UNSELECTED_WIDTH = CommonUtils.dp2px(10);
    private final List<ViewWrapper> mViews = new ArrayList<>();

    public PagerIndicator(Context context) {
        this(context, null);
    }

    public PagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        int selected = viewPager.getCurrentItem();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            if (i == selected) {
                LinearLayout.LayoutParams params = new LayoutParams(SELECTED_WIDTH, CommonUtils.dp2px(10));
                params.leftMargin = CommonUtils.dp2px(5);
                params.rightMargin = CommonUtils.dp2px(5);
                imageView.setBackgroundResource(R.drawable.normal_dot);
                addView(imageView, params);
            } else {
                LinearLayout.LayoutParams params = new LayoutParams(UNSELECTED_WIDTH, CommonUtils.dp2px(10));
                params.leftMargin = CommonUtils.dp2px(5);
                params.rightMargin = CommonUtils.dp2px(5);
                imageView.setBackgroundResource(R.drawable.black_dot);
                addView(imageView, params);
            }
            mViews.add(new ViewWrapper(imageView));
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int count = mViews.size();
                for (int i = 0; i < count; i++) {
                    ViewWrapper viewWrapper = mViews.get(i);
                    if (i == position) {
                        viewWrapper.setBackgroundResource(R.drawable.normal_dot);
                        ObjectAnimator animator = ObjectAnimator.ofInt(viewWrapper,
                                "width", viewWrapper.getWidth(), SELECTED_WIDTH)
                                .setDuration(300);
                        animator.start();
                    } else {
                        viewWrapper.setBackgroundResource(R.drawable.black_dot);
                        ObjectAnimator animator = ObjectAnimator.ofInt(viewWrapper,
                                "width", viewWrapper.getWidth(), UNSELECTED_WIDTH)
                                .setDuration(300);
                        animator.start();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static class ViewWrapper {
        private View view;

        public ViewWrapper(View view) {
            this.view = view;
        }

        public void setBackgroundResource(int resource) {
            this.view.setBackgroundResource(resource);
        }

        public void setWidth(int width) {
            view.getLayoutParams().width = width;
            view.requestLayout();
        }

        public int getWidth() {
            return view.getLayoutParams().width;
        }
    }
}
