package com.example.hangyou.ui.home;

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
        String description = Objects.requireNonNull(data.get(position).get("description")).toString();
        if(description.length() > 15) {
            tv_description.setText(description.substring(0, 15) + "...");
        } else {
            tv_description.setText(description);
        }
        TextView tv_phone = convertView.findViewById(R.id.user_card_phone);
        tv_phone.setText(Objects.requireNonNull(data.get(position).get("phone")).toString());
        TextView tv_gender = convertView.findViewById(R.id.user_card_gender);
        tv_gender.setText(Objects.requireNonNull(data.get(position).get("gender")).toString());
        String headPortrait = Objects.requireNonNull(data.get(position).get("headPortrait")).toString();
        if(!Objects.equals(headPortrait, "")) {
            byte[] bytes= Base64.decode(headPortrait, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            ((ImageView) convertView.findViewById(R.id.user_card_head)).setImageBitmap(bitmap);

        }
        return convertView;
    }
}
