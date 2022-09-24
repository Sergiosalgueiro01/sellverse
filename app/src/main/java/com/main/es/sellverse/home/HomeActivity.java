package com.main.es.sellverse.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.main.es.sellverse.R;
import com.main.es.sellverse.add.AddPublicationActivity;

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

    /**
     * Set up the navigation menu
     */
    private void setUpFragments() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        View add = findViewById(R.id.add_icon);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddActivity();
            }
        });

        //this is just for add icon because it needs to open a new activity

        //this is for the other one because it needs to open a fragment
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

    /**
     * Opens the add activity
     */
    private void openAddActivity() {
        Intent intent = new Intent(this, AddPublicationActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.up_in,R.anim.up_out);

    }

}


