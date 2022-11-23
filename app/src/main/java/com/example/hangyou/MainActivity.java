package com.example.hangyou;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.example.hangyou.databinding.ActivityMainBinding;
import com.example.hangyou.ui.login.LoginActivity;

import com.example.hangyou.ui.Connection;
import com.example.hangyou.ui.home.FriendListItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DataBaseHelper helper=new DataBaseHelper(MainActivity.this);
        database = helper.getWritableDatabase();
        database.execSQL("create table if not exists user(id integer primary key autoincrement, account text, password text, username text, description text)");
        database.execSQL("create table if not exists user_group(id integer primary key autoincrement, groupNmae text, groupType text, groupInitiator text, groupDate text, groupPeopleNum int, groupMaleExpectedNum int)");
        new Handler().postDelayed(() -> {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }, 3000);

//        BottomNavigationView navView = findViewById(R.id.nav_view);
//         Passing each menu ID as a set of Ids because each
//         menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_group, R.id.navigation_tree_hole, R.id.navigation_home)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupWithNavController(navView, navController);
//
//        ArrayList<FriendListItem> friendListItems = new ArrayList<>();
//        friendListItems.add(new FriendListItem("向恩达", "这是一条很长的自我介绍", "xed_head_portrait"));
    }

}