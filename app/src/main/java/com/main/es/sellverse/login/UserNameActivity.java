package com.main.es.sellverse.login;

import androidx.appcompat.app.AppCompatActivity;
import com.main.es.sellverse.R;
import com.main.es.sellverse.home.HomeActivity;
import com.main.es.sellverse.persistence.UserDataBase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);
        setUp();
    }

    private void setUp() {
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUsername();
            }
        });
    }

    private void checkUsername() {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email=preferences.getString("email","");
        EditText et = findViewById(R.id.etUsername);
        String s = et.getText().toString();
        if(s.trim().isEmpty()){
            et.requestFocus();
            et.setError(getString(R.string.empty_username));
        }
        else if(UserDataBase.checkIfUserNameExists(s)) {
            et.requestFocus();
            et.setError(getString(R.string.repeated_username));
        }
        else {
            preferences.edit().putString("username",s).commit();
            UserDataBase.addUsername(s, email);
            Intent intent= new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }
}