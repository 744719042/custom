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
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.custom.R;
import com.example.custom.utils.CommonUtils;
import com.example.custom.widget.CustomTextView;

/**
 * Created by Administrator on 2018/1/29.
 */

public class TabIndicator extends HorizontalScrollView {
    private static final String TAG = "ArrowTabIndicator";

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private int selected = -1;
    private LinearLayout linearLayout;
    private FrameLayout frameLayout;

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
        frameLayout = new FrameLayout(getContext());
        addView(frameLayout);
        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        frameLayout.addView(linearLayout);

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
            CustomTextView textView = new CustomTextView(getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColor(R.color.colorAccent));
            textView.setTextSize(15f);
            textView.setText(pagerAdapter.getPageTitle(i));
            textView.setProgress(1.0f);
            final int index = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index, true);
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            linearLayout.addView(textView, params);
        }

        tabWidth = width;
        curPos = width / 2;
        selected = 0;
        postInvalidate();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled() position = " + position + ", positionOffset = " + positionOffset);
                setPositionAndOffset(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                scrollToCenter(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void scrollToCenter(int position) {
        int screenSize = CommonUtils.getScreenWidth();
        int midPos = screenSize / 2;
        int left = position * tabWidth + tabWidth / 2;
        int right = (linearLayout.getChildCount() - position - 1) * tabWidth + tabWidth / 2;
        if (left < midPos) {
            smoothScrollTo(0, 0);
        } else if (left > midPos && right > midPos) {
            smoothScrollTo(left - midPos, 0);
        } else {
            smoothScrollTo(tabWidth * linearLayout.getChildCount(), 0);
        }
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
        canvas.drawRect(curPos,
                getMeasuredHeight() - CommonUtils.dp2px(3),
                curPos + tabWidth, getMeasuredHeight(), paint);
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
        curPos = (int) ((position + offset) * tabWidth);
        CustomTextView left = (CustomTextView) linearLayout.getChildAt(position);
        left.setOritention(1);
        left.setProgress(offset);
        if (position + 1 < linearLayout.getChildCount()) {
            CustomTextView right = (CustomTextView) linearLayout.getChildAt(position + 1);
            right.setProgress(1f - offset);
        }
        invalidate();
    }
}
