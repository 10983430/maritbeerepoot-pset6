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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

/**
 * Makes it possible for a user to register and sends the register information to firebase
 */
public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseUser user;
    EditText emailinput;
    FirebaseDatabase fbdb;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        fbdb = FirebaseDatabase.getInstance();
        dbref = fbdb.getReference("User");

        Button signup = findViewById(R.id.butRegister);
        signup.setOnClickListener(new Click());
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
        updateUI(user);
    }

    // Put user info in database when registering
    private class Click implements View.OnClickListener{
        public void onClick(View view) {
            emailinput = findViewById(R.id.emailreg);
            EditText passwordinput = findViewById(R.id.passwordreg);
            String email = emailinput.getText().toString();
            String password = passwordinput.getText().toString();

            // Length of the password must be greater than 6 for Firebase to accept it as password
            if (passwordinput.length() >= 6) {
                try {
                    createAccount(email, password);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Please fill out your information", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Please make sure your passord has a length of atleast 6!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Create user in firebase
    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Loginstatus", "createUserWithEmail:success");
                    user = mAuth.getCurrentUser();
                    String id = user.getUid();
                    userInformation(emailinput.getText().toString(), id);
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Loginstatus", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
                FirebaseUser currentUser = mAuth.getCurrentUser();
                updateUI(currentUser);
            }
        });
    }

    // Add additional information of the user into the database
    public void userInformation(String email, String id){
        HashMap<String, String> favorites = new HashMap<>();
        EditText usernameinput = findViewById(R.id.usernamereg);
        String username = usernameinput.getText().toString();
        userinfo user = new userinfo(id, username, favorites, email);
        Log.d("tessstt", id.toString());
        dbref.child("user").child(id).setValue(user);
    }

    public void updateUI(FirebaseUser  user) {
        if (user != null) {
            Toast.makeText(getApplicationContext(), "Succes! You are logged in!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Register.this, CharacterDatabase.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
        }
    }
}
