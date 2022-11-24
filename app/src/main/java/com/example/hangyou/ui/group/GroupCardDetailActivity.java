package com.example.hangyou.ui.group;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.R;

import org.w3c.dom.Text;

public class GroupCardDetailActivity extends AppCompatActivity {
    private int id;
    SQLiteDatabase database;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_group_card_detail);
        DataBaseHelper helper = new DataBaseHelper(GroupCardDetailActivity.this);
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists user_group(id integer primary key autoincrement, groupName text, groupType text, groupInitiator text, groupDescription text, groupYear int, groupMonth int, groupDay int, groupMaleExpectedNum int, groupMaleNowNum int, groupFemaleExpectedNum int, groupFemaleNowNum int)");
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
        String type = cursor.getString(cursor.getColumnIndex("groupType"));
        TextView tv_groupType = findViewById(R.id.group_card_detail_groupType);
        tv_groupType.setText(type);
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
        String maleExpectedNum = cursor.getString(cursor.getColumnIndex("groupMaleExpectedNum"));
        TextView tv_male_expected_num = findViewById(R.id.group_card_detail_groupMaleExpectedNum);
        tv_male_expected_num.setText(maleExpectedNum);
        String maleNowNum = cursor.getString(cursor.getColumnIndex("groupMaleNowNum"));
        TextView tv_male_now_num = findViewById(R.id.group_card_detail_groupMaleNowNum);
        tv_male_now_num.setText(maleNowNum);
        String femaleExpectedNum = cursor.getString(cursor.getColumnIndex("groupFemaleExpectedNum"));
        TextView tv_female_expected_num = findViewById(R.id.group_card_detail_groupFemaleExpectedNum);
        tv_female_expected_num.setText(femaleExpectedNum);
        String femaleNowNum = cursor.getString(cursor.getColumnIndex("groupFemaleNowNum"));
        TextView tv_female_now_num = findViewById(R.id.group_card_detail_groupFemaleNowNum);
        tv_female_now_num.setText(femaleNowNum);
    }

    private void jumpToGroup() {
        Intent intent = new Intent();
        intent.setClass(this, GroupActivity.class);
        startActivity(intent);
    }

    private void initClickListener() {
        findViewById(R.id.group_card_detail_return).setOnClickListener(v -> jumpToGroup());
    }
}
