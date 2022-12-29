package com.example.hangyou.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

public class SearchUserActivity extends AppCompatActivity {
    private ArrayList<HashMap<String, Object>> data = new ArrayList<>();
    ArrayList<String> idLists = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search_user);
        SharedPreferences sp = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int theme = sp.getInt("theme", 0);
        switch (theme) {
            case 0: findViewById(R.id.fragment_search_user).setBackgroundResource(R.color.purple_2); break;
            case 1: findViewById(R.id.fragment_search_user).setBackgroundResource(R.color.blue_2); break;
            case 2: findViewById(R.id.fragment_search_user).setBackgroundResource(R.color.red_2); break;
            case 3: findViewById(R.id.fragment_search_user).setBackgroundResource(R.color.yellow_2); break;
            case 4: findViewById(R.id.fragment_search_user).setBackgroundResource(R.color.blue_6); break;
            case 5: findViewById(R.id.fragment_search_user).setBackgroundResource(R.color.red_4); break;
            case 6: findViewById(R.id.fragment_search_user).setBackgroundResource(R.color.yellow_6); break;
            case 7: findViewById(R.id.fragment_search_user).setBackgroundResource(R.color.gray_2); break;
        }
        initClickListener();
        Bundle receiver = getIntent().getExtras();
        String type = receiver.getString("type");
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        AtomicBoolean flag1 = new AtomicBoolean(false);
        if(type.equals("showAll")) {
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "select * from user ,user_head_portrait where user.id=user_head_portrait.userId";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    resultSet.set(ps.executeQuery());
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
        } else if (type.equals("showFollowers")) {
            new Thread(() -> {
                //                    Connection connection = MysqlConnector.getConnection();
//                    String sql = "select * from user, user_head_portrait inner join follow on user.account=follow.followerAccount and user.account=? and user.id=user_head_portrait.userId";
//                    PreparedStatement ps = connection.prepareStatement(sql);
//                    ps.setString(1, account);
//                    resultSet.set(ps.executeQuery());
                flag1.set(true);
            }).start();
        } else if (type.equals("showFollowing")) {
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "select * from user, user_head_portrait inner join follow on user.account=follow.followingAccount and user.account=? and user.id=user_head_portrait.userId";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, account);
                    resultSet.set(ps.executeQuery());
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "select * from user, user_head_portrait where user.id=user_head_portrait.userId";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    resultSet.set(ps.executeQuery());
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        while(!flag1.get());
        while(true) {
            try {
                if (!resultSet.get().next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            HashMap<String, Object> item = new HashMap<>();
            try {
                item.put("username", resultSet.get().getString("username"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                item.put("description", resultSet.get().getString("description"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                item.put("gender", resultSet.get().getString("gender"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                item.put("phone", resultSet.get().getString("phone"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                item.put("headPortrait", resultSet.get().getString("headPortrait"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                idLists.add(resultSet.get().getString("id"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            data.add(item);
        }
        System.out.println(data.size());
        UserCardAdapter adapter = new UserCardAdapter(SearchUserActivity.this, data);
        ListView userCards = findViewById(R.id.search_user_user_list);
        userCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        userCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                String followerAccount = sp.getString("account", "defaultValue");
                String followingId = idLists.get(position);
                AtomicReference<ResultSet> resultSet = new AtomicReference<>();
                AtomicBoolean flag1 = new AtomicBoolean(false);
                new Thread(() -> {
                    try {
                        Connection connection = MysqlConnector.getConnection();
                        String sql = "select * from user, follow where user.account=? and follow.followingId=?";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, account);
                        ps.setString(2, followingId);
                        resultSet.set(ps.executeQuery());
                        flag1.set(true);
                    } catch (InterruptedException | SQLException e) {
                        e.printStackTrace();
                    }
                }).start();
                while(!flag1.get());
                try {
                    if(resultSet.get().next()) {
                    } else {
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
                    while(!flag1.get());
                    try {
                        resultSet.get().next();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    String followerId = null;
                    try {
                        followerId = resultSet.get().getString("id");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    String finalFollowerId = followerId;
                    new Thread(() -> {
                        try {
                            Connection connection = MysqlConnector.getConnection();
                            String sql = "insert into follow(followerId, followingId) values (?, ?)";
                            PreparedStatement ps = connection.prepareStatement(sql);
                            ps.setString(1, finalFollowerId);
                            ps.setString(2, followingId);
                            ps.executeUpdate();
                            flag1.set(true);
                        } catch (InterruptedException | SQLException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void search() {
        EditText et_key_word = findViewById(R.id.search_user_key_word);
        String keyWord = et_key_word.getText().toString();
        data = new ArrayList<>();
        Bundle receiver = getIntent().getExtras();
        String type = receiver.getString("type");
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        AtomicBoolean flag1 = new AtomicBoolean(false);
        if(type.equals("showAll")) {
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "select * from user ,user_head_portrait where user.id=user_head_portrait.userId and user.username like '%" + keyWord + "%'";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    resultSet.set(ps.executeQuery());
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
        } else if (type.equals("showFollowers")) {
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "select * from user, user_head_portrait inner join follow on user.account=follow.followerAccount and user.account=? and user.id=user_head_portrait.userId and user.username like '%?%";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, account);
                    ps.setString(2, keyWord);
                    resultSet.set(ps.executeQuery());
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
        } else if (type.equals("showFollowing")) {
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "select * from user, user_head_portrait inner join follow on user.account=follow.followingAccount and user.account=? and user.id=user_head_portrait.userId and user.username like '%?%";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, account);
                    ps.setString(2, keyWord);
                    resultSet.set(ps.executeQuery());
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "select * from user, user_head_portrait where user.id=user_head_portrait.userId and user.username like '%?%";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    resultSet.set(ps.executeQuery());
                    ps.setString(1, keyWord);
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        while(!flag1.get());
        while(true) {
            try {
                if (!resultSet.get().next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            HashMap<String, Object> item = new HashMap<>();
            try {
                item.put("username", resultSet.get().getString("username"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                item.put("description", resultSet.get().getString("description"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                item.put("gender", resultSet.get().getString("gender"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                item.put("phone", resultSet.get().getString("phone"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                item.put("headPortrait", resultSet.get().getString("headPortrait"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                idLists.add(resultSet.get().getString("id"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            data.add(item);
            UserCardAdapter adapter = new UserCardAdapter(SearchUserActivity.this, data);
            ListView userCards = findViewById(R.id.search_user_user_list);
            userCards.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    private void initClickListener() {
        findViewById(R.id.user_search_key_word_button).setOnClickListener(v -> search());
    }
}
