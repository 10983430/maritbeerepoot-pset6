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
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        Button signup = findViewById(R.id.butRegister);
        signup.setOnClickListener(new Click());
    }

    @Override
    public void onStart() {
        super.onStart();
        // Update UI on state of user
        user = mAuth.getCurrentUser();
        updateUI(user);
    }

    private class Click implements View.OnClickListener {
        public void onClick(View view) {
            // Get email and password from the inputfields
            EditText emailinput = findViewById(R.id.emailreg);
            EditText passwordinput = findViewById(R.id.passwordreg);
            String email = emailinput.getText().toString();
            String password = passwordinput.getText().toString();

            // Length of the password must be greater than 6 for Firebase to accept it as password
            if (passwordinput.length() >= 6) {
                try {
                    createAccount(email, password);
                } catch (Exception e) {
                    // When createAccount has no input, it throws an error so this lets the user know that no information was filled out
                    Toast.makeText(getApplicationContext(), "Please fill out your information", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Please make sure your password has a length of atleast 6!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Creates user in firebase
     */
    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Loginstatus", "createUserWithEmail:success");

                    // Add the username to Firebase and update UI
                    user = mAuth.getCurrentUser();
                    String id = user.getUid();
                    EditText emailinput = findViewById(R.id.emailreg);
                    userInformation(emailinput.getText().toString(), id);
                    updateUI(user);
                }
                else {
                    // If sign in fails, display a message to the user.
                    Log.w("Loginstatus", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    /**
     * Adds additional information of the user into the database
     */
    public void userInformation(String email, String id){
        // Create an empty hashmap for the favorites, to use when adding items
        HashMap<String, String> favorites = new HashMap<>();

        // Get username
        EditText usernameinput = findViewById(R.id.usernamereg);
        String username = usernameinput.getText().toString();

        // Create a new instance of the class UserInfoClass for an user and insert into Firebase
        UserInfoClass user = new UserInfoClass(id, username, favorites, email);
        FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
        DatabaseReference dbref = fbdb.getReference("User");
        dbref.child("user").child(id).setValue(user);
    }

    /**
     * Updates the UI when the registration was successful and otherwise toast to try again
     */
    public void updateUI(FirebaseUser  user) {
        if (user != null) {
            Toast.makeText(getApplicationContext(), "Succes! You are logged in!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Register.this, CharacterDatabase.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Failed, try again", Toast.LENGTH_SHORT).show();
        }
    }
}
