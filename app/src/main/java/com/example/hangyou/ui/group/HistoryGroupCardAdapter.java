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

public class HistoryGroupCardAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> data;
    private Context ctx;
    public HistoryGroupCardAdapter(Context ctx, ArrayList<HashMap<String, Object>> data) {
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
        convertView = LayoutInflater.from(ctx).inflate(R.layout.history_group_card, parent, false);
        TextView tv_groupName = convertView.findViewById(R.id.history_group_card_groupName);
        tv_groupName.setText(Objects.requireNonNull(data.get(position).get("groupName")).toString());
        TextView tv_groupType = convertView.findViewById(R.id.history_group_card_groupType);
        tv_groupType.setText(Objects.requireNonNull(data.get(position).get("groupType")).toString());
        TextView tv_groupDate = convertView.findViewById(R.id.history_group_groupDate);
        tv_groupDate.setText(Objects.requireNonNull(data.get(position).get("groupDate")).toString());
        TextView tv_groupInitiator = convertView.findViewById(R.id.history_group_groupInitiator);
        tv_groupInitiator.setText(Objects.requireNonNull(data.get(position).get("groupInitiator")).toString());
        ProgressBar progressBar = convertView.findViewById(R.id.history_group_progress_bar);
        int groupMaleExpectedNum = (int) Objects.requireNonNull(data.get(position).get("groupMaleExpectedNum"));
        int groupMaleNowNum = (int) Objects.requireNonNull(data.get(position).get("groupMaleNowNum"));
        int groupFemaleExpectedNum = (int) Objects.requireNonNull(data.get(position).get("groupFemaleExpectedNum"));
        int groupFemaleNowNum = (int) Objects.requireNonNull(data.get(position).get("groupFemaleNowNum"));
        progressBar.setMax(groupMaleExpectedNum + groupFemaleExpectedNum);
        progressBar.setProgress(groupMaleNowNum);
        progressBar.setSecondaryProgress(groupMaleNowNum + groupFemaleNowNum);
        return convertView;
    }
}
