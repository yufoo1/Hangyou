package com.example.hangyou.ui.tree_hole;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.R;
import com.example.hangyou.utils.DataBaseHelper;
import com.example.hangyou.utils.MysqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class TreeHolePostCommentEdit extends AppCompatActivity {
    private int postId;
    SQLiteDatabase database;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tree_hole_comment_create);
        Bundle receiver = getIntent().getExtras();
        postId = receiver.getInt("postId");
        initClickListener();
    }

    private void initClickListener() {
        findViewById(R.id.click_to_comment).setOnClickListener(v -> {
            try {
                commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        findViewById(R.id.comment_back_post).setOnClickListener(v -> returnToPost());
    }

    private void returnToPost() {
        Bundle bundle=new Bundle();
        bundle.putInt("id", postId);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(TreeHolePostCommentEdit.this, TreeHolePostView.class);
        startActivity(intent);
        finish();
    }

    private void commit() throws SQLException {
        EditText et_commentText = findViewById(R.id.post_comment_text_input);
        String commentText = et_commentText.getText().toString();

        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");

        AtomicReference<ResultSet> resultSet2 = new AtomicReference<>();
        AtomicBoolean flag2 = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from user where account=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1,account);
                resultSet2.set(ps.executeQuery());
                flag2.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag2.get());
        resultSet2.get().first();
        String userId = resultSet2.get().getString("id");

        flag2.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "insert into post_comment(commentText, userId, postId) " +
                        "values (?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, commentText);
                ps.setString(2, userId);
                ps.setString(3, String.valueOf(postId));
                ps.executeUpdate();
                flag2.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag2.get());
        System.out.println("评论发布成功");

        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        AtomicBoolean flag1 = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from post where id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(postId));
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag1.get());
        resultSet.get().first();

        int commentNum = resultSet.get().getInt("commentNum");
        commentNum +=1;

        flag2.set(false);
        int finalCommentNum = commentNum;
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "UPDATE post SET commentNum = ? WHERE id = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(finalCommentNum));
                ps.setString(2, String.valueOf(postId));
                ps.executeUpdate();
                flag2.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag2.get());

        Bundle bundle=new Bundle();
        bundle.putInt("id", postId);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(TreeHolePostCommentEdit.this, TreeHolePostView.class);
        startActivity(intent);
    }
}
