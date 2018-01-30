package com.example.custom.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.custom.R;
import com.example.custom.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class GalleryImageAdapter extends PagerAdapter {
    private int[] resArr = new int[] {
            R.drawable.blue,
            R.drawable.good,
            R.drawable.red_flower,
            R.drawable.yellow_mountain
    };

    private String[] mTitles = new String[] {
            "蓝色", "红绿", "红色", "红黄"
    };
    private List<ImageView> mList = new ArrayList<>(resArr.length);

    public GalleryImageAdapter() {
        for (int i = 0; i < resArr.length; i++) {
            mList.add(null);
        }
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
        if (mList.get(position) == null) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(resArr[position]);
            imageView.setPadding(CommonUtils.dp2px(10), 0, CommonUtils.dp2px(10), 0);
            container.addView(imageView);
            mList.add(position, imageView);
            return imageView;
        } else {
            View view = mList.get(position);
            if (view.getParent() instanceof ViewGroup) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            container.addView(view);
            return view;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof ImageView) {
            container.removeView((ImageView) object);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position % resArr.length];
    }
}
