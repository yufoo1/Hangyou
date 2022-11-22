package com.example.hangyou.ui.group;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.databinding.FragmentGroupBinding;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.R;
import com.example.hangyou.ui.home.HomePageActivity;

public class GroupActivity extends AppCompatActivity{
    SQLiteDatabase database;
    FragmentGroupBinding binding;
    GestureDetector gestureDetector;

    /* TODO list declare */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_group);
        initClickListener();
        DataBaseHelper helper=new DataBaseHelper(GroupActivity.this);
        binding =FragmentGroupBinding.inflate(getLayoutInflater());
        database = helper.getWritableDatabase();
        showTotalUser();
    }

    private void retMyInBureau() {
        System.out.println("TODO retMyInBureau");
    }

    private void retMyCreatedBureau() {
        System.out.println("TODO retMyCreatedBureau");
    }

    private void jumpToCreateBureau() {
        Intent intent = new Intent();
//        intent.setClass(GroupActivity.this, CreateBureauActivity.class);
        intent.setClass(GroupActivity.this, HomePageActivity.class);
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
        TextView textTotalPeople = findViewById(R.id.text_total_people);
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
}
