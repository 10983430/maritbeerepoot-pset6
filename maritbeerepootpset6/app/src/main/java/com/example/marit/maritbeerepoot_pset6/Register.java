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
        String email =  emailinput.getText().toString();
        String password = passwordinput.getText().toString();
        String username = usernameinput.getText().toString();
        public void onClick(View view){
                createAccount(email, password);
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
    public void userinfo(String email, String password, String username){
        final DatabaseReference userRef = dbref.child(nicknametext);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Creates user if not yet exists
                if(dataSnapshot.getValue() == null){
                    createUser(emailtext, passwordtext,);
                }
                else {
                    Toast.makeText(getContext(), "User already exists", Toast.LENGTH_SHORT).show();
                }
            }
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
