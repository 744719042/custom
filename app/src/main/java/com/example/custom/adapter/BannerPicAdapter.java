package com.example.custom.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.custom.R;
import com.example.custom.utils.CommonUtils;

/**
 * Created by Administrator on 2018/1/30.
 */

public class BannerPicAdapter extends PagerAdapter {
    private int[] resArr = new int[] {
            R.drawable.blue,
            R.drawable.good,
            R.drawable.red_flower,
            R.drawable.yellow_mountain
    };

    private String[] mTitles = new String[] {
            "蓝色", "红绿", "红色", "红黄"
    };

    public BannerPicAdapter() {
    }

    @Override
    public int getCount() {
        return resArr.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(resArr[position]);
        container.addView(imageView);
        imageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        imageView.getLayoutParams().height = CommonUtils.dp2px(150);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
