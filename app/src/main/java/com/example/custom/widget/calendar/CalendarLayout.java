package com.example.custom.widget.calendar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
    private CalendarView calendarView;
    private Calendar calendar = Calendar.getInstance();

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
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.widget_calendar_layout, this);
        back = (ImageView) findViewById(R.id.back);
        forward = (ImageView) findViewById(R.id.forward);
        year = (TextView) findViewById(R.id.year);
        month = (TextView) findViewById(R.id.month);
        calendarView = (CalendarView) findViewById(R.id.calendar);

        initText();

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.YEAR, calendarView.getYear());
                calendar.set(Calendar.MONTH, calendarView.getMonth());
                calendar.add(Calendar.MONTH, -1);
                calendarView.setYear(calendar.get(Calendar.YEAR));
                calendarView.setMonth(calendar.get(Calendar.MONTH));
                initText();
            }
        });

        forward.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.YEAR, calendarView.getYear());
                calendar.set(Calendar.MONTH, calendarView.getMonth());
                calendar.add(Calendar.MONTH, 1);
                calendarView.setYear(calendar.get(Calendar.YEAR));
                calendarView.setMonth(calendar.get(Calendar.MONTH));
                initText();
            }
        });
    }

    private void initText() {
        year.setText(getResources().getString(R.string.year, calendarView.getYear()));
        month.setText(getResources().getString(R.string.month, calendarView.getMonth() + 1));
    }
}
