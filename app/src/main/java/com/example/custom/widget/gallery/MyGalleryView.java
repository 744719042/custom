package com.example.custom.widget.gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.custom.utils.CommonUtils;

/**
 * Created by Administrator on 2018/1/30.
 */

public class MyGalleryView extends FrameLayout {
    private static final String TAG = "MyGalleryView";

    private ViewPager viewPager;
    public MyGalleryView(@NonNull Context context) {
        this(context, null);
    }

    public MyGalleryView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyGalleryView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        viewPager = new ViewPager(getContext());
        viewPager.setClipChildren(false);
        viewPager.setClipToPadding(false);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPadding(CommonUtils.dp2px(50), 0, CommonUtils.dp2px(50), 0);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        addView(viewPager, params);

        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                Log.d(TAG, "transformPage(): position = " + position);
                final float SCALE_MAX = 0.8f;
                float scale = (position < 0)
                        ? ((1 - SCALE_MAX) * position + 1)
                        : ((SCALE_MAX - 1) * position + 1);

                if(position < 0) {
                    page.setPivotX(page.getWidth());
                    page.setPivotY(page.getHeight() / 2);
                } else {
                    page.setPivotX(0);
                    page.setPivotY(page.getHeight() / 2);
                }
                page.setScaleX(scale);
                page.setScaleY(scale);
            }
        });
    }

    public void setAdapter(PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }
}
