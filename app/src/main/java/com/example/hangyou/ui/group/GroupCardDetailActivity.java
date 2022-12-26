package com.example.hangyou.ui.group;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.R;

public class GroupCardDetailActivity extends AppCompatActivity {
    private int id;
    SQLiteDatabase database;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_group_detail);
        DataBaseHelper helper = new DataBaseHelper(GroupCardDetailActivity.this);
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists user_group(id integer primary key autoincrement, groupName text, groupType text, groupInitiator text, groupDescription text, groupYear int, groupMonth int, groupDay int, groupMaleExpectedNum int, groupMaleNowNum int, groupFemaleExpectedNum int, groupFemaleNowNum int)");
        database.execSQL("create table if not exists user_group_relation(id integer primary key autoincrement, userId integer, groupId integer)");
        Bundle receiver = getIntent().getExtras();
        id = receiver.getInt("id");
        initView();
        initClickListener();
    }

    private void initView() {
        Cursor cursor = database.rawQuery("select * from user_group where id=?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        System.out.println(id);
        String name = cursor.getString(cursor.getColumnIndex("groupName"));
        TextView tv_groupName = findViewById(R.id.group_card_detail_groupName);
        tv_groupName.setText(name);
//        String type = cursor.getString(cursor.getColumnIndex("groupType"));
//        TextView tv_groupType = findViewById(R.id.group_card_detail_groupType);
//        tv_groupType.setText(type);
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
        TextView tv_male_required_num = findViewById(R.id.group_card_detail_groupMaleRequiredNum);
        tv_male_required_num.setText(maleRequiredNum);
        String femaleNowNum = cursor.getString(cursor.getColumnIndex("groupFemaleNowNum"));
        TextView tv_female_now_num = findViewById(R.id.group_card_detail_groupFemaleNowNum);
        tv_female_now_num.setText(femaleNowNum);
        String femaleExpectedNum = cursor.getString(cursor.getColumnIndex("groupFemaleExpectedNum"));
        String femaleRequiredNum = String.valueOf(Integer.parseInt(femaleExpectedNum) - Integer.parseInt(femaleNowNum));
        TextView tv_female_required_num = findViewById(R.id.group_card_detail_groupFemaleRequiredNum);
        tv_female_required_num.setText(femaleRequiredNum);
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
        database.execSQL("insert into user_group_relation(userId, groupId) values (?, ?)", new Object[]{userId, id});
        cursor = database.rawQuery("select * from user_group where id=?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();

        Intent intent = new Intent();
        intent.setClass(this, GroupActivity.class);
        startActivity(intent);
    }

    private void initClickListener() {
        findViewById(R.id.group_card_detail_return).setOnClickListener(v -> jumpToGroup());
        findViewById(R.id.group_card_detail_page_add_or_exit).setOnClickListener(v -> addOrExitGroup());
    }
}
