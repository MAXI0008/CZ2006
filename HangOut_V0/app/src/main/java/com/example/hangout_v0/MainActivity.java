package com.example.hangout_v0;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.hangout_v0.Home.HomeFragment;
import com.example.hangout_v0.Me.MeFragment;
import com.example.hangout_v0.Nearby.NearbyFragment;
import com.example.hangout_v0.Recommendation.RecommendationFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hide original app bar
        this.getSupportActionBar().hide();

//        FloatingActionButton exit = findViewById(R.id.exitFloatingActionButton);
//        exit.bringToFront();

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navLister);

        // initialize with home fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navLister =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.navigation_nearby:
                            selectedFragment = new NearbyFragment();
                            break;
                        case R.id.navigation_recommendation:
                            selectedFragment = new RecommendationFragment();
                            break;
                        case R.id.navigation_me:
                            selectedFragment = new MeFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                            selectedFragment).commit();

                    return true;
                }
            };

}