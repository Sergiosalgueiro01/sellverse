package com.main.es.sellverse.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.ktx.Firebase;
import com.google.j2objc.annotations.ObjectiveCName;
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
        myAuth=FirebaseAuth.getInstance();
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
        String password = txtPasword.getText().toString();
        TextView txtUsername = findViewById(R.id.txtUsername);
        String username = txtUsername.getText().toString();
        TextView txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        String phone_number = txtPhoneNumber.getText().toString();
        TextView txtName = findViewById(R.id.txtNameR);
        String name = txtName.getText().toString();
        TextView txtSurname = findViewById(R.id.txtSurnameR);
        String surname = txtSurname.getText().toString();

        if (!txtEmail.getText().toString().isEmpty() && !txtPasword.getText().toString().isEmpty()
            && !txtUsername.getText().toString().isEmpty() && !txtPhoneNumber.getText().toString().isEmpty()) {
            myAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPasword.getText().toString()).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String id = myAuth.getCurrentUser().getUid();
                                Map<String, Object> map = new HashMap<>();
                                map.put("id", id);
                                map.put("name", username);
                                map.put("name", name);
                                map.put("surname", surname);
                                map.put("email", email);
                                map.put("phone_number", phone_number);

                                mFirestore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        FirebaseUser user = myAuth.getCurrentUser();
                                        user.sendEmailVerification();
                                        Intent intent = new Intent(RegisterActivity.this, VerifyActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterActivity.this, "Error to save", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else {
                                showAlert();
                            }
                        }
                    });
        }
    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("An error ocurred when you save it");
        builder.setPositiveButton("Accept", null);
        builder.create().show();
    }
}