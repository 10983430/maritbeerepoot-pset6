package com.example.marit.maritbeerepoot_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartUp extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        // Put listeners on the buttons
        Button login = findViewById(R.id.GoLoginButton);
        login.setOnClickListener(new Click());
        Button database = findViewById(R.id.GoDatabaseButton);
        database.setOnClickListener(new Click());
    }

    @Override
    public void onBackPressed() {
        // Do nothing, this makes sure that a person that just logged out and is directed to this activity can't
        // go back an be logged in again. It's also the mainscreen, so there is no page before this, which
        // makes it weird to have an onBackPressed
    }

    private class Click implements View.OnClickListener {
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.GoDatabaseButton:
                    navDatabase();
                    break;
                case R.id.GoLoginButton:
                    if (user == null) {
                        navLogin();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "You are already loged in!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    // When the database button is clicked, go to the database acitivity
    public void navDatabase(){
        Intent intent = new Intent(this, CharacterDatabase.class);
        startActivity(intent);
    }

    // When the login button is clicked, go to the login acitivity
    public void navLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
