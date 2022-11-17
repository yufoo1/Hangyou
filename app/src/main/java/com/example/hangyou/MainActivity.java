package com.example.hangyou;

import android.os.Bundle;
import android.widget.ListView;

import com.example.hangyou.ui.Connection;
import com.example.hangyou.ui.home.FriendListItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hangyou.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_group, R.id.navigation_tree_hole, R.id.navigation_home)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        listView = (ListView)findViewById(R.id.friend_list);
        ArrayList<FriendListItem> friendListItems = new ArrayList<>();
        friendListItems.add(new FriendListItem("向恩达", "这是一条很长的自我介绍", "xed_head_portrait"));
//        Connection.mymysql();
    }

}