package com.main.es.sellverse.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.main.es.sellverse.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth myAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpRegisterEmailPaswword();
        mFirestore = FirebaseFirestore.getInstance();
        myAuth= FirebaseAuth.getInstance();
    }


    private void setUpRegisterEmailPaswword(){
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpEmailPassword();
            }
        });
    }

    private void signUpEmailPassword(){
        TextView txtEmail = findViewById(R.id.txtEmailR);
        String email = txtEmail.getText().toString();
        TextView txtPasword = findViewById(R.id.txtPasswordR);
        if(txtPasword.getText().length()<6){
            txtPasword.requestFocus();
            txtPasword.setError(getString(R.string.max_length_password));
        }
        else {
            if (!txtEmail.getText().toString().isEmpty() && !txtPasword.getText().toString().isEmpty()
            ) {
                myAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPasword.getText().toString()).addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String id = myAuth.getCurrentUser().getUid();
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("id", id);
                                    map.put("email", email);
                                    map.put("phone_number", "Phone");
                                    map.put("name", "Name");

                                    mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            FirebaseUser user = myAuth.getCurrentUser();
                                            addToSharedPreferences(user);
                                            Intent intent = new Intent(RegisterActivity.this, UserNameActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterActivity.this, "Error to save", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    showAlert();
                                }
                            }
                        });
            }
        }
    }

    private void addToSharedPreferences(FirebaseUser user) {
        SharedPreferences preferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", user.getEmail());
        editor.putString("userID",user.getUid());
        editor.commit();
    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("An error ocurred when you save it");
        builder.setPositiveButton("Accept", null);
        builder.create().show();
    }
}