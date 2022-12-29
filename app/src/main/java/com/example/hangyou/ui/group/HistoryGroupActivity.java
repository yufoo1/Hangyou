package com.example.hangyou.ui.group;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.R;
import com.example.hangyou.utils.MysqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class HistoryGroupActivity extends AppCompatActivity {
    ArrayList<HashMap<String, Object>> data;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history_group);
        SharedPreferences sp = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int theme = sp.getInt("theme", 0);
        switch (theme) {
            case 0: findViewById(R.id.fragment_history_group).setBackgroundResource(R.color.purple_2); break;
            case 1: findViewById(R.id.fragment_history_group).setBackgroundResource(R.color.blue_2); break;
            case 2: findViewById(R.id.fragment_history_group).setBackgroundResource(R.color.red_2); break;
            case 3: findViewById(R.id.fragment_history_group).setBackgroundResource(R.color.yellow_2); break;
            case 4: findViewById(R.id.fragment_history_group).setBackgroundResource(R.color.blue_6); break;
            case 5: findViewById(R.id.fragment_history_group).setBackgroundResource(R.color.red_4); break;
            case 6: findViewById(R.id.fragment_history_group).setBackgroundResource(R.color.yellow_6); break;
            case 7: findViewById(R.id.fragment_history_group).setBackgroundResource(R.color.gray_2); break;
        }
        try {
            showHistoryGroupCard();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showHistoryGroupCard() throws SQLException {
        data = new ArrayList<>();
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        AtomicBoolean flag1 = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from user_group, user, user_group_relation where user.account=? and user.id=user_group_relation.userId and user_group.id=user_group_relation.groupId";
                PreparedStatement ps = connection.prepareStatement(sql);
                SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                String account = sp.getString("account", "defaultValue");
                ps.setString(1, account);
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag1.get());
        ArrayList<Integer> idLists = new ArrayList<>();
        HashMap<String, Object> item;
        while(resultSet.get().next()) {
            item = new HashMap<>();
            item.put("groupName", resultSet.get().getString("groupName"));
            item.put("groupType", resultSet.get().getString("groupType"));
            item.put("groupInitiator", resultSet.get().getString("groupInitiator"));
            String  groupYear = resultSet.get().getString("year");
            String groupMonth = resultSet.get().getString("month");
            String groupDay = resultSet.get().getString("day");
            String groupDate = groupYear + "-" + groupMonth + "-" + groupDay;
            item.put("groupDate", groupDate);
            item.put("groupMaleExpectedNum", Integer.parseInt(resultSet.get().getString("maleExpect")));
            item.put("groupMaleNowNum", Integer.parseInt(resultSet.get().getString("maleNow")));
            item.put("groupFemaleExpectedNum", Integer.parseInt(resultSet.get().getString("femaleExpect")));
            item.put("groupFemaleNowNum", Integer.parseInt(resultSet.get().getString("femaleNow")));
            item.put("groupDescription", resultSet.get().getString("description"));
            item.put("id", resultSet.get().getString("id"));
            idLists.add(Integer.parseInt(resultSet.get().getString("id")));
            data.add(item);
        }
        HistoryGroupCardAdapter adapter = new HistoryGroupCardAdapter(this, data);
        ListView historyGroupCards = findViewById(R.id.history_group_list);
        historyGroupCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        historyGroupCards.setOnItemClickListener((parent, view, position, id) -> {
            Bundle bundle=new Bundle();
            bundle.putInt("id", idLists.get(position));
            Intent intent =new Intent(this, GroupCardDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        });
    }
}
