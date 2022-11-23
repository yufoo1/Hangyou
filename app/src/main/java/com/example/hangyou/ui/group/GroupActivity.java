package com.example.hangyou.ui.group;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.databinding.FragmentGroupBinding;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.R;
import com.example.hangyou.ui.home.HomePageActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupActivity extends AppCompatActivity{
    SQLiteDatabase database;
    FragmentGroupBinding binding;

    /* TODO list declare */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_group);
        initClickListener();
        DataBaseHelper helper = new DataBaseHelper(GroupActivity.this);
        binding =FragmentGroupBinding.inflate(getLayoutInflater());
        database = helper.getWritableDatabase();
        showTotalUser();
        showGroupCards();
    }

    private void retMyInBureau() {
        System.out.println("TODO retMyInBureau");
    }

    private void retMyCreatedBureau() {
        System.out.println("TODO retMyCreatedBureau");
    }

    private void jumpToCreateBureau() {
        Intent intent = new Intent();
        intent.setClass(GroupActivity.this, AddGroupPageActivity.class);
//        intent.setClass(GroupActivity.this, HomePageActivity.class);
        startActivity(intent);
    }

    private void jumpToGuide() {
        Intent intent = new Intent();
        intent.setClass(GroupActivity.this, GuideActivity.class);
        startActivity(intent);
    }

    private void initClickListener() {
        findViewById(R.id.button_my_in_bureau).setOnClickListener(v -> retMyInBureau());
        findViewById(R.id.button_my_created_bureau).setOnClickListener(v -> retMyCreatedBureau());
        findViewById(R.id.button_create_bureau).setOnClickListener(v -> jumpToCreateBureau());
        findViewById(R.id.imageButton_guide).setOnClickListener(v -> jumpToGuide());
    }

    private void showTotalUser() {
        TextView textTotalPeople = findViewById(R.id.add_group_page_total_people);
        Cursor cursor = database.rawQuery("select * from user", new String[]{});
        cursor.moveToFirst();
        int cnt = 0;
        if (cursor.moveToFirst()) {
            cnt++;
            while(cursor.moveToNext()) {
                cnt++;
            }
        }
        cursor.close();
        textTotalPeople.setText(String.valueOf(cnt));
    }

    private void showGroupCards() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from user_group", new String[]{});
        HashMap<String, Object> item;
        while(cursor.moveToNext()) {
            item = new HashMap<>();
            item.put("groupName", cursor.getString(cursor.getColumnIndex("groupName")));
            item.put("groupType", cursor.getString(cursor.getColumnIndex("groupType")));
            item.put("groupInitiator", cursor.getString(cursor.getColumnIndex("groupInitiator")));
            String  groupYear = cursor.getString(cursor.getColumnIndex("groupYear"));
            String groupMonth = cursor.getString(cursor.getColumnIndex("groupMonth"));
            String groupDay = cursor.getString(cursor.getColumnIndex("groupDay"));
            String groupDate = groupYear + "-" + groupMonth + "-" + groupDay;
            item.put("groupDate", groupDate);
            item.put("groupMaleExpectedNum", Integer.parseInt(cursor.getString(cursor.getColumnIndex("groupMaleExpectedNum"))));
            item.put("groupMaleNowNum", Integer.parseInt(cursor.getString(cursor.getColumnIndex("groupMaleNowNum"))));
            item.put("groupFemaleExpectedNum", Integer.parseInt(cursor.getString(cursor.getColumnIndex("groupFemaleExpectedNum"))));
            item.put("groupFemaleNowNum", Integer.parseInt(cursor.getString(cursor.getColumnIndex("groupFemaleNowNum"))));
            item.put("groupDescription", cursor.getString(cursor.getColumnIndex("groupDescription")));
            data.add(item);
        }
        cursor.close();
        GroupCardAdapter adapter = new GroupCardAdapter(GroupActivity.this, data);
        ListView groupCards = findViewById(R.id.group_cards);
        groupCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
