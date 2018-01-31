package com.example.custom.widget.vertical;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2018/1/30.
 */
public class VerticalScrollView extends FrameLayout {
    private View recycleView;
    private View currentView;
    private BaseAdapter adapter;
    private int current = -1;
    private Runnable playRunnable = new Runnable() {
        @Override
        public void run() {
            current = (current + 1) % adapter.getCount();
            View newView = adapter.getView(current, recycleView, VerticalScrollView.this);
            ObjectAnimator hide = ObjectAnimator.ofFloat(currentView, "translationY", 0, -recycleView.getHeight());
            ObjectAnimator show = ObjectAnimator.ofFloat(newView, "translationY", newView.getHeight(), 0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(hide).with(show);
            animatorSet.setDuration(1000);
            animatorSet.start();
            recycleView = currentView;
            currentView = newView;
            postDelayed(this, 3000);
        }
    };

    public VerticalScrollView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        if (adapter == null || adapter.getCount() < 1) {
            setVisibility(View.GONE);
            return;
        }

        currentView = adapter.getView(0, null, this);
        recycleView = adapter.getView(1, null, this);
        current = 0;
        addView(recycleView);
        addView(currentView);
    }

    public void pausePlay() {
        removeCallbacks(playRunnable);
    }

    public void resumePlay() {
        removeCallbacks(playRunnable);
        postDelayed(playRunnable, 3000);
    }

    public void destroy() {
        pausePlay();
        playRunnable = null;
    }
}
