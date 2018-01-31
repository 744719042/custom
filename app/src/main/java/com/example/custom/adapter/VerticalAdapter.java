package com.example.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.custom.R;
import com.example.custom.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */

public class VerticalAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<User> data = new ArrayList<User>() {
        {
            add(new User(R.drawable.blue, "司马懿", "老而不死是为贼"));
            add(new User(R.drawable.red_flower, "曹操", "宁教我负天下人，不教天下人负我"));
            add(new User(R.drawable.good, "诸葛亮", "鞠躬尽瘁死而后已"));
        }
    };

    public VerticalAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public User getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.desc);
            viewHolder.portrait = (ImageView) convertView.findViewById(R.id.portrait);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        bindView(getItem(position), viewHolder);
        return convertView;
    }

    private void bindView(User item, ViewHolder viewHolder) {
        viewHolder.portrait.setImageResource(item.getPortrait());
        viewHolder.name.setText(item.getName());
        viewHolder.desc.setText(item.getDesc());
    }

    private static class ViewHolder {
        ImageView portrait;
        TextView name;
        TextView desc;
    }
}
