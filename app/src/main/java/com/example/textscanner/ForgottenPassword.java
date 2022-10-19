package com.example.textscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ForgottenPassword extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText etUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);

        etUsername = findViewById(R.id.etUsername);
        Button resetPassword = findViewById(R.id.btResetPW);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword(){
        if (etUsername.getText().toString().isEmpty()) {
            etUsername.setError("Email is required!");
            etUsername.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(etUsername.getText().toString().trim()).matches()) {
            etUsername.setError("Please provide a valid email!");
            etUsername.requestFocus();
            return;
        }

        mAuth = FirebaseAuth.getInstance();

        mAuth.sendPasswordResetEmail(etUsername.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgottenPassword.this, "Email sent.", Toast.LENGTH_LONG).show();

                            // buffering
                            goLoginActivity();
                        }else{
                            Toast.makeText(getApplicationContext(), "Authentication failed: " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void goLoginActivity() {
        Intent intent = new Intent(ForgottenPassword.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}