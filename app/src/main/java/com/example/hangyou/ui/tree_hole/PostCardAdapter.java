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

public class PostCardAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> data;
    private Context ctx;

    public PostCardAdapter(Context ctx, ArrayList<HashMap<String, Object>> data) {
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
        convertView = LayoutInflater.from(ctx).inflate(R.layout.post_card, parent, false);
        TextView tv_postName = convertView.findViewById(R.id.postCardPostName);
        tv_postName.setText(Objects.requireNonNull(data.get(position).get("postName")).toString());
        TextView tv_postTime = convertView.findViewById(R.id.postCardPostTime);
        tv_postTime.setText(Objects.requireNonNull(data.get(position).get("postTime")).toString());
        TextView tv_postUser = convertView.findViewById(R.id.postCardUserName);
        tv_postUser.setText(Objects.requireNonNull(data.get(position).get("postUser")).toString());
        TextView tv_numOfLike = convertView.findViewById(R.id.num_of_like);
        tv_numOfLike.setText(Objects.requireNonNull(data.get(position).get("numOfLike")).toString());
        TextView tv_numOfComment = convertView.findViewById(R.id.num_of_comment);
        tv_numOfComment.setText(Objects.requireNonNull(data.get(position).get("numOfComment")).toString());

        return convertView;
    }
}
