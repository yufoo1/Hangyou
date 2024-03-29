package com.example.hangyou.ui.group;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.R;
import com.example.hangyou.ui.home.UpdateHomePageActivity;
import com.example.hangyou.utils.HorizontalListView;
import com.example.hangyou.utils.MysqlConnector;
import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class GroupCardDetailActivity extends AppCompatActivity {
    private int groupId;
    ArrayList<HashMap<String, Object>> data;
    ArrayList<HashMap<String, Object>> payData;
    ArrayList<HashMap<String, Object>> noPayData;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_group_detail);
        Bundle receiver = getIntent().getExtras();
        SharedPreferences sp = getSharedPreferences("theme", Context.MODE_PRIVATE);
        int theme = sp.getInt("theme", 0);
        switch (theme) {
            case 0: findViewById(R.id.fragment_group_detail).setBackgroundResource(R.color.purple_2); break;
            case 1: findViewById(R.id.fragment_group_detail).setBackgroundResource(R.color.blue_2); break;
            case 2: findViewById(R.id.fragment_group_detail).setBackgroundResource(R.color.red_2); break;
            case 3: findViewById(R.id.fragment_group_detail).setBackgroundResource(R.color.yellow_2); break;
            case 4: findViewById(R.id.fragment_group_detail).setBackgroundResource(R.color.blue_6); break;
            case 5: findViewById(R.id.fragment_group_detail).setBackgroundResource(R.color.red_4); break;
            case 6: findViewById(R.id.fragment_group_detail).setBackgroundResource(R.color.yellow_6); break;
            case 7: findViewById(R.id.fragment_group_detail).setBackgroundResource(R.color.gray_2); break;
        }
        groupId = receiver.getInt("id");
        initClickListener();
        try {
            showGroupPeople();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            initView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            showGroupComment();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initView() throws SQLException {
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        AtomicBoolean flag1 = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from user_group where id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(groupId));
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag1.get());
        resultSet.get().next();
        System.out.println(groupId);
        String name = resultSet.get().getString("groupName");
        TextView tv_groupName = findViewById(R.id.group_card_detail_groupName);
        tv_groupName.setText(name);
        String type = resultSet.get().getString("groupType");
        TextView tv_groupType = findViewById(R.id.group_card_detail_groupType);
        MaterialCardView mcv_study = findViewById(R.id.group_detail_study);
        MaterialCardView mcv_sport = findViewById(R.id.group_detail_sport);
        MaterialCardView mcv_eat = findViewById(R.id.group_detail_eat);
        MaterialCardView mcv_travel = findViewById(R.id.group_detail_travel);
        MaterialCardView mcv_buy = findViewById(R.id.group_detail_buy);
        MaterialCardView mcv_movie = findViewById(R.id.group_detail_movie);
        MaterialCardView mcv_game = findViewById(R.id.group_detail_game);
        MaterialCardView mcv_other = findViewById(R.id.group_detail_other);
        switch (type) {
            case "学习":
                mcv_study.setVisibility(View.VISIBLE);
                mcv_sport.setVisibility(View.GONE);
                mcv_eat.setVisibility(View.GONE);
                mcv_travel.setVisibility(View.GONE);
                mcv_buy.setVisibility(View.GONE);
                mcv_movie.setVisibility(View.GONE);
                mcv_game.setVisibility(View.GONE);
                mcv_other.setVisibility(View.GONE);
                break;
            case "运动":
                mcv_study.setVisibility(View.GONE);
                mcv_sport.setVisibility(View.VISIBLE);
                mcv_eat.setVisibility(View.GONE);
                mcv_travel.setVisibility(View.GONE);
                mcv_buy.setVisibility(View.GONE);
                mcv_movie.setVisibility(View.GONE);
                mcv_game.setVisibility(View.GONE);
                mcv_other.setVisibility(View.GONE);
                break;
            case "聚餐":
                mcv_study.setVisibility(View.GONE);
                mcv_sport.setVisibility(View.GONE);
                mcv_eat.setVisibility(View.VISIBLE);
                mcv_travel.setVisibility(View.GONE);
                mcv_buy.setVisibility(View.GONE);
                mcv_movie.setVisibility(View.GONE);
                mcv_game.setVisibility(View.GONE);
                mcv_other.setVisibility(View.GONE);
                break;
            case "旅行":
                mcv_study.setVisibility(View.GONE);
                mcv_sport.setVisibility(View.GONE);
                mcv_eat.setVisibility(View.GONE);
                mcv_travel.setVisibility(View.VISIBLE);
                mcv_buy.setVisibility(View.GONE);
                mcv_movie.setVisibility(View.GONE);
                mcv_game.setVisibility(View.GONE);
                mcv_other.setVisibility(View.GONE);
                break;
            case "拼单":
                mcv_study.setVisibility(View.GONE);
                mcv_sport.setVisibility(View.GONE);
                mcv_eat.setVisibility(View.GONE);
                mcv_travel.setVisibility(View.GONE);
                mcv_buy.setVisibility(View.VISIBLE);
                mcv_movie.setVisibility(View.GONE);
                mcv_game.setVisibility(View.GONE);
                mcv_other.setVisibility(View.GONE);
                break;
            case "电影":
                mcv_study.setVisibility(View.GONE);
                mcv_sport.setVisibility(View.GONE);
                mcv_eat.setVisibility(View.GONE);
                mcv_travel.setVisibility(View.GONE);
                mcv_buy.setVisibility(View.GONE);
                mcv_movie.setVisibility(View.VISIBLE);
                mcv_game.setVisibility(View.GONE);
                mcv_other.setVisibility(View.GONE);
                break;
            case "游戏":
                System.out.println("lala");
                mcv_study.setVisibility(View.GONE);
                mcv_sport.setVisibility(View.GONE);
                mcv_eat.setVisibility(View.GONE);
                mcv_travel.setVisibility(View.GONE);
                mcv_buy.setVisibility(View.GONE);
                mcv_movie.setVisibility(View.GONE);
                mcv_game.setVisibility(View.VISIBLE);
                mcv_other.setVisibility(View.GONE);
                break;
            default:
                mcv_study.setVisibility(View.GONE);
                mcv_sport.setVisibility(View.GONE);
                mcv_eat.setVisibility(View.GONE);
                mcv_travel.setVisibility(View.GONE);
                mcv_buy.setVisibility(View.GONE);
                mcv_movie.setVisibility(View.GONE);
                mcv_game.setVisibility(View.GONE);
                mcv_other.setVisibility(View.VISIBLE);
                break;
        }
        String description = resultSet.get().getString("description");
        TextView tv_groupDescription = findViewById(R.id.group_card_detail_groupDescription);
        tv_groupDescription.setText(description);
        String initiator = resultSet.get().getString("groupInitiator");
        TextView tv_groupInitiator = findViewById(R.id.group_card_detail_groupInitiator);
        tv_groupInitiator.setText(initiator);
        String year = resultSet.get().getString("year");
        String month = resultSet.get().getString("month");
        String day = resultSet.get().getString("day");
        String date = year + "-" + month + "-" + day;
        TextView tv_date = findViewById(R.id.group_card_detail_groupDate);
        tv_date.setText(date);
        String maleNowNum = resultSet.get().getString("maleNow");
        TextView tv_male_now_num = findViewById(R.id.group_card_detail_groupMaleNowNum);
        tv_male_now_num.setText(maleNowNum);
        String maleExpectedNum = resultSet.get().getString("maleExpect");
        String maleRequiredNum = String.valueOf(Integer.parseInt(maleExpectedNum) - Integer.parseInt(maleNowNum));
        System.out.println("expect male " + maleExpectedNum + " and now male " + maleNowNum);
        TextView tv_male_required_num = findViewById(R.id.group_card_detail_groupMaleRequiredNum);
        tv_male_required_num.setText(maleRequiredNum);
        String femaleNowNum = resultSet.get().getString("femaleNow");
        TextView tv_female_now_num = findViewById(R.id.group_card_detail_groupFemaleNowNum);
        tv_female_now_num.setText(femaleNowNum);
        String femaleExpectedNum = resultSet.get().getString("femaleExpect");
        String femaleRequiredNum = String.valueOf(Integer.parseInt(femaleExpectedNum) - Integer.parseInt(femaleNowNum));
        TextView tv_female_required_num = findViewById(R.id.group_card_detail_groupFemaleRequiredNum);
        tv_female_required_num.setText(femaleRequiredNum);
        Button bt_add_or_exit = findViewById(R.id.group_card_detail_page_add_or_exit);
        TextView tv_status = findViewById(R.id.group_detail_status);
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        flag1.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from user_group_relation, user where user_group_relation.groupId=? and user.id=user_group_relation.userId and user.account=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(groupId));
                ps.setString(2, account);
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag1.get());
        if(resultSet.get().next()) {
            bt_add_or_exit.setText("退局");
            tv_status.setText("已加入");
        } else {
            bt_add_or_exit.setText("入局");
            tv_status.setText("未加入");
            findViewById(R.id.gather_money_other).setVisibility(View.GONE);
        }
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
        String username = resultSet.get().getString("username");
        flag1.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from user_group where id=? and groupInitiator=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(groupId));
                ps.setString(2, username);
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag1.get());
        if(resultSet.get().next()) {
            /* 局主 */
            flag1.set(false);
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "select * from group_money where groupId=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(groupId));
                    resultSet.set(ps.executeQuery());
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
            while (!flag1.get());
            if(resultSet.get().next()) {
                findViewById(R.id.group_card_detail_gather_money_parent).setVisibility(View.GONE);
                String money = resultSet.get().getString("money");
                String time = resultSet.get().getString("createdAt");
                ((TextView)findViewById(R.id.collect_money_time)).setText(time);
                ((TextView)findViewById(R.id.collect_money_time)).setText(time);
                ((TextView)findViewById(R.id.collect_money_username)).setText(username);
                flag1.set(false);
                new Thread(() -> {
                    try {
                        Connection connection = MysqlConnector.getConnection();
                        String sql = "select * from user_group_relation where groupId=?";
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
                MaterialCardView mcv_gather_money_init = findViewById(R.id.gather_money_init);
                mcv_gather_money_init.setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.need_collect_money)).setText(String.valueOf(Integer.parseInt(money) * (cnt - 1)));
                String headPortrait = sp.getString("head_portrait", "");
                if(!Objects.equals(headPortrait, "")) {
                    byte[] bytes= Base64.decode(headPortrait, Base64.DEFAULT);
                    Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    ((ImageView)findViewById(R.id.collect_money_head)).setImageBitmap(bitmap);
                }
                flag1.set(false);
                new Thread(() -> {
                    try {
                        Connection connection = MysqlConnector.getConnection();
                        String sql = "select * from group_money_pay_relation where groupId=?";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, String.valueOf(groupId));
                        resultSet.set(ps.executeQuery());
                        flag1.set(true);
                    } catch (InterruptedException | SQLException e) {
                        e.printStackTrace();
                    }
                }).start();
                while (!flag1.get());
                cnt = 0;
                while(resultSet.get().next()) {
                    cnt++;
                }
                ((TextView)findViewById(R.id.need_pay_money)).setText(String.valueOf(Integer.parseInt(money) * cnt));
            } else {
                MaterialCardView mcv_gather_money_init = findViewById(R.id.gather_money_init);
                mcv_gather_money_init.setVisibility(View.GONE);
                findViewById(R.id.gather_money_other).setVisibility(View.GONE);
                findViewById(R.id.group_card_detail_gather_money_parent).setVisibility(View.VISIBLE);
            }
            findViewById(R.id.has_group_money_gather).setVisibility(View.GONE);
            findViewById(R.id.gather_money_other).setVisibility(View.GONE);
        } else {
            MaterialCardView mcv_gather_money_init = findViewById(R.id.gather_money_init);
            mcv_gather_money_init.setVisibility(View.GONE);
            flag1.set(false);
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "select * from group_money where groupId=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(groupId));
                    resultSet.set(ps.executeQuery());
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
            while (!flag1.get());
            if(resultSet.get().next()) {
                TextView tv_money = findViewById(R.id.gather_money_money);
                TextView tv_username = findViewById(R.id.gather_money_username);
                TextView tv_createdAt = findViewById(R.id.gather_money_time);
                flag1.set(false);
                new Thread(() -> {
                    try {
                        Connection connection = MysqlConnector.getConnection();
                        String sql = "select group_money.money, user.username, group_money.createdAt, user.id from user, group_money, user_group where group_money.groupId=? and group_money.groupId=user_group.id and user_group.groupInitiator=user.username";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, String.valueOf(groupId));
                        resultSet.set(ps.executeQuery());
                        flag1.set(true);
                    } catch (InterruptedException | SQLException e) {
                        e.printStackTrace();
                    }
                }).start();
                while (!flag1.get());
                resultSet.get().next();
                tv_money.setText(resultSet.get().getString("money"));
                tv_username.setText(resultSet.get().getString("username"));
                tv_createdAt.setText(resultSet.get().getString("createdAt"));
                String userId = resultSet.get().getString("id");
                flag1.set(false);
                new Thread(() -> {
                    try {
                        Connection connection = MysqlConnector.getConnection();
                        String sql = "select * from group_money_pay_relation, user where group_money_pay_relation.groupId=? and user.id=group_money_pay_relation.userId and user.account=?";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, String.valueOf(groupId));
                        ps.setString(2, account);
                        resultSet.set(ps.executeQuery());
                        flag1.set(true);
                    } catch (InterruptedException | SQLException e) {
                        e.printStackTrace();
                    }
                }).start();
                while (!flag1.get());
                if(resultSet.get().next()) {
                    /* 已支付 */
                    TextView tv_confirm = findViewById(R.id.gather_money_confirm);
                    tv_confirm.setText("您已支付");
                } else {
                    TextView tv_confirm = findViewById(R.id.gather_money_confirm);
                    tv_confirm.setText("请支付");
                }
                findViewById(R.id.has_group_money_gather).setVisibility(View.GONE);
            } else {
                findViewById(R.id.has_group_money_gather).setVisibility(View.VISIBLE);
            }
            findViewById(R.id.group_card_detail_gather_money_parent).setVisibility(View.GONE);

        }
    }

    private void showGroupPeople() throws SQLException {
        data = new ArrayList<>();
        payData = new ArrayList<>();
        noPayData = new ArrayList<>();
        AtomicBoolean flag1 = new AtomicBoolean(false);
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        flag1.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select user.id, user.username, user_head_portrait.headPortrait from user, user_group_relation, user_group, group_money_pay_relation, user_head_portrait where user.id=user_group_relation.userId and user_group_relation.groupId=user_group.id and user_group_relation.groupId=? and group_money_pay_relation.userId=user.id and user.id=user_head_portrait.userId";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(groupId));
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag1.get());
        ArrayList<Integer> idLists = new ArrayList<>();
        HashMap<String, Object> item;
        while(resultSet.get().next()) {
            item = new HashMap<>();
            item.put("username", resultSet.get().getString("username"));
            item.put("id", resultSet.get().getString("id"));
            item.put("head_portrait", resultSet.get().getString("headPortrait"));
            idLists.add(Integer.parseInt(resultSet.get().getString("id")));
            data.add(item);
            payData.add(item);
        }
        data = new ArrayList<>();
        payData = new ArrayList<>();
        noPayData = new ArrayList<>();
        flag1.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                System.out.println("groupId = " + groupId);
                String sql = "select user.id, user.username, user_head_portrait.headPortrait from user, user_group_relation, user_group, user_head_portrait where user.id=user_group_relation.userId and user_group_relation.groupId=user_group.id and user_group_relation.groupId=? and user.id=user_head_portrait.userId and not exists(select * from group_money_pay_relation, user where user.id=group_money_pay_relation.userId and group_money_pay_relation.groupId=?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(groupId));
                ps.setString(2, String.valueOf(groupId));
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag1.get());
        while(resultSet.get().next()) {
            item = new HashMap<>();
            item.put("username", resultSet.get().getString("username"));
            item.put("id", resultSet.get().getString("id"));
            item.put("head_portrait", resultSet.get().getString("headPortrait"));
            idLists.add(Integer.parseInt(resultSet.get().getString("id")));
            data.add(item);
            noPayData.add(item);
        }
        System.out.println("data has " + data.size());
        GroupPeopleCardAdapter adapter = new GroupPeopleCardAdapter(GroupCardDetailActivity.this, data);
        HorizontalListView groupPeople = findViewById(R.id.group_people_list);
        groupPeople.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        groupPeople.setOnItemClickListener((parent, view, position, id) -> {
            Bundle bundle=new Bundle();
            bundle.putInt("id", idLists.get(position));
            Intent intent =new Intent(this, UpdateHomePageActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        });
    }

    private void showGroupComment() throws SQLException {
        data = new ArrayList<>();
        AtomicBoolean flag1 = new AtomicBoolean(false);
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        flag1.set(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select group_comment.id, user.username, group_comment.comment, group_comment.createdAt from user, group_comment where user.id=group_comment.userId and group_comment.groupId=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, String.valueOf(groupId));
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag1.get());
        ArrayList<Integer> idLists = new ArrayList<>();
        HashMap<String, Object> item;
        int cnt = 0;
        while(resultSet.get().next()) {
            cnt++;
            item = new HashMap<>();
            item.put("username", resultSet.get().getString("username"));
            item.put("createdAt", resultSet.get().getString("createdAt"));
            item.put("comment", resultSet.get().getString("comment"));
            idLists.add(Integer.parseInt(resultSet.get().getString("id")));
            data.add(item);
        }
        if(cnt == 0) {
            findViewById(R.id.group_has_comment).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.group_has_comment).setVisibility(View.GONE);
        }
        GroupCommentCardAdapter adapter = new GroupCommentCardAdapter(this, data);
        ListView groupComment = findViewById(R.id.comment_list);
        groupComment.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        groupComment.setOnItemClickListener((parent, view, position, id) -> {
            Bundle bundle=new Bundle();
            bundle.putInt("id", idLists.get(position));
            Intent intent =new Intent(this, UpdateHomePageActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        });
    }

    private void jumpToGroup() {
        Intent intent = new Intent();
        intent.setClass(this, GroupActivity.class);
        startActivity(intent);
    }

    private void addOrExitGroup() throws SQLException {
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
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
        String userId = resultSet.get().getString("id");
        String gender = resultSet.get().getString("gender");
        Button bt_add_or_exit = findViewById(R.id.group_card_detail_page_add_or_exit);
        if(bt_add_or_exit.getText().equals("入局")) {
            flag1.set(false);
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "insert into user_group_relation(userId, groupId) values (?, ?)";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, userId);
                    ps.setString(2, String.valueOf(groupId));
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
            String username = resultSet.get().getString("username");
            flag1.set(false);
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "select * from user_group where id=? and groupInitiator=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(groupId));
                    ps.setString(2, username);
                    resultSet.set(ps.executeQuery());
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
            while (!flag1.get());
            if(resultSet.get().next()) {
                /* 局主 */
                Toast.makeText(this, "您作为局主不可以退出哦", Toast.LENGTH_SHORT).show();
                return;
            } else {
                flag1.set(false);
                new Thread(() -> {
                    try {
                        Connection connection = MysqlConnector.getConnection();
                        String sql = "delete from user_group_relation where userId=? and groupId=?";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, String.valueOf(userId));
                        ps.setString(2, String.valueOf(groupId));
                        resultSet.set(ps.executeQuery());
                        flag1.set(true);
                    } catch (InterruptedException | SQLException e) {
                        e.printStackTrace();
                    }
                }).start();
                while (!flag1.get());
            }
        }
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
            int finalCnt = cnt;
            flag1.set(false);
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "update user_group set maleNow=? where id=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(finalCnt));
                    ps.setString(2, String.valueOf(groupId));
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
            int finalCnt = cnt;
            flag1.set(false);
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "update user_group set femaleNow=? where id=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(finalCnt));
                    ps.setString(2, String.valueOf(groupId));
                    ps.executeUpdate();
                    flag1.set(true);
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
            while (!flag1.get());
        }
        Intent intent = new Intent();
        intent.setClass(this, GroupActivity.class);
        startActivity(intent);
    }

    private void jumpToCommentEdit() throws SQLException {
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        AtomicBoolean flag1 = new AtomicBoolean(false);
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
        while(!flag1.get());
        resultSet.get().next();
        String userId = resultSet.get().getString("id");
        GroupCommentDialog dialog = new GroupCommentDialog(this, androidx.appcompat.R.style.Base_Theme_AppCompat_Light, groupId, Integer.parseInt(userId));
        dialog.show();
    }

    private void sendGatherMoneyRequest() {
        System.out.println("发起局收款");
        EditText et_money = findViewById(R.id.group_card_detail_gather_money_money);
        String money = et_money.getText().toString();
        if(money.equals("")) {
            Toast.makeText(this, "请输入每个人需要支付的金额", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "insert into group_money(groupId, money, createdAt) values(?, ?, ?)";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(groupId));
                    ps.setString(2, money);
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int date = c.get(Calendar.DATE);
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int minute = c.get(Calendar.MINUTE);
                    int second = c.get(Calendar.SECOND);
                    ps.setString(3, year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second);
                    ps.executeUpdate();
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
            Toast.makeText(this, "成功发起群收款", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(this, GroupActivity.class);
            startActivity(intent);
        }
    }

    private void confirmPay() throws SQLException {
        if(((TextView)findViewById(R.id.gather_money_confirm)).getText().toString().equals("您已支付")) {
            Toast.makeText(this, "您已经支付过啦", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
            String account = sp.getString("account", "defaultValue");
            AtomicBoolean flag1 = new AtomicBoolean(false);
            AtomicReference<ResultSet>resultSet = new AtomicReference<>();
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
            String id = resultSet.get().getString("id");
            new Thread(() -> {
                try {
                    Connection connection = MysqlConnector.getConnection();
                    String sql = "insert into group_money_pay_relation(groupId, userId) values (?, ?)";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, String.valueOf(groupId));
                    ps.setString(2, id);
                    ps.executeUpdate();
                } catch (InterruptedException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();
            Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(this, GroupActivity.class);
            startActivity(intent);
        }
    }

    private void initClickListener() {
        findViewById(R.id.group_card_detail_return).setOnClickListener(v -> jumpToGroup());
        findViewById(R.id.group_card_detail_page_add_or_exit).setOnClickListener(v -> {
            try {
                addOrExitGroup();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        findViewById(R.id.group_card_detail_page_comment).setOnClickListener(v -> {
            try {
                jumpToCommentEdit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        findViewById(R.id.group_card_detail_gather_money_request).setOnClickListener(v -> sendGatherMoneyRequest());
        findViewById(R.id.gather_money_confirm).setOnClickListener(v -> {
            try {
                confirmPay();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
