package com.example.custom.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.custom.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */

public class TransformImageAdapter extends PagerAdapter {
    private int[] resArr = new int[] {
            R.drawable.blue,
            R.drawable.good,
            R.drawable.red_flower,
            R.drawable.yellow_mountain,
            R.drawable.mountain,
            R.drawable.river,
            R.drawable.tree
    };

    private String[] mTitles = new String[] {
            "蓝色", "红绿", "红色", "红黄", "大山", "河流", "大树"
    };
    private List<ImageView> mList = new ArrayList<>(resArr.length);

    public TransformImageAdapter() {
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
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(resArr[position]);
            imageView.setTag(position);
            container.addView(imageView);
            mList.add(position, imageView);
            imageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            imageView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            container.addView(mList.get(position));
        }
        return mList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof ImageView) {
            container.removeView((ImageView) object);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
