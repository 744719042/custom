package com.example.custom.widget.vertical;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ViewFlipper;

import com.example.custom.R;

/**
 * Created by Administrator on 2018/1/31.
 */

public class VerticalScrollView2 extends ViewFlipper {
    private BaseAdapter adapter;

    public VerticalScrollView2(@NonNull Context context) {
        this(context, null);
    }

    public VerticalScrollView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setAutoStart(false);
        setFlipInterval(3000);
        setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_in));
        setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_out));
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        if (adapter == null || adapter.getCount() < 1) {
            setVisibility(View.GONE);
            return;
        }

        for (int i = 0, count = adapter.getCount(); i < count; i++) {
            addView(adapter.getView(i, null, this));
        }
    }

    public void pausePlay() {
        stopFlipping();
    }

    public void resumePlay() {
        startFlipping();
    }

    public void destroy() {
        pausePlay();
    }
}
