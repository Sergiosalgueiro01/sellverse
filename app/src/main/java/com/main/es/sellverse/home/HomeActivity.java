package com.main.es.sellverse.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.main.es.sellverse.R;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView ;
    private Fragment homeFragment = new HomeFragment();
    private Fragment profileFragment = new ProfileFragment();
    private Fragment chatFragment = new ChatFragment();
    private Fragment yourBidsFragment = new YourBidsFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUpFragments();

    }

    private void setUpFragments() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        bottomNavigationView.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_icon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

                        break;
                    case R.id.bids_icon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,yourBidsFragment).commit();
                        break;

                    case R.id.add_icon:
                       openAddActivity();
                        break;

                    case R.id.profile_icon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment).commit();
                        break;

                    case R.id.chat_icon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,chatFragment).commit();
                        break;
                }

                }
            });
        }

    private void openAddActivity() {

    }
}


