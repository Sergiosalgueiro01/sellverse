package com.main.es.sellverse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Sellverse);//set the main activity theme to finish the splash screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}