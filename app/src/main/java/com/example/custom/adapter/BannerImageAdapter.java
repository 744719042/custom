package com.example.custom.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.custom.R;
import com.example.custom.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */

public class BannerImageAdapter extends BannerAdapter {
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

    public BannerImageAdapter() {
        for (int i = 0; i < resArr.length; i++) {
            mList.add(null);
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
        if (mList.get(realPosition) == null) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(resArr[position]);
            container.addView(imageView);
            mList.add(realPosition, imageView);
            imageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            imageView.getLayoutParams().height = CommonUtils.dp2px(150);
        } else {
            container.addView(mList.get(realPosition));
        }
        return mList.get(realPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof ImageView) {
            container.removeView((ImageView) object);
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
