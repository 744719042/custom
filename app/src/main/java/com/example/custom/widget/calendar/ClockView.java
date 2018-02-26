package com.example.custom.widget.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.custom.R;
import com.example.custom.utils.CommonUtils;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/2/23.
 */

public class ClockView extends View {
    private static final int SHORT_TICK = CommonUtils.dp2px(10);
    private static final int LONG_TICK = CommonUtils.dp2px(20);
    private int width = CommonUtils.getScreenWidth() / 2;
    private int height = width;
    private Paint paint;
    private static final String[] HOURS = {"XII", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI"};
    private static final int HOURS_COUNT = 12;
    private static final int MINUTES_COUNT = 12 * 5;
    private Calendar calendar;
    private boolean autoUpdate = true;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (autoUpdate) {
                calendar.setTimeInMillis(System.currentTimeMillis());
                invalidate();
                postDelayed(runnable, 1000);
            }
        }
    };

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(R.color.transparent);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        calendar = Calendar.getInstance();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    public void setAutoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
        if (!autoUpdate) {
            removeCallbacks(runnable);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        calendar.setTimeInMillis(System.currentTimeMillis());
        drawClock(canvas);
        if (autoUpdate) {
            postDelayed(runnable, 1000);
        }
    }

    public int getRealWidth() {
        return width;
    }

    public int getRealHeight() {
        return height;
    }

    private void drawClock(Canvas canvas) {
        paint.setStrokeWidth(CommonUtils.dp2px(1));
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(CommonUtils.dp2px(13));
        int centerX = width / 2;
        int centerY = height / 2;
        canvas.save();
        canvas.drawCircle(centerX, centerY, centerX - CommonUtils.dp2px(5), paint);
        for (int i = 0; i < MINUTES_COUNT; i++) {
            if (i % 5 == 0) { // 画时
                canvas.drawLine(centerX, CommonUtils.dp2px(5), centerX, LONG_TICK, paint);
            } else { // 画分钟
                canvas.drawLine(centerX, CommonUtils.dp2px(5), centerX, SHORT_TICK, paint);
            }
            canvas.rotate(360 / MINUTES_COUNT, centerX, centerY);
        }
        canvas.restore();

        // 画时刻
        canvas.save();
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < HOURS_COUNT; i++) {
            int textWidth = (int) paint.measureText(HOURS[i]);
            canvas.drawText(HOURS[i], centerX - textWidth / 2, CommonUtils.dp2px(5 + 13) + LONG_TICK, paint);
            canvas.rotate(360 / HOURS_COUNT, centerX, centerY);
        }
        canvas.restore();

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        canvas.save();
        float degree = 360 / HOURS_COUNT * (hour + (float) minute / 60);

        canvas.rotate(degree, centerX, centerY);
        paint.setStrokeWidth(CommonUtils.dp2px(5));
        canvas.drawLine(centerX, centerY, centerX, centerY - CommonUtils.dp2px(30), paint);

        canvas.rotate(-degree, centerX, centerY);
        canvas.rotate(360 / MINUTES_COUNT * minute, centerX, centerY);
        paint.setStrokeWidth(CommonUtils.dp2px(3));
        canvas.drawLine(centerX, centerY, centerX, centerY - CommonUtils.dp2px(40), paint);

        canvas.rotate(-360 / MINUTES_COUNT * minute, centerX, centerY);
        canvas.rotate(360 / MINUTES_COUNT * second, centerX, centerY);
        paint.setStrokeWidth(CommonUtils.dp2px(1));
        canvas.drawLine(centerX, centerY, centerX, centerY - CommonUtils.dp2px(50), paint);
        canvas.restore();
    }
}
