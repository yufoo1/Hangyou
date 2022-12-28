package com.example.hangyou.ui.group;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hangyou.R;
import com.example.hangyou.utils.DataBaseHelper;
import com.example.hangyou.utils.MysqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GroupCommentDialog extends Dialog {
    Activity context;
    int groupId;
    int userId;

    public GroupCommentDialog(Activity context, int theme, int groupId, int userId) {
        super(context, theme);
        this.context= context;
        this.groupId = groupId;
        this.userId = userId;
    }

    private void commit() {
        EditText et_comment = findViewById(R.id.group_comment_input);
        String comment = et_comment.getText().toString();
        new Thread(() -> {
            try {
                Connection conn = MysqlConnector.getConnection();
                String s = "insert into group_comment(groupId, userId, createdAt, comment) values(?, ?, datetime('now','localtime'), ?)";
                PreparedStatement p = conn.prepareStatement(s);
                p.setString(1, String.valueOf(groupId));
                p.setString(2, String.valueOf(userId));
                p.setString(3, comment);
                p.executeUpdate();
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    private void clockDialog() {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.group_comment_edit);
        Window dialogWindow = this.getWindow();
        WindowManager m = context.getWindowManager();
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.height = (int) (d.getHeight() * 0.55);
        p.width = (int) (d.getWidth() * 0.8);
        dialogWindow.setAttributes(p);

        Button bt_commit = findViewById(R.id.group_comment_commit);
        bt_commit.setOnClickListener(v -> commit());
        Button bt_return = findViewById(R.id.group_comment_return);
        bt_return.setOnClickListener(v -> clockDialog());

        this.setCancelable(true);
    }
}
