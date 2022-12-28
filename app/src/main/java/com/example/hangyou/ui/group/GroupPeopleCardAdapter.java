package com.example.hangyou.ui.group;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hangyou.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GroupPeopleCardAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> data;
    private Context ctx;

    public GroupPeopleCardAdapter(Context ctx, ArrayList<HashMap<String, Object>> data) {
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
        convertView = LayoutInflater.from(ctx).inflate(R.layout.group_user, parent, false);
        TextView tv_username = convertView.findViewById(R.id.group_user_card_username);
        tv_username.setText(Objects.requireNonNull(data.get(position).get("username")).toString());

        String headPortrait = Objects.requireNonNull(data.get(position).get("head_portrait")).toString();
        byte[] bytes= Base64.decode(headPortrait, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        ImageView iv_head = convertView.findViewById(R.id.group_user_card_head_portrait);
        iv_head.setImageBitmap(bitmap);

        return convertView;
    }
}
