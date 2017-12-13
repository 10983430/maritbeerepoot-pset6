package com.example.marit.maritbeerepoot_pset6;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText passwordinput;
    EditText emailinput;
    EditText usernameinput;
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

        emailinput = findViewById(R.id.emailreg);
        passwordinput = findViewById(R.id.passwordreg);
        usernameinput = findViewById(R.id.usernamereg);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    // Put user info in database when registering
    private class Click implements View.OnClickListener{
        public void onClick(View view) {
            EditText emailinput = findViewById(R.id.emailreg);
            String email = emailinput.getText().toString();
            EditText passwordinput = findViewById(R.id.passwordreg);
            String password = passwordinput.getText().toString();
            Log.d("tesssttttt", email + password);
            try {
                createAccount(email, password);
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(), "Please fill out your information", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
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
                    FirebaseUser user = mAuth.getCurrentUser();
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

                // ...
            }
        });
    }

    // Add additional information of the user into the database
    public void userInformation(String email, String id){
        HashMap<String, String> favorites = new HashMap<>();
        String username = usernameinput.getText().toString();
        userinfo user = new userinfo(id, username, favorites, email);
        Log.d("tessstt", id.toString());
        dbref.child("user").child(id).setValue(user);
        /*favorites.put("tessstttt", "hahahahah");
        try {
            dbref.child("user").child(id).child("favorites").setValue(favorites);
        } catch (Exception e) {
            e.printStackTrace();
        }
        favorites.put("tessstttt2222", "hahahahah222");
        try {
            dbref.child("user").child(id).child("favorites").setValue(favorites);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbref.child("user").child(id).child("favorites").ge*/
    }

    public void updateUI(FirebaseUser  user) {
        if (user != null) {
            Toast.makeText(getApplicationContext(), "succes", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
        }
    }
}
