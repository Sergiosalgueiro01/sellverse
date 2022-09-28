package com.main.es.sellverse.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.main.es.sellverse.R;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth myAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpRegisterEmailPaswword();
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
        TextView txtPasword = findViewById(R.id.txtPasswordR);
        if (!txtEmail.getText().toString().isEmpty() && !txtPasword.getText().toString().isEmpty()) {
            myAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPasword.getText().toString()).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = myAuth.getCurrentUser();
                                user.sendEmailVerification();
                                Intent intent = new Intent(RegisterActivity.this, VerifyActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                showAlert();
                            }
                        }
                    });
        }
    }

    private void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Se ha producido un error al autenticar al usuario");
        builder.setPositiveButton("Aceptar", null);
        builder.create().show();
    }
}