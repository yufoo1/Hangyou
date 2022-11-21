package com.example.hangyou.ui.group;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ObservableField;

import com.example.hangyou.databinding.FragmentGroupBinding;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.R;

public class GroupActivity extends AppCompatActivity{
    SQLiteDatabase database;
    FragmentGroupBinding binding;

    /* TODO list declare */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_group);
        initClickListener();
        DataBaseHelper helper=new DataBaseHelper(GroupActivity.this);
        binding =FragmentGroupBinding.inflate(getLayoutInflater());
        GroupViewModel groupViewModel = new GroupViewModel(new ObservableField<>(Integer.valueOf("1")));
        binding.setGroupViewModel(groupViewModel);
        System.out.println("hello" + groupViewModel.getTotalUser());
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists user_group(id integer primary key autoincrement, groupNmae text, groupType text, groupInitiator text, groupDate text, groupPeopleNum int, groupMaleExpectedNum int)");
    }

    private void retMyInBureau() {
        System.out.println("TODO retMyInBureau");
    }

    private void retMyCreatedBureau() {
        System.out.println("TODO retMyCreatedBureau");
    }

    private void jumpToCreateBureau() {
        Intent intent = new Intent();
        intent.setClass(GroupActivity.this, CreateBureauActivity.class);
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

    private int getUserTotal() {
        return 0;
    }
}
