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
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etFirstName;
    private EditText etLastName;
    private TextView swipeLeft;
    private EditText etRePassword;
    private Button btnSignUp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        btnSignUp = findViewById(R.id.btnRegister);
        etUsername = findViewById(R.id.etSignupUsername);
        etPassword = findViewById(R.id.etSignupPassword);
        etFirstName = findViewById(R.id.etFirstname);
        etLastName = findViewById(R.id.etSignUpLastname);
        etRePassword = findViewById(R.id.etSignupRePassword);
        swipeLeft = findViewById(R.id.swipeLeft);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

                swipeLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goLoginActivity();
                    }
                });
            }

    public void registerUser() {



        if (etFirstName.getText().toString().isEmpty()) {
            etFirstName.setError("First name is required!");
            etFirstName.requestFocus();
            return;
        }
        if (etLastName.getText().toString().isEmpty()) {
            etLastName.setError("Last name is required!");
            etLastName.requestFocus();
            return;
        }
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
        if (etRePassword.getText().toString().isEmpty()) {
            etRePassword.setError("Confirm password is required!");
            etPassword.requestFocus();
            return;
        }
//        System.out.println("Hey----------------xxxxxx");
        if (!(etPassword.getText().toString()).equals(etRePassword.getText().toString())) {
            etRePassword.setError("Password does not a match!");
            etRePassword.requestFocus();
            return;
        }else{

        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        mAuth = FirebaseAuth.getInstance();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("hey", "createUserWithEmail:success");
                                Toast.makeText(SignUpActivity.this, "Registration successful!", Toast.LENGTH_LONG).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                // implement buffering

                                goLoginActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("hey2", "createUserWithEmail:failure", task.getException());
//
                                Toast.makeText(getApplicationContext(), "Authentication failed: " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });

//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            User user = new User(firstName, lastName, email);
//
//                            FirebaseDatabase.getInstance().getReference("Users")
//                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            System.out.println("Here1--------------------------------------------------------------------");
//                                            if (task.isSuccessful()){
//                                                System.out.println("Here2--------------------------------------------------------------------");
//                                                Toast.makeText(SignUpActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
//                                                // go to login page
//                                                goLoginActivity();
//                                            }else{
//                                                Toast.makeText(SignUpActivity.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
//
//                                            }
//                                        }
//                                    });
//                        }else {
//                            Toast.makeText(SignUpActivity.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//                createAccount(etFirstName, etLastName, etPassword, etUsername, genderChoice);

    }}

    private void goLoginActivity() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}