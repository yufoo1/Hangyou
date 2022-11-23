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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.R;
import com.example.hangyou.databinding.FragmentGroupBinding;

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
        findViewById(R.id.add_group_page_button_created).setOnClickListener(v -> commit());
    }

    private void commit() {
        EditText et_name = findViewById(R.id.add_group_page_name_input);
        String groupName = et_name.getText().toString();
        EditText et_type = findViewById(R.id.add_group_page_type);
        String groupType = et_type.getText().toString();
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
