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

/**
 *  Makes it possible for a user to login or navigate to the register page
 */
public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        // Set listeners on the buttons
        Button login = findViewById(R.id.loginButton);
        Button signup = findViewById(R.id.registerButton);
        login.setOnClickListener(new Click());
        signup.setOnClickListener(new Click());
    }

    /**
     * Checks if user is already logged in and when a user is, it sends
     * the user to the database while logging in twice is not possible
     */
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Check if a logged in person is seeing this page
        if (user != null){
            // If there is a logged in user, send him to another activity and notify him with a toast
            Toast.makeText(Login.this, "You are already logged in!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, CharacterDatabase.class);
            startActivity(intent);
            finish();
        }
    }

    private class Click implements View.OnClickListener{
        @Override
        public void onClick(View view){
            // Find the email and password input
            EditText emailfield = findViewById(R.id.emailField);
            EditText passwordfield = findViewById(R.id.passwordField);
            String email = emailfield.getText().toString();
            String password = passwordfield.getText().toString();

            switch (view.getId()){
                case R.id.loginButton:
                    try{
                        SignIn(email, password);
                    }
                    catch (Exception e){
                        // When SignIn has no input, it throws an error so this lets the user know that no information was filled out
                        Toast.makeText(getApplicationContext(), "Please fill out your information", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    break;
                case R.id.registerButton:
                    navRegister();
                    break;
            }
        }
    }

    /**
     * Navigates user to the register activity
     */
    public void navRegister() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    /**
     * Tries to sign in user
     */
    public void SignIn(String email, String password) {
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
            }
        });
    }

    /**
     * Updates the UI when the login was successful and otherwise toast to try again
     */
    public void updateUI(FirebaseUser  user) {
        // If login is succesfull, navigate user to the database
        if (user != null) {
            Toast.makeText(getApplicationContext(), "Succes", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, CharacterDatabase.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Failed, try again", Toast.LENGTH_SHORT).show();
        }
    }
}
