package com.example.hangyou.ui.tree_hole;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hangyou.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class PostCommentAdapter extends BaseAdapter{
    private ArrayList<HashMap<String, Object>> data;
    private Context ctx;

    public PostCommentAdapter(Context ctx, ArrayList<HashMap<String, Object>> data) {
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
        convertView = LayoutInflater.from(ctx).inflate(R.layout.post_comment_item, parent, false);
        TextView tv_commentUsername = convertView.findViewById(R.id.post_cmment_username);
        tv_commentUsername.setText(Objects.requireNonNull(data.get(position).get("commentUsername")).toString());
        TextView tv_comentTime = convertView.findViewById(R.id.post_comment_time);
        tv_comentTime.setText(Objects.requireNonNull(data.get(position).get("commentTime")).toString());
        TextView tv_commentText = convertView.findViewById(R.id.post_comment_text);
        tv_commentText.setText(Objects.requireNonNull(data.get(position).get("commentText")).toString());

        return convertView;
    }
}
