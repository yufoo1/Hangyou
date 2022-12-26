package com.example.hangyou.ui.group;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.R;
import com.example.hangyou.databinding.FragmentGroupBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class AddGroupPageActivity extends AppCompatActivity {
    SQLiteDatabase database;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_group_page);
        DataBaseHelper helper = new DataBaseHelper(AddGroupPageActivity.this);
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists user_group(id integer primary key autoincrement, groupName text, groupType text, groupInitiator text, groupDescription text, groupYear int, groupMonth int, groupDay int, groupMaleExpectedNum int, groupMaleNowNum int, groupFemaleExpectedNum int, groupFemaleNowNum int)");
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
        findViewById(R.id.add_group_page_button_created).setOnClickListener(v -> commit());
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

    private void commit() {
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
        Cursor cursor = database.rawQuery("select * from user where account=?", new String[]{account});
        cursor.moveToFirst();
        String groupInitiator = cursor.getString(cursor.getColumnIndex("username"));
        cursor.close();
        database.execSQL("insert into user_group(groupName, groupType, groupInitiator, groupDescription, groupYear, groupMonth, groupDay, groupMaleExpectedNum, groupMaleNowNum, groupFemaleExpectedNum, groupFemaleNowNum) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[]{groupName, groupType, groupInitiator, groupDescription, groupYear, groupMonth, groupDay, groupMaleExpectedNum, 0, groupFemaleExpectedNum, 0});
        System.out.println("新组添加成功");
        Intent intent = new Intent();
        intent.setClass(AddGroupPageActivity.this, GroupActivity.class);
        startActivity(intent);
    }
}
