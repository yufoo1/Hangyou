package com.example.hangyou.ui.tree_hole;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.R;
import com.example.hangyou.utils.DataBaseHelper;
import com.example.hangyou.utils.MysqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class TreeHoleHotPost extends AppCompatActivity {
    ArrayList<HashMap<String, Object>> data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tree_hole_hotpost);
        SharedPreferences sp = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int theme = sp.getInt("theme", 0);
        switch (theme) {
            case 0: findViewById(R.id.fragment_tree_hole_hotpost).setBackgroundResource(R.color.purple_2); break;
            case 1: findViewById(R.id.fragment_tree_hole_hotpost).setBackgroundResource(R.color.blue_2); break;
            case 2: findViewById(R.id.fragment_tree_hole_hotpost).setBackgroundResource(R.color.red_2); break;
            case 3: findViewById(R.id.fragment_tree_hole_hotpost).setBackgroundResource(R.color.yellow_2); break;
            case 4: findViewById(R.id.fragment_tree_hole_hotpost).setBackgroundResource(R.color.blue_6); break;
            case 5: findViewById(R.id.fragment_tree_hole_hotpost).setBackgroundResource(R.color.red_4); break;
            case 6: findViewById(R.id.fragment_tree_hole_hotpost).setBackgroundResource(R.color.yellow_6); break;
            case 7: findViewById(R.id.fragment_tree_hole_hotpost).setBackgroundResource(R.color.gray_2); break;
        }
        initClickListener();
        try {
            showPostCards();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initClickListener() {
        findViewById(R.id.hotpost_back_tree_hole).setOnClickListener(v -> backToTreeHole());
    }

    private void backToTreeHole() {
        Intent intent = new Intent();
        intent.setClass(this, TreeHoleActivity.class);
        startActivity(intent);
    }

    private void showPostCards() throws SQLException {
        data = new ArrayList<>();

        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        AtomicBoolean flag1 = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from post order by likeNum DESC,commentNum DESC";
                PreparedStatement ps = connection.prepareStatement(sql);
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag1.get());

        ArrayList<Integer> idLists = new ArrayList<>();
        HashMap<String, Object> item;
        int count = 0;
        while(resultSet.get().next()) {
            item = new HashMap<>();
            item.put("postName", resultSet.get().getString("postName"));
            item.put("postTime", resultSet.get().getString("createTime"));
            item.put("numOfLike", resultSet.get().getString("likeNum"));
            item.put("numOfComment", resultSet.get().getString("commentNum"));
            int userId = Integer.parseInt(resultSet.get().getString("userId"));

            AtomicReference<ResultSet> resultSet2 = new AtomicReference<>();
            AtomicBoolean flag2 = new AtomicBoolean(false);
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "select * from user where id=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(userId));
                    resultSet2.set(ps.executeQuery());
                    flag2.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
            while(!flag2.get());
            resultSet2.get().first();

            item.put("postUser", resultSet2.get().getString("username"));
            item.put("id", resultSet.get().getString("id"));
            idLists.add(Integer.parseInt(resultSet.get().getString("id")));
            data.add(item);
            count++;
            if(count==10) break;
        }

        PostCardAdapter adapter = new PostCardAdapter(TreeHoleHotPost.this, data);
        ListView postCards = findViewById(R.id.hot_post_cards);
        postCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        postCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putInt("id", idLists.get(position));
                Intent intent =new Intent(TreeHoleHotPost.this, TreeHolePostView.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

    }
}
