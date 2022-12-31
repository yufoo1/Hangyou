package com.example.hangyou.ui.home;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.utils.DataBaseHelper;
import com.example.hangyou.R;
import com.example.hangyou.ui.group.GroupActivity;
import com.example.hangyou.ui.tree_hole.TreeHoleActivity;
import com.example.hangyou.utils.MysqlConnector;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class HomePageActivity extends AppCompatActivity {
    SQLiteDatabase database;
    boolean hasHeadPortrait = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_page);
        SharedPreferences sp = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int theme = sp.getInt("theme", 0);
        switch (theme) {
            case 0: findViewById(R.id.home_page).setBackgroundResource(R.color.purple_2); break;
            case 1: findViewById(R.id.home_page).setBackgroundResource(R.color.blue_2); break;
            case 2: findViewById(R.id.home_page).setBackgroundResource(R.color.red_2); break;
            case 3: findViewById(R.id.home_page).setBackgroundResource(R.color.yellow_2); break;
            case 4: findViewById(R.id.home_page).setBackgroundResource(R.color.blue_6); break;
            case 5: findViewById(R.id.home_page).setBackgroundResource(R.color.red_4); break;
            case 6: findViewById(R.id.home_page).setBackgroundResource(R.color.yellow_6); break;
            case 7: findViewById(R.id.home_page).setBackgroundResource(R.color.gray_2); break;
        }
        try {
            initTextView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initClickListener();
    }

    private void initTextView() throws SQLException {
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        String headPortrait = sp.getString("head_portrait", "");
        AtomicBoolean flag1 = new AtomicBoolean(false);
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        flag1.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from user where account=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, account);
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag1.get());
        resultSet.get().next();
        TextView username = findViewById(R.id.home_page_username);
        username.setText(resultSet.get().getString("username"));
        TextView description = findViewById(R.id.home_page_description);
        description.setText(resultSet.get().getString("description"));
        if(!Objects.equals(headPortrait, "")) {
            byte[] bytes= Base64.decode(headPortrait, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            ((ImageView)findViewById(R.id.home_page_head_portrait)).setImageBitmap(bitmap);
            hasHeadPortrait = true;
        }
        int cnt;
        TextView followers = findViewById(R.id.home_page_followers);
        flag1.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from follow where followingAccount=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, account);
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag1.get());
        cnt = 0;
        while(resultSet.get().next()) cnt++;
        followers.setText(String.valueOf(cnt));
        TextView following = findViewById(R.id.home_page_following);
        flag1.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from follow where followerAccount=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, account);
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag1.get());
        cnt = 0;
        while(resultSet.get().next()) cnt++;
        following.setText(String.valueOf(cnt));
    }

    private void jumpToUpdateHomePage() {
        Intent intent = new Intent();
        intent.setClass(HomePageActivity.this, UpdateHomePageActivity.class);
        startActivity(intent);
    }

    private void jumpToSearchUser() {
        Intent intent = new Intent();
        Bundle receive = new Bundle();
        receive.putString("type", "showAll");
        intent.putExtras(receive);
        intent.setClass(HomePageActivity.this, SearchUserActivity.class);
        startActivity(intent);
    }

    private void jumpToShowFollowers() {
        Intent intent = new Intent();
        Bundle receive = new Bundle();
        receive.putString("type", "showFollowers");
        intent.putExtras(receive);
        intent.setClass(this, SearchUserActivity.class);
        startActivity(intent);
    }

    private void jumpToShowFollowings() {
        Intent intent = new Intent();
        Bundle receive = new Bundle();
        receive.putString("type", "showFollowing");
        intent.putExtras(receive);
        intent.setClass(this, SearchUserActivity.class);
        startActivity(intent);
    }

    private void jumpToGroup() {
        Intent intent = new Intent();
        intent.setClass(HomePageActivity.this, GroupActivity.class);
        startActivity(intent);
    }

    private void jumpToTreeHole() {
        Intent intent = new Intent();
        intent.setClass(HomePageActivity.this, TreeHoleActivity.class);
        startActivity(intent);
    }

    private void changeHeadPortrait() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,1206);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == 1206) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                ((ImageView)findViewById(R.id.home_page_head_portrait)).setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                String headPortrait = Base64.encodeToString(bytes,Base64.DEFAULT);
                SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                String account = sp.getString("account", "defaultValue");
                AtomicBoolean flag1 = new AtomicBoolean(false);
                AtomicReference<ResultSet> resultSet = new AtomicReference<>();
                new Thread(() -> {
                    try {
                        Connection connection = MysqlConnector.getConnection();
                        String sql = "select id from user where account=?";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, account);
                        resultSet.set(ps.executeQuery());
                        flag1.set(true);
                    } catch (InterruptedException | SQLException e) {
                        e.printStackTrace();
                    }
                }).start();
                while(!flag1.get());
                resultSet.get().next();
                String userId = resultSet.get().getString("id");
                new Thread(() -> {
                    try {
                        Connection connection = MysqlConnector.getConnection();
                        String sql;
                        if(hasHeadPortrait) {
                            sql = "update user_head_portrait set headPortrait=? where userId=?";
                        } else {
                            sql = "insert user_head_portrait(headPortrait, userId)values(?, ?)";
                        }
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, headPortrait);
                        ps.setString(2, userId);
                        ps.executeUpdate();
                    } catch (InterruptedException | SQLException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void jumpToFeedback() {
        Intent intent = new Intent();
        intent.setClass(this, FeedbackActivity.class);
        startActivity(intent);
    }

    private void jumpToShield() {
        Intent intent = new Intent();
        intent.setClass(this, ShieldActivity.class);
        startActivity(intent);
    }

    private void jumpToChangeTheme() {
        Intent intent = new Intent();
        intent.setClass(this, ChangeThemeActivity.class);
        startActivity(intent);
    }

    private void jumpToHelp() {
        Intent intent = new Intent();
        intent.setClass(this, HelpActivity.class);
        startActivity(intent);
    }

    private void jumpToAchievement() {
        Intent intent = new Intent();
        intent.setClass(this, AchievementActivity.class);
        startActivity(intent);
    }

    private void initClickListener() {
        findViewById(R.id.home_page_update).setOnClickListener(v -> jumpToUpdateHomePage());
        findViewById(R.id.home_page_search_user).setOnClickListener(v -> jumpToSearchUser());
        findViewById(R.id.home_page_followers).setOnClickListener(v -> jumpToShowFollowers());
        findViewById(R.id.home_page_following).setOnClickListener(v -> jumpToShowFollowings());
        findViewById(R.id.home_page_group).setOnClickListener(v -> jumpToGroup());
        findViewById(R.id.home_page_tree_hole).setOnClickListener(v -> jumpToTreeHole());
        findViewById(R.id.home_page_head_portrait).setOnClickListener(v -> changeHeadPortrait());
        findViewById(R.id.home_page_feedback).setOnClickListener(v -> jumpToFeedback());
        findViewById(R.id.home_page_shield).setOnClickListener(v -> jumpToShield());
        findViewById(R.id.home_page_change_theme).setOnClickListener(v -> jumpToChangeTheme());
        findViewById(R.id.home_page_help).setOnClickListener(v -> jumpToHelp());
        findViewById(R.id.home_page_achievement).setOnClickListener(v -> jumpToAchievement());
    }
}
