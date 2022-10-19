package com.example.textscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        TextView ftPassword = findViewById(R.id.forgotPassword);
        etPassword = findViewById(R.id.etPassword);
        Button btnLogin = findViewById(R.id.btLogIn);
        Button btLogIn2 = findViewById(R.id.btLogIn2);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();


            }
        });

        btLogIn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                goToSignUpActivity();
            }


        });

        ftPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goForgottenPassword();
            }


        });


    }

    private void loginUser(){
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

        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Password is required!");
            etPassword.requestFocus();
            return;
        }
        if (etPassword.getText().toString().length() < 6) {
            etPassword.setError("Password must be more then 6 characters!");
            etPassword.requestFocus();
            return;
        }

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(etUsername.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            goToMainActivity();
                        }else{

                            Toast.makeText(LoginActivity.this, "Username and password incorrect!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

//    private void loginUser(String username, String password) {
//        ParseUser.logInInBackground(username, password, new LogInCallback() {
//            @Override
//            public void done(ParseUser user, ParseException e) {
//                if (e != null) {
//                    Toast.makeText(getContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
//                } else {
//                    goToMainActivity();
//                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    private void goToSignUpActivity() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    private void goForgottenPassword() {
        Intent intent = new Intent(LoginActivity.this, ForgottenPassword.class);
        startActivity(intent);
        finish();
    }

    private void goMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}