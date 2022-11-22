package com.example.hangyou;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.hangyou.databinding.ActivityMainBinding;
import com.example.hangyou.ui.group.GroupActivity;
import com.example.hangyou.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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