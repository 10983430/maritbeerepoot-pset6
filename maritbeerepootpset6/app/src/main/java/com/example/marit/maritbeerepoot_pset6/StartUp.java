package com.example.marit.maritbeerepoot_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Used to put listeners on the buttons of the startview and navigate to next screen
 */
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

    private class Click implements View.OnClickListener {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.GoDatabaseButton:
                    navDatabase();
                    break;
                case R.id.GoLoginButton:
                    // Check if a user is already logged in, if he is, don't allow him to go to login
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

    /**
     * Starts the Characterbase activity
     */
    public void navDatabase() {
        Intent intent = new Intent(this, CharacterDatabase.class);
        startActivity(intent);
    }

    /**
     * Starts the login activity
     */
    public void navLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

}
