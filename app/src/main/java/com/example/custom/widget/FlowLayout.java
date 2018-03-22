package com.example.custom.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.custom.R;
import com.example.custom.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 */

public class FlowLayout extends LinearLayout {

    private List<String> mData = new ArrayList<>();
    private List<List<View>> mLineViews = new ArrayList<>();
    private List<Integer> mHeights = new ArrayList<>();
    private LayoutInflater mInflater;
    private int mVerticalMargin = CommonUtils.dp2px(5);
    private int mHorizontalMargin = CommonUtils.dp2px(5);

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
            mVerticalMargin = array.getDimensionPixelOffset(R.styleable.FlowLayout_vertical_margin, CommonUtils.dp2px(5));
            mHorizontalMargin = array.getDimensionPixelOffset(R.styleable.FlowLayout_horizontal_margin, CommonUtils.dp2px(5));
            array.recycle();
        }
        init();
    }

    private void init() {
        mInflater = LayoutInflater.from(getContext());
    }

    public void setData(List<String> data) {
        mData.clear();
        mData.addAll(data);
        removeAllViews();
        mLineViews.clear();
        mHeights.clear();

        int count = data.size();
        for (int i = 0; i < count; i++) {
            String str = data.get(i);
            View view = mInflater.inflate(R.layout.item_comment, this, false);
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText(str);
            addView(view);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0, height = 0, lineWidth = 0, lineHeight = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = Math.min(widthSize, CommonUtils.getScreenWidth());
        }

        mLineViews.clear();
        mHeights.clear();

        int count = getChildCount();
        int maxLineWidth = width - getPaddingLeft() - getPaddingRight();
        List<View> lineViews = new ArrayList<>();
        mLineViews.add(lineViews);
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            if (lineWidth + child.getMeasuredWidth() + mHorizontalMargin > maxLineWidth) {
                mHeights.add(lineHeight);
                lineViews = new ArrayList<>();
                mLineViews.add(lineViews);
                lineViews.add(child);
                lineWidth = child.getMeasuredWidth() + mHorizontalMargin;
                lineHeight = child.getMeasuredHeight() + mVerticalMargin;
            } else {
                lineViews.add(child);
                if (lineHeight < child.getMeasuredHeight() + mVerticalMargin) {
                    lineHeight = child.getMeasuredHeight() + mVerticalMargin;
                }
                lineWidth += child.getMeasuredWidth() + mHorizontalMargin;

                if (i == count - 1) {
                    mHeights.add(lineHeight);
                }
            }
        }

        int maxHeight = getPaddingBottom() + getPaddingTop();
        for (int i = 0; i < mHeights.size(); i++) {
            maxHeight += mHeights.get(i);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = Math.min(maxHeight, heightSize);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int x = getPaddingLeft(), y = getPaddingTop(), count = mLineViews.size(), lineHeight;
            for (int i = 0; i < count; i++) {
                List<View> viewList = mLineViews.get(i);
                lineHeight = mHeights.get(i);
                for (int j = 0; j < viewList.size(); j++) {
                    View child = viewList.get(j);
                    child.layout(x, y, x + child.getMeasuredWidth(), y + child.getMeasuredHeight());
                    x += child.getMeasuredWidth() + mHorizontalMargin;
                }
                x = getPaddingLeft();
                y += lineHeight;
            }
        }
    }
}
