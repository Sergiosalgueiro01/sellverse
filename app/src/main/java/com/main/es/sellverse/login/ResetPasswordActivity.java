package com.main.es.sellverse.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;



import com.main.es.sellverse.R;

public class ResetPasswordActivity extends AppCompatActivity {

    //private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

      //  myAuth = FirebaseAuth.getInstance();
        clickButtonResetPassword();
    }

    private void clickButtonResetPassword() {
        Button btnResetPasword = findViewById(R.id.btnResetPassword);
        btnResetPasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //recuperar();
            }
        });
    }
/**
    private void recuperar(){
        EditText txtEmail = findViewById(R.id.txtEmailResetPassword);
        String email = txtEmail.getText().toString();
        if(email.isEmpty()){
            Toast.makeText(ResetPasswordActivity.this, "Indicate your email", Toast.LENGTH_SHORT).show();
        }else{
            myAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ResetPasswordActivity.this, "Mail sent correctly",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                        startActivity(intent);

                        finish();
                    }
                    else{
                        Toast.makeText(ResetPasswordActivity.this, "Unregistered email", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }*/
}