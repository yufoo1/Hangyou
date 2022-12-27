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
import android.widget.ListView;
import android.widget.TextView;

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
        Bundle receiver = getIntent().getExtras();
        groupId = receiver.getInt("id");
        initView();
        initClickListener();
        showGroupPeople();
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
            database.execSQL("delete from user_group_relation where userId=? and groupId=?", new Object[]{userId, groupId});
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

    private void initClickListener() {
        findViewById(R.id.group_card_detail_return).setOnClickListener(v -> jumpToGroup());
        findViewById(R.id.group_card_detail_page_add_or_exit).setOnClickListener(v -> addOrExitGroup());
    }
}
