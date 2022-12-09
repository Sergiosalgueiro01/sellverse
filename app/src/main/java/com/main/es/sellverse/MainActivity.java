package com.main.es.sellverse;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.main.es.sellverse.home.HomeActivity;
import com.main.es.sellverse.login.LoginActivity;
import com.main.es.sellverse.login.UserNameActivity;
import com.main.es.sellverse.persistence.UserDataBase;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkForNewWindow();


    }

    private void checkForNewWindow() {


        String idUser= FirebaseAuth.getInstance().getUid();


        UserDataBase.checkIfUserHasUsernameMain(idUser,this);



    }


    public void setUpButton() {
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