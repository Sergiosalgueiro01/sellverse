package com.main.es.sellverse;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.main.es.sellverse.home.HomeActivity;
import com.main.es.sellverse.login.LoginActivity;
import com.main.es.sellverse.login.UserNameActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Sellverse_NoActionBar);//set the main activity theme to finish the splash screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkForNewWindow();
        setUpButton();


    }

    private void checkForNewWindow() {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        if(!preferences.getString("email","").equals("")) {
            if (!preferences.getString("username", "").equals(""))
                openNextActivity(new Intent(this, HomeActivity.class));
            else {
                openNextActivity(new Intent(this, UserNameActivity.class));
            }
        }

    }

    private void setUpButton() {
        Button button = findViewById(R.id.btnStart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    private void openNextActivity(Intent intent) {
        startActivity(intent);
        finish();
    }
}