package com.example.custom.widget.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.custom.R;
import com.example.custom.utils.CommonUtils;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/2/9.
 */

public class CalendarView extends View {
    private static final String TAG = "CalendarView";

    private static final int DEFAULT_CELL_WIDTH = CommonUtils.dp2px(45);
    private static final int DEFAULT_CELL_HEIGHT = CommonUtils.dp2px(45);
    private static final int DEFAULT_TEXT_SIZE = 16;
    private static final int DEFAULT_DAY_CELL_WIDTH = DEFAULT_CELL_WIDTH;
    private static final int DEFAULT_DAY_CELL_HEIGHT = CommonUtils.dp2px(30);
    private static final int COLUMN = 7;
    private static final int ROW = 6;
    private static final int[] MONTH_DAYS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private int cellWidth = DEFAULT_CELL_WIDTH;
    private int cellHeight = DEFAULT_CELL_HEIGHT;
    private int textSize = DEFAULT_TEXT_SIZE;
    private int dayCellWidth = DEFAULT_DAY_CELL_WIDTH;
    private int dayCellHeight = DEFAULT_DAY_CELL_HEIGHT;
    private int textColor;
    private int selectColor;
    private int todayColor;
    private Paint paint;

    private Calendar calendar = Calendar.getInstance();
    private Calendar tmpCalendar = Calendar.getInstance();

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CalendarView);
            cellWidth = dayCellWidth = typedArray.getDimensionPixelSize(R.styleable.CalendarView_cell_width, DEFAULT_CELL_WIDTH);
            cellHeight = typedArray.getDimensionPixelSize(R.styleable.CalendarView_cell_height, DEFAULT_CELL_HEIGHT);
            dayCellHeight = typedArray.getDimensionPixelSize(R.styleable.CalendarView_day_cell_height, DEFAULT_DAY_CELL_HEIGHT);
            textColor = typedArray.getColor(R.styleable.CalendarView_text_color, getResources().getColor(R.color.black));
            selectColor = typedArray.getColor(R.styleable.CalendarView_select_color, getResources().getColor(R.color.translucent_colorPrimary));
            todayColor = typedArray.getColor(R.styleable.CalendarView_today_color, getResources().getColor(R.color.colorAccent));
            typedArray.recycle();
        } else {
            textColor = getResources().getColor(R.color.black);
            selectColor = getResources().getColor(R.color.translucent_colorPrimary);
            todayColor = getResources().getColor(R.color.colorAccent);
        }
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(cellWidth * COLUMN +
                getPaddingLeft() + getPaddingRight(), MeasureSpec.EXACTLY);

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(cellHeight * (ROW - 1) + dayCellHeight +
                getPaddingTop() + getPaddingBottom(), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);
        drawDay(canvas);
        drawDate(canvas);
    }

    private boolean isLeaf(int year) {
        return year % 100 == 0 && year % 400 == 0 || year % 4 == 0;
    }

    private void drawDate(Canvas canvas) {
        int year = calendar.get(Calendar.YEAR);
        MONTH_DAYS[1] = isLeaf(year) ? 29 : 28;
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        tmpCalendar.set(year, month, 1, 1, 1);
        int day = tmpCalendar.get(Calendar.DAY_OF_WEEK);
        tmpCalendar.add(Calendar.DATE, -1);
        int lastMonth = tmpCalendar.get(Calendar.MONTH);

        Log.d(TAG, "year = " + year + ", month = " + month + ", day = " + day + ", lastMonth = " + lastMonth);
        int index = 0;
        int days = MONTH_DAYS[lastMonth];
        for (int i = 0; i < day - 2; i++, index++) {
            paint.setColor(getResources().getColor(R.color.gray));
            drawCell(canvas, index, days - day + i + 2);
        }

        days = MONTH_DAYS[month];
        for (int i = 0; i < days; i++, index++) {
            if (date == i + 1) {
                paint.setColor(todayColor);
            } else {
                paint.setColor(textColor);
            }
            drawCell(canvas, index, i + 1);
        }

        int cellCount = ROW * COLUMN;
        for (int i = index ; i < cellCount; i++) {
            paint.setColor(getResources().getColor(R.color.gray));
            drawCell(canvas, i, i - index + 1);
        }
    }

    private void drawCell(Canvas canvas, int i, int date) {
        int row = i / COLUMN, column = i % COLUMN;
        paint.setTextSize(CommonUtils.dp2px(13));
        int textWidth = (int) paint.measureText(String.valueOf(date));
        // 单元格中间点的位置
        int x = getPaddingLeft() + column * cellWidth + cellWidth / 2 - textWidth / 2;
        int y = getPaddingTop() + (row + 1) * cellHeight + dayCellHeight - (dayCellHeight - CommonUtils.dp2px(13)) / 2;
        canvas.drawText(String.valueOf(date), x, y, paint);
    }

    private void drawDay(Canvas canvas) {
        String[] days = getResources().getStringArray(R.array.weekdays);
        int start = getPaddingLeft();
        for (int i = 0; i < COLUMN; i++) {
            paint.setTextSize(CommonUtils.dp2px(13));
            paint.setColor(textColor);
            int textWidth = (int) paint.measureText(days[i]);
            // 单元格中间点的位置
            int x = (start + (start + cellWidth)) / 2 - textWidth / 2;
            start += cellWidth;
            int y = getPaddingTop() + dayCellHeight - (dayCellHeight - CommonUtils.dp2px(13)) / 2;
            canvas.drawText(days[i], x, y, paint);
        }
    }

    private void drawGrid(Canvas canvas) {
        int width = getMeasuredWidth(), height = getMeasuredHeight();
        canvas.drawLine(getPaddingLeft(), getPaddingTop(), width - getPaddingRight(), getPaddingTop(), paint);
        for (int i = 1; i < ROW + 1; i++) {
            int y = getPaddingTop() + dayCellHeight + (i - 1) * cellHeight;
            if (i == 1 || i == ROW) {
                canvas.drawLine(getPaddingLeft(), y, width - getPaddingRight(), y, paint);
            }
        }
    }
}
