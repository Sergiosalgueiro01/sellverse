package com.main.es.sellverse;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.main.es.sellverse.home.HomeActivity;
import com.main.es.sellverse.login.LoginActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Sellverse);//set the main activity theme to finish the splash screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpButton();


    }

    private void setUpButton() {
        Button button = findViewById(R.id.btnStart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNextActivity();
            }
        });
    }

    private void openNextActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}