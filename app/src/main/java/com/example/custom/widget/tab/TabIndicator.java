package com.example.custom.widget.tab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.custom.R;
import com.example.custom.utils.CommonUtils;

/**
 * Created by Administrator on 2018/1/29.
 */

public class TabIndicator extends LinearLayout {
    private static final String TAG = "ArrowTabIndicator";
    private static final int STATE_INIT = 0;
    private static final int STATE_DECIDE_DIRECT = 1;
    private static final int STATE_DRAW_SCROLL_ARROW = 2;

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private int selected = -1;

    private int state = STATE_INIT;
    private float lastOffset = -1;
    private int lastPosition = -1;
    private boolean leftToRight = false;
    private int tabWidth;
    private int curPos;
    private Paint paint;
    public TabIndicator(Context context) {
        this(context, null);
    }

    public TabIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    public void setViewPager(final ViewPager viewPager) {
        this.viewPager = viewPager;
        if (viewPager.getAdapter() != null) {
            this.pagerAdapter = viewPager.getAdapter();
        } else {
            throw new IllegalArgumentException("ViewPager has no pagerAdapter");
        }

        if (pagerAdapter.getCount() == 0) {
            setVisibility(View.GONE);
            return;
        }

        setVisibility(View.VISIBLE);
        int count = pagerAdapter.getCount();
        int width = getTabWidth(count);

        for (int i = 0; i < count; i++) {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.colorAccent));
            textView.setTextSize(15f);
            textView.setText(pagerAdapter.getPageTitle(i));
            final int index = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index, true);
                }
            });
            LinearLayout.LayoutParams params = new LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            addView(textView, params);
        }

        tabWidth = width;
        curPos = width / 2;
        selected = 0;
        postInvalidate();
    }

    private int getTabWidth(int count) {
        if (count >= 4) {
            return CommonUtils.getScreenWidth() / 4;
        } else {
            return CommonUtils.getScreenWidth() / count;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int realHeight = CommonUtils.dp2px(45);
        if (mode == MeasureSpec.EXACTLY) {
            realHeight = height;
        }

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).getLayoutParams().height = realHeight;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), realHeight);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawBottomLine(canvas);
    }

    private void drawBottomLine(Canvas canvas) {
        canvas.save();
        canvas.drawRect(curPos - tabWidth / 2,
                getMeasuredHeight() - CommonUtils.dp2px(3),
                curPos + tabWidth / 2, getMeasuredHeight(), paint);
        canvas.restore();
    }

    private void drawTriangle(Canvas canvas) {
        canvas.save();
        Path path = new Path();
        path.moveTo(curPos - CommonUtils.dp2px(9), getMeasuredHeight());
        path.lineTo(curPos + CommonUtils.dp2px(9), getMeasuredHeight());
        path.lineTo(curPos, getMeasuredHeight() - CommonUtils.dp2px(12));
        path.close();
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    public void setPositionAndOffset(int position, float offset) {
        if (selected < 0 || offset < 0.00000001 || offset > 0.9999999999) {
            return;
        }

        switch (state) {
            case STATE_INIT:
                lastOffset = offset;
                lastPosition = position;
                state = STATE_DECIDE_DIRECT;
                Log.d(TAG, "setPositionAndOffset(): init state = " + state
                        + ", lastPosition = " + lastPosition + ", lastOffset = " + lastOffset);
                break;
            case STATE_DECIDE_DIRECT:
                if (lastPosition == position) {
                    leftToRight = (offset - lastOffset) > 0;
                    state = STATE_DRAW_SCROLL_ARROW;
                } else {
                    lastOffset = offset;
                    lastPosition = position;
                }
                Log.d(TAG, "setPositionAndOffset(): init state = " + state
                        + ", lastPosition = " + lastPosition + ", lastOffset = " + lastOffset);
                break;
            case STATE_DRAW_SCROLL_ARROW:
                if (leftToRight) {
                    int target = position + 1;
                    Log.d(TAG, "setPositionAndOffset(): init state = " + state
                            + ", leftToRight = " + leftToRight + ", target = " + target + ", selected = " + selected);
                    if (target >= pagerAdapter.getCount() || target == selected) {
                        return;
                    }
                    curPos = (int) (((target + offset) * tabWidth) - tabWidth / 2);
                } else {
                    int target = position;
                    Log.d(TAG, "setPositionAndOffset(): init state = " + state
                            + ", leftToRight = " + leftToRight + ", target = " + target + ", selected = " + selected);
                    curPos = (int) (((target + 1 + offset) * tabWidth) - tabWidth / 2);
                }
                invalidate();
            default:
                break;
        }

    }

    public void reset() {
        state = STATE_INIT;
        selected = viewPager.getCurrentItem();
    }
}
