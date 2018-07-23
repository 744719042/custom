package com.example.custom.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

public class CustomTextView extends AppCompatTextView {
    private Paint mInnerPaint;
    private Paint mOuterPaint;
    private float mProgress;
    private int colorDefault = Color.BLACK;
    private int colorNew = Color.RED;
    private int oritention = 0; // 0 正向 1反向

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTextSize(15);
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setDither(true);
        mInnerPaint.setTextSize(dp2sp(15));
        mInnerPaint.setColor(colorDefault);
        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setDither(true);
        mOuterPaint.setTextSize(dp2sp(15));
        mOuterPaint.setColor(colorNew);
        mProgress = 0.5f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint.FontMetricsInt fontMetricsInt = mOuterPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        if (oritention == 0) {
            int x = (int) (getWidth() / 2 - mInnerPaint.measureText(getText().toString()) / 2);
            canvas.save();
            canvas.clipRect(0, 0, mProgress * getWidth(), getHeight());
            canvas.drawText(getText().toString(), x, baseLine, mInnerPaint);
            canvas.restore();

            canvas.save();
            canvas.clipRect(mProgress * getWidth(), 0, getWidth(), getHeight());
            canvas.drawText(getText().toString(), x, baseLine, mOuterPaint);
            canvas.restore();
        } else {
            int x = (int) (getWidth() / 2 - mInnerPaint.measureText(getText().toString()) / 2);
            canvas.save();
            canvas.clipRect(0, 0, (1.0f - mProgress) * getWidth(), getHeight());
            canvas.drawText(getText().toString(), x, baseLine, mOuterPaint);
            canvas.restore();

            canvas.save();
            canvas.clipRect((1.0f - mProgress) * getWidth(), 0, getWidth(), getHeight());
            canvas.drawText(getText().toString(), x, baseLine, mInnerPaint);
            canvas.restore();
        }
    }

    public int dp2sp(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        invalidate();
    }

    public void setOritention(int oritention) {
        this.oritention = oritention;
    }
}
