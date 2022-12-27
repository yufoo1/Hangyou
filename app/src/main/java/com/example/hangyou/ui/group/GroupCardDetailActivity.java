package com.example.hangyou.ui.group;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.utils.DataBaseHelper;
import com.example.hangyou.R;
import com.example.hangyou.ui.home.UpdateHomePageActivity;
import com.example.hangyou.utils.HorizontalListView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupCardDetailActivity extends AppCompatActivity {
    private int groupId;
    SQLiteDatabase database;
    ArrayList<HashMap<String, Object>> data;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_group_detail);
        DataBaseHelper helper = new DataBaseHelper(GroupCardDetailActivity.this);
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists user_group(id integer primary key autoincrement, groupName text, groupType text, groupInitiator text, groupDescription text, groupYear int, groupMonth int, groupDay int, groupMaleExpectedNum int, groupMaleNowNum int, groupFemaleExpectedNum int, groupFemaleNowNum int)");
        database.execSQL("create table if not exists user_group_relation(id integer primary key autoincrement, userId integer, groupId integer)");
        database.execSQL("create table if not exists group_comment(id integer primary key autoincrement, groupId int, userId int, createdAt text, comment text)");
        database.execSQL("create table if not exists group_money(id integer primary key autoincrement, groupId int, money int, createdAt text)");
        database.execSQL("create table if not exists group_money_pay_relation(id integer primary key autoincrement, groupId int, userId int)");
        Bundle receiver = getIntent().getExtras();
        groupId = receiver.getInt("id");
        initView();
        initClickListener();
        showGroupPeople();
        showGroupComment();
    }

    private void initView() {
        Cursor cursor = database.rawQuery("select * from user_group where id=?", new String[]{String.valueOf(groupId)});
        cursor.moveToFirst();
        System.out.println(groupId);
        String name = cursor.getString(cursor.getColumnIndex("groupName"));
        TextView tv_groupName = findViewById(R.id.group_card_detail_groupName);
        tv_groupName.setText(name);
        String type = cursor.getString(cursor.getColumnIndex("groupType"));
        TextView tv_groupType = findViewById(R.id.group_card_detail_groupType);
        MaterialCardView mcv_study = findViewById(R.id.group_detail_study);
        MaterialCardView mcv_sport = findViewById(R.id.group_detail_sport);
        MaterialCardView mcv_eat = findViewById(R.id.group_detail_eat);
        MaterialCardView mcv_travel = findViewById(R.id.group_detail_travel);
        MaterialCardView mcv_buy = findViewById(R.id.group_detail_buy);
        MaterialCardView mcv_movie = findViewById(R.id.group_detail_movie);
        MaterialCardView mcv_game = findViewById(R.id.group_detail_game);
        MaterialCardView mcv_other = findViewById(R.id.group_detail_other);
        System.out.println("start");
        if(type.equals("学习")) {
            mcv_study.setVisibility(View.VISIBLE);
            mcv_sport.setVisibility(View.GONE);
            mcv_eat.setVisibility(View.GONE);
            mcv_travel.setVisibility(View.GONE);
            mcv_buy.setVisibility(View.GONE);
            mcv_movie.setVisibility(View.GONE);
            mcv_game.setVisibility(View.GONE);
            mcv_other.setVisibility(View.GONE);
        } else if(type.equals("运动")) {
            mcv_study.setVisibility(View.GONE);
            mcv_sport.setVisibility(View.VISIBLE);
            mcv_eat.setVisibility(View.GONE);
            mcv_travel.setVisibility(View.GONE);
            mcv_buy.setVisibility(View.GONE);
            mcv_movie.setVisibility(View.GONE);
            mcv_game.setVisibility(View.GONE);
            mcv_other.setVisibility(View.GONE);
        } else if(type.equals("聚餐")) {
            mcv_study.setVisibility(View.GONE);
            mcv_sport.setVisibility(View.GONE);
            mcv_eat.setVisibility(View.VISIBLE);
            mcv_travel.setVisibility(View.GONE);
            mcv_buy.setVisibility(View.GONE);
            mcv_movie.setVisibility(View.GONE);
            mcv_game.setVisibility(View.GONE);
            mcv_other.setVisibility(View.GONE);
        } else if(type.equals("旅行")) {
            mcv_study.setVisibility(View.GONE);
            mcv_sport.setVisibility(View.GONE);
            mcv_eat.setVisibility(View.GONE);
            mcv_travel.setVisibility(View.VISIBLE);
            mcv_buy.setVisibility(View.GONE);
            mcv_movie.setVisibility(View.GONE);
            mcv_game.setVisibility(View.GONE);
            mcv_other.setVisibility(View.GONE);
        } else if(type.equals("拼单")) {
            mcv_study.setVisibility(View.GONE);
            mcv_sport.setVisibility(View.GONE);
            mcv_eat.setVisibility(View.GONE);
            mcv_travel.setVisibility(View.GONE);
            mcv_buy.setVisibility(View.VISIBLE);
            mcv_movie.setVisibility(View.GONE);
            mcv_game.setVisibility(View.GONE);
            mcv_other.setVisibility(View.GONE);
        } else if(type.equals("电影")) {
            mcv_study.setVisibility(View.GONE);
            mcv_sport.setVisibility(View.GONE);
            mcv_eat.setVisibility(View.GONE);
            mcv_travel.setVisibility(View.GONE);
            mcv_buy.setVisibility(View.GONE);
            mcv_movie.setVisibility(View.VISIBLE);
            mcv_game.setVisibility(View.GONE);
            mcv_other.setVisibility(View.GONE);
        } else if(type.equals("游戏")) {
            System.out.println("lala");
            mcv_study.setVisibility(View.GONE);
            mcv_sport.setVisibility(View.GONE);
            mcv_eat.setVisibility(View.GONE);
            mcv_travel.setVisibility(View.GONE);
            mcv_buy.setVisibility(View.GONE);
            mcv_movie.setVisibility(View.GONE);
            mcv_game.setVisibility(View.VISIBLE);
            mcv_other.setVisibility(View.GONE);
        } else {
            mcv_study.setVisibility(View.GONE);
            mcv_sport.setVisibility(View.GONE);
            mcv_eat.setVisibility(View.GONE);
            mcv_travel.setVisibility(View.GONE);
            mcv_buy.setVisibility(View.GONE);
            mcv_movie.setVisibility(View.GONE);
            mcv_game.setVisibility(View.GONE);
            mcv_other.setVisibility(View.VISIBLE);
        }
        String description = cursor.getString(cursor.getColumnIndex("groupDescription"));
        TextView tv_groupDescription = findViewById(R.id.group_card_detail_groupDescription);
        tv_groupDescription.setText(description);
        String initiator = cursor.getString(cursor.getColumnIndex("groupInitiator"));
        TextView tv_groupInitiator = findViewById(R.id.group_card_detail_groupInitiator);
        tv_groupInitiator.setText(initiator);
        String year = cursor.getString(cursor.getColumnIndex("groupYear"));
        String month = cursor.getString(cursor.getColumnIndex("groupMonth"));
        String day = cursor.getString(cursor.getColumnIndex("groupDay"));
        String date = year + "-" + month + "-" + day;
        TextView tv_date = findViewById(R.id.group_card_detail_groupDate);
        tv_date.setText(date);
        String maleNowNum = cursor.getString(cursor.getColumnIndex("groupMaleNowNum"));
        TextView tv_male_now_num = findViewById(R.id.group_card_detail_groupMaleNowNum);
        tv_male_now_num.setText(maleNowNum);
        String maleExpectedNum = cursor.getString(cursor.getColumnIndex("groupMaleExpectedNum"));
        String maleRequiredNum = String.valueOf(Integer.parseInt(maleExpectedNum) - Integer.parseInt(maleNowNum));
        System.out.println("expect male " + maleExpectedNum + " and now male " + maleNowNum);
        TextView tv_male_required_num = findViewById(R.id.group_card_detail_groupMaleRequiredNum);
        tv_male_required_num.setText(maleRequiredNum);
        String femaleNowNum = cursor.getString(cursor.getColumnIndex("groupFemaleNowNum"));
        TextView tv_female_now_num = findViewById(R.id.group_card_detail_groupFemaleNowNum);
        tv_female_now_num.setText(femaleNowNum);
        String femaleExpectedNum = cursor.getString(cursor.getColumnIndex("groupFemaleExpectedNum"));
        String femaleRequiredNum = String.valueOf(Integer.parseInt(femaleExpectedNum) - Integer.parseInt(femaleNowNum));
        TextView tv_female_required_num = findViewById(R.id.group_card_detail_groupFemaleRequiredNum);
        tv_female_required_num.setText(femaleRequiredNum);
        Button bt_add_or_exit = findViewById(R.id.group_card_detail_page_add_or_exit);
        TextView tv_status = findViewById(R.id.group_detail_status);
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        cursor = database.rawQuery("select * from user_group_relation, user where user_group_relation.groupId=? and user.id=user_group_relation.userId and user.account=?", new String[]{String.valueOf(groupId), account});
        if(cursor.moveToFirst()) {
            bt_add_or_exit.setText("退局");
            tv_status.setText("已加入");
        } else {
            bt_add_or_exit.setText("入局");
            tv_status.setText("未加入");
            findViewById(R.id.gather_money_other).setVisibility(View.GONE);
        }
        cursor = database.rawQuery("select * from user where account=?", new String[]{account});
        cursor.moveToFirst();
        String username = cursor.getString(cursor.getColumnIndex("username"));
        cursor = database.rawQuery("select * from user_group where id=? and groupInitiator=?", new String[]{String.valueOf(groupId), username});
        if(cursor.moveToFirst()) {
            /* 局主 */
            cursor = database.rawQuery("select * from group_money where groupId=?", new String[]{String.valueOf(groupId)});
            if(cursor.moveToFirst()) {
                findViewById(R.id.group_card_detail_gather_money_parent).setVisibility(View.GONE);
            } else {
                findViewById(R.id.gather_money_other).setVisibility(View.GONE);
                findViewById(R.id.group_card_detail_gather_money_parent).setVisibility(View.VISIBLE);
            }
            findViewById(R.id.has_group_money_gather).setVisibility(View.GONE);
        } else {
            cursor = database.rawQuery("select * from group_money where groupId=?", new String[]{String.valueOf(groupId)});
            if(cursor.moveToFirst()) {
                TextView tv_money = findViewById(R.id.gather_money_money);
                TextView tv_username = findViewById(R.id.gather_money_username);
                TextView tv_createdAt = findViewById(R.id.gather_money_time);
                cursor = database.rawQuery("select group_money.money as money, user.username as username, group_money.createdAt, user.id as userId from user, group_money, user_group where group_money.groupId=? and group_money.groupId=user_group.id and user_group.groupInitiator=user.username", new String[]{String.valueOf(groupId)});
                cursor.moveToFirst();
                tv_money.setText(cursor.getString(cursor.getColumnIndex("money")));
                tv_username.setText(cursor.getString(cursor.getColumnIndex("username")));
                tv_createdAt.setText(cursor.getString(cursor.getColumnIndex("createdAt")));
                String userId = cursor.getString(cursor.getColumnIndex("userId"));
                cursor = database.rawQuery("select * from group_money_pay_relation where groupId=? and userId=?", new String[]{String.valueOf(groupId), userId});
                if(cursor.moveToFirst()) {
                    /* 已支付 */
                    TextView tv_confirm = findViewById(R.id.gather_money_confirm);
                    tv_confirm.setText("请支付");
                } else {
                    TextView tv_confirm = findViewById(R.id.gather_money_confirm);
                    tv_confirm.setText("您已支付");
                }
                findViewById(R.id.has_group_money_gather).setVisibility(View.GONE);
            } else {
                findViewById(R.id.has_group_money_gather).setVisibility(View.VISIBLE);
            }
            findViewById(R.id.group_card_detail_gather_money_parent).setVisibility(View.GONE);

        }
    }

    private void showGroupPeople() {
        data = new ArrayList<>();
        Cursor cursor = database.rawQuery("select user.id as userId, user.username as username from user, user_group_relation, user_group where user.id=user_group_relation.userId and user_group_relation.groupId=user_group.id and user_group_relation.groupId=?", new String[]{String.valueOf(groupId)});
        ArrayList<Integer> idLists = new ArrayList<>();
        HashMap<String, Object> item;
        while(cursor.moveToNext()) {
            item = new HashMap<>();
            System.out.println(cursor.getString(cursor.getColumnIndex("username")));
            item.put("username", cursor.getString(cursor.getColumnIndex("username")));
            item.put("id", cursor.getString(cursor.getColumnIndex("userId")));
            idLists.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("userId"))));
            data.add(item);
        }
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

    private void showGroupComment() {
        data = new ArrayList<>();
        Cursor cursor = database.rawQuery("select group_comment.id as commentId, user.username as username, group_comment.comment as comment, group_comment.createdAt as createdAt from user, group_comment where user.id=group_comment.userId and group_comment.groupId=?", new String[]{String.valueOf(groupId)});
        ArrayList<Integer> idLists = new ArrayList<>();
        HashMap<String, Object> item;
        int cnt = 0;
        while(cursor.moveToNext()) {
            cnt++;
            item = new HashMap<>();
            item.put("username", cursor.getString(cursor.getColumnIndex("username")));
            item.put("createdAt", cursor.getString(cursor.getColumnIndex("createdAt")));
            item.put("comment", cursor.getString(cursor.getColumnIndex("comment")));
            idLists.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("commentId"))));
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

    private void addOrExitGroup() {
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        Cursor cursor = database.rawQuery("select * from user where account=?", new String[]{account});
        cursor.moveToFirst();
        String userId = cursor.getString(cursor.getColumnIndex("id"));
        String gender = cursor.getString(cursor.getColumnIndex("gender"));
        Button bt_add_or_exit = findViewById(R.id.group_card_detail_page_add_or_exit);
        if(bt_add_or_exit.getText().equals("入局")) {
            database.execSQL("insert into user_group_relation(userId, groupId) values (?, ?)", new Object[]{userId, groupId});
        } else {
            cursor = database.rawQuery("select * from user where account=?", new String[]{account});
            cursor.moveToFirst();
            String username = cursor.getString(cursor.getColumnIndex("username"));
            cursor = database.rawQuery("select * from user_group where id=? and groupInitiator=?", new String[]{String.valueOf(groupId), username});
            if(cursor.moveToFirst()) {
                /* 局主 */
                Toast.makeText(this, "您作为局主不可以退出哦", Toast.LENGTH_SHORT).show();
                return;
            } else {
                database.execSQL("delete from user_group_relation where userId=? and groupId=?", new Object[]{userId, groupId});
            }
        }
        if(gender.equals("男")) {
            cursor = database.rawQuery("select * from user_group, user, user_group_relation where user_group.id=user_group_relation.groupId and user.id=user_group_relation.userId and gender='男' and user_group.id=?", new String[]{String.valueOf(groupId)});
            int cnt = 0;
            while(cursor.moveToNext()) {
                cnt++;
            }
            database.execSQL("update user_group set groupMaleNowNum=? where id=?", new String[]{String.valueOf(cnt), String.valueOf(groupId)});
        } else {
            cursor = database.rawQuery("select * from user_group, user, user_group_relation where user_group.id=user_group_relation.groupId and user.id=user_group_relation.userId and gender='女' and user_group.id=?", new String[]{String.valueOf(groupId)});
            int cnt = 0;
            while(cursor.moveToNext()) {
                cnt++;
            }
            database.execSQL("update user_group set groupFemaleNowNum=? where id=?", new String[]{String.valueOf(cnt), String.valueOf(groupId)});
        }
        Intent intent = new Intent();
        intent.setClass(this, GroupActivity.class);
        startActivity(intent);
    }

    private void jumpToCommentEdit() {
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String account = sp.getString("account", "defaultValue");
        Cursor cursor = database.rawQuery("select * from user where account=?", new String[]{account});
        cursor.moveToFirst();
        String userId = cursor.getString(cursor.getColumnIndex("id"));
        GroupCommentDialog dialog = new GroupCommentDialog(this, androidx.appcompat.R.style.Base_Theme_AppCompat_Light, groupId, Integer.parseInt(userId));
        dialog.show();
    }

    private void sendGatherMoneyRequest() {
        EditText et_money = findViewById(R.id.group_card_detail_gather_money_money);
        String money = et_money.toString();
        if(money.equals("")) {
            Toast.makeText(this, "请输入每个人需要支付的金额", Toast.LENGTH_SHORT).show();
        } else {
            database.execSQL("insert into group_money(groupId, money, createdAt) values(?, ?, datetime('now','localtime'))", new String[]{String.valueOf(groupId), money});
            Toast.makeText(this, "成功发起群收款", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(this, GroupActivity.class);
            startActivity(intent);
        }
    }

    private void initClickListener() {
        findViewById(R.id.group_card_detail_return).setOnClickListener(v -> jumpToGroup());
        findViewById(R.id.group_card_detail_page_add_or_exit).setOnClickListener(v -> addOrExitGroup());
        findViewById(R.id.group_card_detail_page_comment).setOnClickListener(v -> jumpToCommentEdit());
        findViewById(R.id.group_card_detail_gather_money_request).setOnClickListener(v -> sendGatherMoneyRequest());
    }
}
