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
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.custom.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */

public class AutoPlayView2 extends FrameLayout {
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private Runnable mPlayRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % (pagerAdapter.getCount() + 2), true);
            invalidate();
            postDelayed(mPlayRunnable, 3000);
        }
    };
    private Paint paint;
    public AutoPlayView2(@NonNull Context context) {
        this(context, null);
    }

    public AutoPlayView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPlayView2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(pagerAdapter.getCount(), false);
                    } else if (viewPager.getCurrentItem() == pagerAdapter.getCount() + 1) {
                        viewPager.setCurrentItem(1, false);
                    }
                }
            }
        });
    }

    public boolean hasAdapter() {
        return viewPager.getAdapter() != null;
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
        viewPager.setAdapter(new InnerPagerAdapter(pagerAdapter, viewPager));
        viewPager.setCurrentItem(1);
    }

    private static class InnerPagerAdapter extends PagerAdapter {
        private List<Object> list = new ArrayList<>();
        private PagerAdapter adapter;
        private ViewPager viewPager;

        private InnerPagerAdapter(PagerAdapter pagerAdapter, ViewPager viewPager) {
            this.adapter = pagerAdapter;
            this.viewPager = viewPager;
            int count = pagerAdapter.getCount();
            if (count == 1) {
                FrameLayout frameLayout = new FrameLayout(viewPager.getContext());
                list.add(0, pagerAdapter.instantiateItem(frameLayout, count - 1));
            } else {
                FrameLayout frameLayout = new FrameLayout(viewPager.getContext());
                list.add(0, pagerAdapter.instantiateItem(frameLayout, count - 1));
                frameLayout.removeAllViews();
                for (int i = 1; i < count + 1; i++) {
                    list.add(i, pagerAdapter.instantiateItem(frameLayout, i - 1));
                    frameLayout.removeAllViews();
                }
                list.add(count + 1, pagerAdapter.instantiateItem(frameLayout, 0));
                frameLayout.removeAllViews();
            }
        }

        @Override
        public int getCount() {
            return adapter.getCount() == 1 ? 1 : adapter.getCount() + 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = (View) list.get(position);
            if (view.getParent() instanceof ViewGroup) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) list.get(position));
        }
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
        int count = pagerAdapter.getCount();
        int chosen = getChosenPosition(viewPager.getCurrentItem());
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

    private int getChosenPosition(int currentItem) {
        if (currentItem == 0) {
            return pagerAdapter.getCount() - 1;
        } else if (pagerAdapter.getCount() + 1 == currentItem) {
            return 0;
        } else {
            return currentItem - 1;
        }
    }

    public void destroy() {
        pausePlay();
        mPlayRunnable = null;
    }
}
