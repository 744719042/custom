package com.example.custom.widget.viewpager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.custom.adapter.BannerAdapter;
import com.example.custom.adapter.BannerImageAdapter;
import com.example.custom.utils.CommonUtils;

/**
 * Created by Administrator on 2018/1/29.
 */

public class AutoPlayView extends FrameLayout {
    private ViewPager viewPager;
    private Runnable mPlayRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            invalidate();
            postDelayed(mPlayRunnable, 3000);
        }
    };
    private Paint paint;

    public AutoPlayView(Context context) {
        this(context, null);
    }

    public AutoPlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPlayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setDither(true);

        viewPager = new ViewPager(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(viewPager, params);
    }

    public boolean hasAdapter() {
        return viewPager.getAdapter() != null;
    }

    public void setAdapter(BannerAdapter bannerAdapter) {
        viewPager.setAdapter(bannerAdapter);
    }

    public void resumePlay() {
        if (!hasAdapter()) {
            return;
        }
        postDelayed(mPlayRunnable, 3000);
        invalidate();
    }

    public void pausePlay() {
        if (!hasAdapter()) return;
        removeCallbacks(mPlayRunnable);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawCircles(canvas);
    }

    private void drawCircles(Canvas canvas) {
        canvas.save();
        BannerImageAdapter adapter = (BannerImageAdapter) viewPager.getAdapter();
        int count = adapter.getItemCount();
        int chosen = viewPager.getCurrentItem() % count;
        int centerX = getMeasuredWidth() / 2;
        int centerY = getMeasuredHeight() - CommonUtils.dp2px(10);
        int radius = CommonUtils.dp2px(5);
        int margin = CommonUtils.dp2px(10);
        int half = count / 2;
        int start = centerX - half * 2 * radius - (half - 1) * margin;
        for (int i = 0; i < count; i++) {
            if (i == chosen) {
                paint.setStyle(Paint.Style.FILL);
            } else {
                paint.setStrokeWidth(CommonUtils.dp2px(1));
                paint.setStyle(Paint.Style.STROKE);
            }
            canvas.drawCircle(start + i * (2 * radius + margin), centerY, radius, paint);
        }
        canvas.restore();
    }

    public void destroy() {
        pausePlay();
        setAdapter(null);
        mPlayRunnable = null;
    }
}
