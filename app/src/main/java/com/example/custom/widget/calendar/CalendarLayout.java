package com.example.custom.widget.calendar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.custom.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/2/22.
 */

public class CalendarLayout extends FrameLayout {
    private ImageView back;
    private ImageView forward;
    private TextView year;
    private TextView month;
    private CalendarView hideCalendarView;
    private CalendarView calendarView;
    private FrameLayout frameLayout;
    // 记录当前展示的时间
    private Calendar calendar = Calendar.getInstance();

    private boolean mIsScroll = false;
    private int mDownX;
    private int mLastX;
    private int mTouchSlop;
    private VelocityTracker tracker;

    public CalendarLayout(Context context) {
        this(context, null);
    }

    public CalendarLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.widget_calendar_layout, this);
        back = (ImageView) findViewById(R.id.back);
        forward = (ImageView) findViewById(R.id.forward);
        year = (TextView) findViewById(R.id.year);
        month = (TextView) findViewById(R.id.month);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        initText();

        hideCalendarView = (CalendarView) inflater.inflate(R.layout.calendar_days, frameLayout, false);
        hideCalendarView.setVisibility(View.INVISIBLE);
        calendarView = (CalendarView) inflater.inflate(R.layout.calendar_days, frameLayout, false);
        calendarView.setVisibility(View.VISIBLE);
        frameLayout.addView(hideCalendarView);
        frameLayout.addView(calendarView);

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                hideCalendarView.setYear(calendar.get(Calendar.YEAR));
                hideCalendarView.setMonth(calendar.get(Calendar.MONTH));
                hideCalendarView.setVisibility(View.VISIBLE);
                ObjectAnimator showAnimator = ObjectAnimator.ofFloat(hideCalendarView,
                        "translationX", -calendarView.getWidth(), 0);
                final ObjectAnimator hideAnimator = ObjectAnimator.ofFloat(calendarView,
                        "translationX", 0, calendarView.getWidth());
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(showAnimator).with(hideAnimator);
                animatorSet.setDuration(300);
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        CalendarView tmp = hideCalendarView;
                        hideCalendarView = calendarView;
                        calendarView = tmp;
                        hideCalendarView.setVisibility(View.INVISIBLE);
                    }
                });
                animatorSet.start();
                initText();
            }
        });

        forward.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                hideCalendarView.setYear(calendar.get(Calendar.YEAR));
                hideCalendarView.setMonth(calendar.get(Calendar.MONTH));
                hideCalendarView.setVisibility(View.VISIBLE);
                ObjectAnimator showAnimator = ObjectAnimator.ofFloat(hideCalendarView,
                        "translationX", calendarView.getWidth(), 0);
                final ObjectAnimator hideAnimator = ObjectAnimator.ofFloat(calendarView,
                        "translationX", 0, -calendarView.getWidth());
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(showAnimator).with(hideAnimator);
                animatorSet.setDuration(300);
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        CalendarView tmp = hideCalendarView;
                        hideCalendarView = calendarView;
                        calendarView = tmp;
                        hideCalendarView.setVisibility(View.INVISIBLE);
                    }
                });
                animatorSet.start();
                initText();
            }
        });

        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        tracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getY();
        tracker.addMovement(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = mDownX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mIsScroll && Math.abs(x - mDownX) > mTouchSlop) {
                    mIsScroll = true;
                }
                mLastX = x;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mIsScroll) {
                    tracker.computeCurrentVelocity(1000);
                    if (Math.abs(tracker.getXVelocity()) > 50) {
                        if (tracker.getXVelocity() > 0) {
                            back.performClick();
                        } else {
                            forward.performClick();
                        }
                    }
                }
                mIsScroll = false;
                break;
        }
        return true;
    }

    private void initText() {
        year.setText(getResources().getString(R.string.year, calendar.get(Calendar.YEAR)));
        month.setText(getResources().getString(R.string.month, calendar.get(Calendar.MONTH) + 1));
    }
}
