package com.example.hangyou.ui.group;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.MainActivity;
import com.example.hangyou.databinding.FragmentGroupBinding;

import com.example.hangyou.DataBaseHelper;
import com.example.hangyou.R;
import com.example.hangyou.ui.home.HomePageActivity;
import com.example.hangyou.ui.tree_hole.TreeHoleActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GroupActivity extends AppCompatActivity{
    SQLiteDatabase database;
    FragmentGroupBinding binding;
    ArrayList<HashMap<String, Object>> data;


    /* TODO list declare */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_group);
        initClickListener();
        DataBaseHelper helper = new DataBaseHelper(GroupActivity.this);
        binding =FragmentGroupBinding.inflate(getLayoutInflater());
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists user_group(id integer primary key autoincrement, groupName text, groupType text, groupInitiator text, groupDescription text, groupYear int, groupMonth int, groupDay int, groupMaleExpectedNum int, groupMaleNowNum int, groupFemaleExpectedNum int, groupFemaleNowNum int)");
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
        startActivity(intent);
    }

    private void jumpToGuide() {
        Intent intent = new Intent();
        intent.setClass(GroupActivity.this, GuideActivity.class);
        startActivity(intent);
    }

    private void jumpToTreeHole() {
        Intent intent = new Intent();
        intent.setClass(GroupActivity.this, TreeHoleActivity.class);
        startActivity(intent);
    }

    private void jumpToHomePage() {
        Intent intent = new Intent();
        intent.setClass(GroupActivity.this, HomePageActivity.class);
        startActivity(intent);
    }

    private void initClickListener() {
        findViewById(R.id.button_my_in_bureau).setOnClickListener(v -> retMyInBureau());
        findViewById(R.id.button_my_created_bureau).setOnClickListener(v -> retMyCreatedBureau());
        findViewById(R.id.button_create_bureau).setOnClickListener(v -> jumpToCreateBureau());
        findViewById(R.id.imageButton_guide).setOnClickListener(v -> jumpToGuide());
        findViewById(R.id.cardView_haveempty).setOnClickListener(v -> showHaveemptyGtoup());
        findViewById(R.id.cardView_study).setOnClickListener(v -> showStudyGroup());
        findViewById(R.id.cardView_sport).setOnClickListener(v -> showSportGroup());
        findViewById(R.id.cardView_eat).setOnClickListener(v -> showEatGroup());
        findViewById(R.id.cardView_travel).setOnClickListener(v -> showTravelGroup());
        findViewById(R.id.cardView_buy).setOnClickListener(v -> showBuyGroup());
        findViewById(R.id.cardView_movie).setOnClickListener(v -> showMovieGroup());
        findViewById(R.id.cardView_game).setOnClickListener(v -> showGameGroup());
        findViewById(R.id.group_tree_hole).setOnClickListener(v -> jumpToTreeHole());
        findViewById(R.id.group_home_page).setOnClickListener(v -> jumpToHomePage());
    }

    private void showStudyGroup() {
        ArrayList<HashMap<String, Object>> cdata = new ArrayList<>();
        ArrayList<Integer> idLists = new ArrayList<>();
        data.forEach(i -> {
            if (i.get("groupType").equals("学习")) {
                cdata.add(i);
                idLists.add(Integer.parseInt(Objects.requireNonNull(i.get("id")).toString()));
            }
        });
        GroupCardAdapter adapter = new GroupCardAdapter(GroupActivity.this, cdata);
        ListView groupCards = findViewById(R.id.group_cards);
        groupCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        groupCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putInt("id", idLists.get(position));
                Intent intent =new Intent(GroupActivity.this, GroupCardDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showHaveemptyGtoup() {
        Cursor cursor = database.rawQuery("select * from user_group", new String[]{});
        ArrayList<Integer> idLists = new ArrayList<>();
        while(cursor.moveToNext()) {
            idLists.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
        }
        GroupCardAdapter adapter = new GroupCardAdapter(GroupActivity.this, data);
        ListView groupCards = findViewById(R.id.group_cards);
        groupCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        groupCards.setOnItemClickListener((parent, view, position, id) -> {
            Bundle bundle=new Bundle();
            bundle.putInt("id", idLists.get(position));
            Intent intent =new Intent(GroupActivity.this, GroupCardDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        });
    }

    private void showSportGroup() {
        ArrayList<HashMap<String, Object>> cdata = new ArrayList<>();
        ArrayList<Integer> idLists = new ArrayList<>();
        data.forEach(i -> {
            if (i.get("groupType").equals("运动")) {
                cdata.add(i);
                idLists.add(Integer.parseInt(Objects.requireNonNull(i.get("id")).toString()));
            }
        });
        GroupCardAdapter adapter = new GroupCardAdapter(GroupActivity.this, cdata);
        ListView groupCards = findViewById(R.id.group_cards);
        groupCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        groupCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putInt("id", idLists.get(position));
                Intent intent =new Intent(GroupActivity.this, GroupCardDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showEatGroup() {
        ArrayList<HashMap<String, Object>> cdata = new ArrayList<>();
        ArrayList<Integer> idLists = new ArrayList<>();
        data.forEach(i -> {
            if (i.get("groupType").equals("聚餐")) {
                cdata.add(i);
                idLists.add(Integer.parseInt(Objects.requireNonNull(i.get("id")).toString()));
            }
        });
        GroupCardAdapter adapter = new GroupCardAdapter(GroupActivity.this, cdata);
        ListView groupCards = findViewById(R.id.group_cards);
        groupCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        groupCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putInt("id", idLists.get(position));
                Intent intent =new Intent(GroupActivity.this, GroupCardDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showTravelGroup() {
        ArrayList<HashMap<String, Object>> cdata = new ArrayList<>();
        ArrayList<Integer> idLists = new ArrayList<>();
        data.forEach(i -> {
            if (i.get("groupType").equals("旅行")) {
                cdata.add(i);
                idLists.add(Integer.parseInt(Objects.requireNonNull(i.get("id")).toString()));
            }
        });
        GroupCardAdapter adapter = new GroupCardAdapter(GroupActivity.this, cdata);
        ListView groupCards = findViewById(R.id.group_cards);
        groupCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        groupCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putInt("id", idLists.get(position));
                Intent intent =new Intent(GroupActivity.this, GroupCardDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showBuyGroup() {
        ArrayList<HashMap<String, Object>> cdata = new ArrayList<>();
        ArrayList<Integer> idLists = new ArrayList<>();
        data.forEach(i -> {
            if (i.get("groupType").equals("拼单")) {
                cdata.add(i);
                idLists.add(Integer.parseInt(Objects.requireNonNull(i.get("id")).toString()));
            }
        });
        GroupCardAdapter adapter = new GroupCardAdapter(GroupActivity.this, cdata);
        ListView groupCards = findViewById(R.id.group_cards);
        groupCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        groupCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putInt("id", idLists.get(position));
                Intent intent =new Intent(GroupActivity.this, GroupCardDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showMovieGroup() {
        ArrayList<HashMap<String, Object>> cdata = new ArrayList<>();
        ArrayList<Integer> idLists = new ArrayList<>();
        data.forEach(i -> {
            if (i.get("groupType").equals("电影")) {
                cdata.add(i);
                idLists.add(Integer.parseInt(Objects.requireNonNull(i.get("id")).toString()));
            }
        });
        GroupCardAdapter adapter = new GroupCardAdapter(GroupActivity.this, cdata);
        ListView groupCards = findViewById(R.id.group_cards);
        groupCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        groupCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putInt("id", idLists.get(position));
                Intent intent =new Intent(GroupActivity.this, GroupCardDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showGameGroup() {
        ArrayList<HashMap<String, Object>> cdata = new ArrayList<>();
        ArrayList<Integer> idLists = new ArrayList<>();
        data.forEach(i -> {
            if (i.get("groupType").equals("游戏")) {
                cdata.add(i);
                idLists.add(Integer.parseInt(Objects.requireNonNull(i.get("id")).toString()));
            }
        });
        GroupCardAdapter adapter = new GroupCardAdapter(GroupActivity.this, cdata);
        ListView groupCards = findViewById(R.id.group_cards);
        groupCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        groupCards.setOnItemClickListener((parent, view, position, id) -> {
            Bundle bundle=new Bundle();
            bundle.putInt("id", idLists.get(position));
            Intent intent =new Intent(GroupActivity.this, GroupCardDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        });
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
        data = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from user_group", new String[]{});
        ArrayList<Integer> idLists = new ArrayList<>();
        HashMap<String, Object> item;
        System.out.println("dsada");
        while(cursor.moveToNext()) {
            System.out.println("gugugu");
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
            item.put("id", cursor.getString(cursor.getColumnIndex("id")));
            idLists.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
            data.add(item);
        }
        cursor.close();
        GroupCardAdapter adapter = new GroupCardAdapter(GroupActivity.this, data);
        ListView groupCards = findViewById(R.id.group_cards);
        groupCards.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        groupCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putInt("id", idLists.get(position));
                Intent intent =new Intent(GroupActivity.this, GroupCardDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }
}
