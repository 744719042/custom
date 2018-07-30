package com.example.custom.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.custom.R;
import com.example.custom.utils.CommonUtils;

import java.util.LinkedList;

/**
 * Created by Administrator on 2018/1/29.
 */

public class BannerImageAdapter extends BannerAdapter {
    private int[] resArr = new int[] {
            R.drawable.blue,
            R.drawable.good,
            R.drawable.red_flower
    };

    private String[] mTitles = new String[] {
            "蓝色", "红绿", "红色"
    };
    private LinkedList<ImageView> mList = new LinkedList<>();

    public BannerImageAdapter(Context context) {
        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mList.addLast(imageView);
        }
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position % resArr.length;
        ImageView imageView = null;
        if (!mList.isEmpty()) {
            imageView = mList.removeFirst();
        } else {
            imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        imageView.setImageResource(resArr[realPosition]);
        container.addView(imageView);
        imageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        imageView.getLayoutParams().height = CommonUtils.dp2px(150);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof ImageView) {
            ImageView imageView = (ImageView) object;
            container.removeView(imageView);
            mList.addLast(imageView);
        }
    }

    @Override
    public int getItemCount() {
        return resArr.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position % resArr.length];
    }
}
