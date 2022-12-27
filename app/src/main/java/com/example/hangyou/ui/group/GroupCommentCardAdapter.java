package com.example.hangyou.ui.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hangyou.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GroupCommentCardAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> data;
    private Context ctx;

    public GroupCommentCardAdapter(Context ctx, ArrayList<HashMap<String, Object>> data) {
        this.data = data;
        this.ctx = ctx;
    }
    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(ctx).inflate(R.layout.comment_item, parent, false);
        TextView tv_username = convertView.findViewById(R.id.group_comment_username);
        tv_username.setText(Objects.requireNonNull(data.get(position).get("username")).toString());
        TextView tv_comment = convertView.findViewById(R.id.group_comment_content);
        tv_comment.setText(Objects.requireNonNull(data.get(position).get("comment")).toString());
        TextView tv_createAt = convertView.findViewById(R.id.group_comment_time);
        tv_createAt.setText(Objects.requireNonNull(data.get(position).get("createdAt")).toString());
        return convertView;
    }
}
