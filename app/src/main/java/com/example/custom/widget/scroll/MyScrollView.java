package com.example.custom.widget.scroll;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ScrollView;

import com.example.custom.R;

/**
 * Created by Administrator on 2018/2/23.
 */

public class MyScrollView extends ScrollView {
    private static final String TAG = "MyScrollView";
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
        int y = (int) ev.getY();
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mPullDownY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (getScrollY() != 0) {
                    return super.onTouchEvent(ev);
                }
                int motionY = y - mPullDownY;
                if (!mIsPullDown) {
                    if (motionY < 0) { // 向上推动
                        return super.onTouchEvent(ev);
                    } else { // 向下拉动
                        mIsPullDown = true;
                        originHeight = headerView.getHeight();
                        originWidth = headerView.getWidth();
                    }
                }

                if (mIsPullDown && motionY > 0) {
                    int height = (int) (originHeight + motionY * 0.5f);
                    float ratio = (float) height / originHeight;
                    ratio = ratio > MAX_SCALE_RATIO ? MAX_SCALE_RATIO : ratio;
                    setRatio(ratio);
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
                        setRatio((float) animation.getAnimatedValue());
                    }
                });
                valueAnimator.start();
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void setRatio(float ratio) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) headerView.getLayoutParams();
        layoutParams.width = (int) (ratio * originWidth);
        layoutParams.height = (int) (ratio * originHeight);
        layoutParams.leftMargin = -(layoutParams.width - originWidth) / 2;
        headerView.requestLayout();
    }
}
