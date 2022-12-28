package com.example.hangyou.ui.group;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.R;
import com.example.hangyou.utils.MysqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class AddGroupPageActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_group_page);
        initClickListener();
    }

    private void initClickListener() {
        RadioButton rb_type0 = findViewById(R.id.add_group_page_type_0);
        RadioButton rb_type1 = findViewById(R.id.add_group_page_type_1);
        RadioButton rb_type2 = findViewById(R.id.add_group_page_type_2);
        RadioButton rb_type3 = findViewById(R.id.add_group_page_type_3);
        RadioButton rb_type4 = findViewById(R.id.add_group_page_type_4);
        RadioButton rb_type5 = findViewById(R.id.add_group_page_type_5);
        RadioButton rb_type6 = findViewById(R.id.add_group_page_type_6);
        RadioButton rb_type7 = findViewById(R.id.add_group_page_type_7);
        findViewById(R.id.add_group_page_button_created).setOnClickListener(v -> {
            try {
                commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        findViewById(R.id.add_group_page_button_return).setOnClickListener(v -> returnToGroup());
        findViewById(R.id.add_group_page_type_0).setOnClickListener(v -> {
            rb_type1.setChecked(false);
            rb_type2.setChecked(false);
            rb_type3.setChecked(false);
            rb_type4.setChecked(false);
            rb_type5.setChecked(false);
            rb_type6.setChecked(false);
            rb_type7.setChecked(false);
        });
        findViewById(R.id.add_group_page_type_1).setOnClickListener(v -> {
            rb_type0.setChecked(false);
            rb_type2.setChecked(false);
            rb_type3.setChecked(false);
            rb_type4.setChecked(false);
            rb_type5.setChecked(false);
            rb_type6.setChecked(false);
            rb_type7.setChecked(false);
        });
        findViewById(R.id.add_group_page_type_2).setOnClickListener(v -> {
            rb_type1.setChecked(false);
            rb_type0.setChecked(false);
            rb_type3.setChecked(false);
            rb_type4.setChecked(false);
            rb_type5.setChecked(false);
            rb_type6.setChecked(false);
            rb_type7.setChecked(false);
        });
        findViewById(R.id.add_group_page_type_3).setOnClickListener(v -> {
            rb_type1.setChecked(false);
            rb_type2.setChecked(false);
            rb_type0.setChecked(false);
            rb_type4.setChecked(false);
            rb_type5.setChecked(false);
            rb_type6.setChecked(false);
            rb_type7.setChecked(false);
        });
        findViewById(R.id.add_group_page_type_4).setOnClickListener(v -> {
            rb_type1.setChecked(false);
            rb_type2.setChecked(false);
            rb_type3.setChecked(false);
            rb_type0.setChecked(false);
            rb_type5.setChecked(false);
            rb_type6.setChecked(false);
            rb_type7.setChecked(false);
        });
        findViewById(R.id.add_group_page_type_5).setOnClickListener(v -> {
            rb_type1.setChecked(false);
            rb_type2.setChecked(false);
            rb_type3.setChecked(false);
            rb_type4.setChecked(false);
            rb_type0.setChecked(false);
            rb_type6.setChecked(false);
            rb_type7.setChecked(false);
        });
        findViewById(R.id.add_group_page_type_6).setOnClickListener(v -> {
            rb_type1.setChecked(false);
            rb_type2.setChecked(false);
            rb_type3.setChecked(false);
            rb_type4.setChecked(false);
            rb_type5.setChecked(false);
            rb_type0.setChecked(false);
            rb_type7.setChecked(false);
        });
        findViewById(R.id.add_group_page_type_7).setOnClickListener(v -> {
            rb_type1.setChecked(false);
            rb_type2.setChecked(false);
            rb_type3.setChecked(false);
            rb_type4.setChecked(false);
            rb_type5.setChecked(false);
            rb_type6.setChecked(false);
            rb_type0.setChecked(false);
        });
    }

    private void returnToGroup() {
        Intent intent = new Intent();
        intent.setClass(AddGroupPageActivity.this, GroupActivity.class);
        startActivity(intent);
    }

    private void commit() throws SQLException {
        EditText et_name = findViewById(R.id.add_group_page_name_input);
        String groupName = et_name.getText().toString();
        RadioButton rb_type0 = findViewById(R.id.add_group_page_type_0);
        RadioButton rb_type1 = findViewById(R.id.add_group_page_type_1);
        RadioButton rb_type2 = findViewById(R.id.add_group_page_type_2);
        RadioButton rb_type3 = findViewById(R.id.add_group_page_type_3);
        RadioButton rb_type4 = findViewById(R.id.add_group_page_type_4);
        RadioButton rb_type5 = findViewById(R.id.add_group_page_type_5);
        RadioButton rb_type6 = findViewById(R.id.add_group_page_type_6);
        RadioButton rb_type7 = findViewById(R.id.add_group_page_type_7);
        ArrayList<String> types = new ArrayList<>(Arrays.asList("学习", "运动", "聚餐", "旅行", "拼单", "电影", "游戏", "其他"));
        String groupType;
        if (rb_type0.isChecked()) {
            groupType = types.get(0);
        } else if (rb_type1.isChecked()) {
            groupType = types.get(1);
        } else if (rb_type2.isChecked()) {
            groupType = types.get(2);
        } else if (rb_type3.isChecked()) {
            groupType = types.get(3);
        } else if (rb_type4.isChecked()) {
            groupType = types.get(4);
        } else if (rb_type5.isChecked()) {
            groupType = types.get(5);
        } else if (rb_type6.isChecked()) {
            groupType = types.get(6);
        } else {
            groupType = types.get(7);
        }
        EditText et_description = findViewById(R.id.add_group_page_content_input);
        String groupDescription = et_description.getText().toString();
        DatePicker datepick = findViewById(R.id.add_group_page_date_picker);
        int groupYear = datepick.getYear();
        int groupMonth = datepick.getMonth() + 1;
        int groupDay = datepick.getDayOfMonth();
        EditText et_maleExpectedNum = findViewById(R.id.add_group_page_male_expected_num);
        int groupMaleExpectedNum = Integer.parseInt(et_maleExpectedNum.getText().toString());
        EditText et_femaleExpectedNum = findViewById(R.id.add_group_page_female_expected_num);
        int groupFemaleExpectedNum = Integer.parseInt(et_femaleExpectedNum.getText().toString());
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        System.out.println(account);
        AtomicBoolean flag1 = new AtomicBoolean(false);
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
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
        resultSet.get().next();
        String groupInitiator = resultSet.get().getString("username");
        String id = resultSet.get().getString("id");
        String gender = resultSet.get().getString("gender");
        flag1.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "insert into user_group(groupName, groupType, groupInitiator, description, year, month, day, maleExpect, maleNow, femaleExpect, femaleNow) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, groupName);
                ps.setString(2, groupType);
                ps.setString(3, groupInitiator);
                ps.setString(4, groupDescription);
                ps.setString(5, String.valueOf(groupYear));
                ps.setString(6, String.valueOf(groupMonth));
                ps.setString(7, String.valueOf(groupDay));
                ps.setString(8, String.valueOf(groupMaleExpectedNum));
                ps.setString(9, String.valueOf(0));
                ps.setString(10, String.valueOf(groupFemaleExpectedNum));
                ps.setString(11, String.valueOf(0));
                ps.executeUpdate();
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag1.get());
        flag1.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select id from user_group order by id desc";
                PreparedStatement ps = connection.prepareStatement(sql);
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag1.get())
        resultSet.get().next();
        String groupId = resultSet.get().getString("id");
        flag1.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "insert into user_group_relation(groupId, userId) values(?, ?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(groupId));
                ps.setString(2, String.valueOf(id));
                ps.executeUpdate();
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        resultSet.get().next();
        if(gender.equals("男")) {
            flag1.set(false);
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "select * from user_group, user, user_group_relation where user_group.id=user_group_relation.groupId and user.id=user_group_relation.userId and gender='男' and user_group.id=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(groupId));
                    resultSet.set(ps.executeQuery());
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
            while (!flag1.get());
            int cnt = 0;
            while(resultSet.get().next()) {
                cnt++;
            }
            flag1.set(false);
            int finalCnt = cnt;
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "update user_group set maleNow=? where id=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(finalCnt));
                    ps.setString(2, String.valueOf(String.valueOf(groupId)));
                    ps.executeUpdate();
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
            while (!flag1.get());
        } else {
            flag1.set(false);
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "select * from user_group, user, user_group_relation where user_group.id=user_group_relation.groupId and user.id=user_group_relation.userId and gender='女' and user_group.id=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(groupId));
                    resultSet.set(ps.executeQuery());
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
            while (!flag1.get());
            int cnt = 0;
            while(resultSet.get().next()) {
                cnt++;
            }
            flag1.set(false);
            int finalCnt = cnt;
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "update user_group set groupFemaleNowNum=? where id=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(finalCnt));
                    ps.setString(2, String.valueOf(groupId));
                    ps.executeUpdate();
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println("新组添加成功");
        Intent intent = new Intent();
        intent.setClass(AddGroupPageActivity.this, GroupActivity.class);
        startActivity(intent);
    }
}
