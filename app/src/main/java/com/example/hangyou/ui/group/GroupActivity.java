package com.example.hangyou.ui.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hangyou.databinding.FragmentGroupBinding;

import com.example.hangyou.R;
import com.example.hangyou.ui.home.HomePageActivity;
import com.example.hangyou.ui.tree_hole.TreeHoleActivity;
import com.example.hangyou.utils.MysqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class GroupActivity extends AppCompatActivity{
    FragmentGroupBinding binding;
    ArrayList<HashMap<String, Object>> data;

    /* TODO list declare */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_group);
        initClickListener();
        binding =FragmentGroupBinding.inflate(getLayoutInflater());
        try {
            showTotalUser();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            showGroupCards();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        findViewById(R.id.cardView_haveempty).setOnClickListener(v -> {
            try {
                showHaveemptyGtoup();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
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

    private void showHaveemptyGtoup() throws SQLException {
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        AtomicBoolean flag1 = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from user_group";
                PreparedStatement ps = connection.prepareStatement(sql);
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag1.get());
        ArrayList<Integer> idLists = new ArrayList<>();
        while(resultSet.get().next()) {
            idLists.add(Integer.parseInt(resultSet.get().getString("id")));
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

    private void showTotalUser() throws SQLException {
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        TextView textTotalPeople = findViewById(R.id.add_group_page_total_people);
        AtomicBoolean flag1 = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from user";
                PreparedStatement ps = connection.prepareStatement(sql);
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag1.get());
        int cnt = 0;
        while(resultSet.get().next()) {
            cnt++;
        }
        textTotalPeople.setText(String.valueOf(cnt));
    }

    private void showGroupCards() throws SQLException {
        data = new ArrayList<>();
        AtomicReference<ResultSet> resultSet = new AtomicReference<>();
        AtomicBoolean flag1 = new AtomicBoolean(false);
        new Thread(() -> {
            try {
                Connection connection = MysqlConnector.getConnection();
                String sql = "select * from user_group";
                PreparedStatement ps = connection.prepareStatement(sql);
                resultSet.set(ps.executeQuery());
                flag1.set(true);
            } catch (InterruptedException | SQLException e) {
                e.printStackTrace();
            }
        }).start();
        while(!flag1.get());
        ArrayList<Integer> idLists = new ArrayList<>();
        HashMap<String, Object> item;
        while(resultSet.get().next()) {
            item = new HashMap<>();
            item.put("groupName", resultSet.get().getString("groupName"));
            item.put("groupType", resultSet.get().getString("groupType"));
            item.put("groupInitiator", resultSet.get().getString("groupInitiator"));
            String  groupYear = resultSet.get().getString("year");
            String groupMonth = resultSet.get().getString("month");
            String groupDay = resultSet.get().getString("day");
            String groupDate = groupYear + "-" + groupMonth + "-" + groupDay;
            item.put("groupDate", groupDate);
            item.put("groupMaleExpectedNum", Integer.parseInt(resultSet.get().getString("maleExpect")));
            item.put("groupMaleNowNum", Integer.parseInt(resultSet.get().getString("maleNow")));
            item.put("groupFemaleExpectedNum", Integer.parseInt(resultSet.get().getString("femaleExpect")));
            item.put("groupFemaleNowNum", Integer.parseInt(resultSet.get().getString("femaleNow")));
            item.put("groupDescription", resultSet.get().getString("description"));
            item.put("id", resultSet.get().getString("id"));
            idLists.add(Integer.parseInt(resultSet.get().getString("id")));
            data.add(item);
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
}
