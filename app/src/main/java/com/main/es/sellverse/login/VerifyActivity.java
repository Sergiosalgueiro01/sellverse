package com.main.es.sellverse.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.main.es.sellverse.R;
import com.main.es.sellverse.home.HomeActivity;

public class VerifyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        verificado();
    }

    private void verificado() {
        Button btnVerificado = findViewById(R.id.btnVerificado);
        btnVerificado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.reload();
                if (user.isEmailVerified()){
                    updateUI(user);
                }
                else{
                    Toast.makeText(VerifyActivity.this, "Verifique su correo", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null){
            Intent intent= new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }
}