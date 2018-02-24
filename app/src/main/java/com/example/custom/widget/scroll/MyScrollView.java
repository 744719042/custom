package com.example.custom.widget.scroll;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ScrollView;

import com.example.custom.R;

/**
 * Created by Administrator on 2018/2/23.
 */

public class MyScrollView extends ScrollView {
    private static final float MAX_SCALE_RATIO = 1.8f;
    private int mPullDownY = -1;
    private boolean mIsPullDown = false;
    private int originHeight;
    private int originWidth;
    private View headerView;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        headerView = findViewById(R.id.scroll_header_view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (headerView == null) {
            return super.onTouchEvent(ev);
        }

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_MOVE:
                if (getScrollY() != 0) {
                    return super.onTouchEvent(ev);
                }
                if (!mIsPullDown && getScrollY() == 0 && mPullDownY == -1) {
                    mPullDownY = (int) ev.getY();
                    return super.onTouchEvent(ev);
                } else if (!mIsPullDown) {
                    int diff = (int) (ev.getY() - mPullDownY);
                    if (diff < 0) { // 向上推动
                        return super.onTouchEvent(ev);
                    } else { // 向下拉动
                        mIsPullDown = true;
                        originHeight = headerView.getHeight();
                        originWidth = headerView.getWidth();
                    }
                }

                int diff = (int) (ev.getY() - mPullDownY);
                if (mIsPullDown && diff > 0) {
                    int height = (int) (originHeight + diff * 0.5f);
                    float ratio = (float) height / originHeight;
                    ratio = ratio > MAX_SCALE_RATIO ? MAX_SCALE_RATIO : ratio;
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) headerView.getLayoutParams();
                    layoutParams.width = (int) (originWidth * ratio);
                    layoutParams.height = (int) (originHeight * ratio);
                    layoutParams.leftMargin = -(layoutParams.width - originWidth) / 2;
                    headerView.requestLayout();
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsPullDown = false;
                mPullDownY = -1;
                float startRatio = (float) headerView.getLayoutParams().width / originWidth;
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(startRatio, 1.0f).setDuration(300);
                valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) headerView.getLayoutParams();
                        layoutParams.width = (int) ((float) animation.getAnimatedValue() * originWidth);
                        layoutParams.height = (int) ((float) animation.getAnimatedValue() * originHeight);
                        layoutParams.leftMargin = -(layoutParams.width - originWidth) / 2;
                        headerView.requestLayout();
                    }
                });
                valueAnimator.start();
                break;
        }
        return super.onTouchEvent(ev);
    }
}
