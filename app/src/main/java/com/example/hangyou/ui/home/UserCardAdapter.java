package com.example.hangyou.ui.home;

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

public class UserCardAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> data;
    private Context ctx;

    public UserCardAdapter(Context ctx, ArrayList<HashMap<String, Object>> data) {
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
        convertView = LayoutInflater.from(ctx).inflate(R.layout.user_card, parent, false);
        TextView tv_username = convertView.findViewById(R.id.user_card_username);
        tv_username.setText(Objects.requireNonNull(data.get(position).get("username")).toString());
        TextView tv_description = convertView.findViewById(R.id.user_card_description);
        tv_description.setText(Objects.requireNonNull(data.get(position).get("description")).toString());
        return convertView;
    }
}
