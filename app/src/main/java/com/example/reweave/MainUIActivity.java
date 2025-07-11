package com.example.reweave;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainUIActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        AppBarConfiguration config = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_donate,
                R.id.navigation_marketplace,
                R.id.navigation_news,
                R.id.navigation_profile
        ).build();

        NavigationUI.setupActionBarWithNavController(this, navController, config);
        NavigationUI.setupWithNavController(navView, navController);
    }
}

