package com.main.es.sellverse.login;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.main.es.sellverse.R;
import com.main.es.sellverse.home.HomeActivity;
import com.main.es.sellverse.persistence.UserDataBase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class UserNameActivity extends AppCompatActivity {

    private FirebaseAuth myAuth;

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
        String id= FirebaseAuth.getInstance().getCurrentUser().getUid();
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
            UserDataBase.addUsername(s, id);
            createUser();
            Intent intent= new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }


    }


    private void createUser() {
        myAuth = FirebaseAuth.getInstance();
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        String id = myAuth.getCurrentUser().getUid();
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("email", "Email");
        map.put("phone_number", "Phone");
        map.put("name", "Name");
        map.put("surname", "Surname");
        mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                FirebaseUser user = myAuth.getCurrentUser();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error to save", Toast.LENGTH_SHORT).show();
            }
        });

    }
}