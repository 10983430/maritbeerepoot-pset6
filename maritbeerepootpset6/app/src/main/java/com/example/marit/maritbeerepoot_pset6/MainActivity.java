package com.example.marit.maritbeerepoot_pset6;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText emailfield;
    EditText passwordfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        // Find the email and password input
        emailfield = findViewById(R.id.emailField);
        passwordfield = findViewById(R.id.passwordField);

        // Set listeners on the buttons
        Button login = findViewById(R.id.loginButton);
        Button signup = findViewById(R.id.registerButton);
        login.setOnClickListener(new Click());
        signup.setOnClickListener(new RegisterClick());
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Check if a logged in person is seeing this page
        if (user != null){
            // If there is a logged in user, send him to another activity and notify him with a toast
            Toast.makeText(MainActivity.this, "You are already logged in!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, CharacterDatabase.class);
            startActivity(intent);
            finish();
        }
    }

    private class Click implements View.OnClickListener{
        @Override
        public void onClick(View view){
            String email = emailfield.getText().toString();
            String password = passwordfield.getText().toString();
            switch (view.getId()){
                case R.id.loginButton:
                    try{
                        SignIn(email, password);
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Please fill out your information", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
            }
        }
    }

    private class RegisterClick implements View.OnClickListener{
        @Override
        public void onClick(View view){
            switch (view.getId()){
                case R.id.registerButton:
                    navRegister();
            }
        }
    }

    public void navRegister() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void SignIn(String email, String password) {
        Log.d("oooooooooooooo", email + password);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Loginstatus", "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Loginstatus", "signInWithEmail:failure", task.getException());
                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

                // ...
            }
        });
    }

    public void updateUI(FirebaseUser  user) {
        if (user != null) {
            Toast.makeText(getApplicationContext(), "Succes", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, CharacterDatabase.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Failed, try again", Toast.LENGTH_SHORT).show();
        }
    }
}
