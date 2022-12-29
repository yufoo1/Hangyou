package com.example.hangyou.ui.tree_hole;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.utils.DataBaseHelper;
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

public class TreeHolePostView extends AppCompatActivity {
    private int id;
    private boolean reportState=false;
    private boolean likeState=false;
    SQLiteDatabase database;
    ArrayList<HashMap<String, Object>> data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tree_hole_detail);
        SharedPreferences sp = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int theme = sp.getInt("theme", 0);
        switch (theme) {
            case 0: findViewById(R.id.fragment_tree_hole_detail).setBackgroundResource(R.color.purple_2); break;
            case 1: findViewById(R.id.fragment_tree_hole_detail).setBackgroundResource(R.color.blue_2); break;
            case 2: findViewById(R.id.fragment_tree_hole_detail).setBackgroundResource(R.color.red_2); break;
            case 3: findViewById(R.id.fragment_tree_hole_detail).setBackgroundResource(R.color.yellow_2); break;
            case 4: findViewById(R.id.fragment_tree_hole_detail).setBackgroundResource(R.color.blue_6); break;
            case 5: findViewById(R.id.fragment_tree_hole_detail).setBackgroundResource(R.color.red_4); break;
            case 6: findViewById(R.id.fragment_tree_hole_detail).setBackgroundResource(R.color.yellow_6); break;
            case 7: findViewById(R.id.fragment_tree_hole_detail).setBackgroundResource(R.color.gray_2); break;
        }
        DataBaseHelper helper = new DataBaseHelper(TreeHolePostView.this);
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists post(id integer primary key autoincrement, postName text," +
                " postText text, createTime text, userId int , likeNum int not null default 0,commentNum int not null default 0," +
                " reportNum int not null default 0, FOREIGN KEY(userId) REFERENCES user(id) )");
        Bundle receiver = getIntent().getExtras();
        id = receiver.getInt("id");
        try {
            initView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initClickListener();
    }

    private void initView() throws SQLException {
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        AtomicBoolean flag1 = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from post where id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(id));
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag1.get());

        resultSet.get().first();
        String postName = resultSet.get().getString("postName");
        ((TextView) (findViewById(R.id.postName))).setText(postName);
        String postText = resultSet.get().getString("postText");
        ((TextView) (findViewById(R.id.postText))).setText(postText);
        String createTime = resultSet.get().getString("createTime");
        ((TextView) (findViewById(R.id.postTime))).setText(createTime);
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

        String username = resultSet2.get().getString("username");
        ((TextView) (findViewById(R.id.postUserName))).setText(username);
        showCommentCards();
    }

    private void initClickListener() {
        findViewById(R.id.post_view_back_tree_hode).setOnClickListener(v -> jumpToTreeHole());
        findViewById(R.id.likePost).setOnClickListener(v -> {
            try {
                likePost();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        findViewById(R.id.commentPost).setOnClickListener(v -> commentPost());
        findViewById(R.id.reportPost).setOnClickListener(v -> {
            try {
                reportPost();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void reportPost() throws SQLException {
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        AtomicBoolean flag1 = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from post where id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(id));
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag1.get());

        resultSet.get().first();
        int reportNum = resultSet.get().getInt("reportNum");
        if(!reportState){
            reportNum+=1;
            reportState = true;
        } else {
            reportNum-=1;
            reportState = false;
        }
        int finalReportNum = reportNum;

        flag1.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "UPDATE post SET reportNum = ? WHERE id = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(finalReportNum));
                ps.setString(2, String.valueOf(id));
                ps.executeUpdate();
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag1.get());
    }

    private void commentPost() {
        Bundle bundle=new Bundle();
        bundle.putInt("postId", id);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(this, TreeHolePostCommentEdit.class);
        startActivity(intent);
        finish();
    }

    private void likePost() throws SQLException {

        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        AtomicBoolean flag1 = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from post where id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(id));
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag1.get());
        resultSet.get().first();
        int likeNum = resultSet.get().getInt("likeNum");
        if(!likeState){
            likeNum+=1;
            likeState = true;
        } else {
            likeNum-=1;
            likeState = false;
        }

        int finalLikeNum = likeNum;
        flag1.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "UPDATE post SET likeNum = ? WHERE id = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(finalLikeNum));
                ps.setString(2, String.valueOf(id));
                ps.executeUpdate();
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag1.get());

    }

    private void jumpToTreeHole() {
        Intent intent = new Intent();
        intent.setClass(this, TreeHoleActivity.class);
        startActivity(intent);
    }

    private void showCommentCards() throws SQLException {
        data = new ArrayList<>();

        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        AtomicBoolean flag1 = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from post_comment where postId=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1,String.valueOf(id));
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag1.get());

        HashMap<String, Object> item;
        while(resultSet.get().next()) {
            item = new HashMap<>();
            item.put("commentText", resultSet.get().getString("commentText"));
            item.put("commentTime", resultSet.get().getString("commentTime"));
            int userId = Integer.parseInt(resultSet.get().getString("userId"));

            AtomicReference<ResultSet> resultSet2 = new AtomicReference<>();
            AtomicBoolean flag2 = new AtomicBoolean(false);
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "select * from user where id=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1,String.valueOf(userId));
                    resultSet2.set(ps.executeQuery());
                    flag2.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
            while(!flag2.get());
            resultSet2.get().first();

            item.put("commentUsername", resultSet2.get().getString("username"));
            item.put("id", resultSet.get().getString("id"));
            //idLists.add(Integer.parseInt(resultSet.get().getString("id")));
            data.add(item);
        }

        PostCommentAdapter adapter = new PostCommentAdapter(TreeHolePostView.this, data);
        ListView postCards = findViewById(R.id.post_comment_cards);
        postCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
