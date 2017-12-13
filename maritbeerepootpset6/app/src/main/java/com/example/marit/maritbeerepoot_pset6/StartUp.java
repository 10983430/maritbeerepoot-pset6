package com.example.marit.maritbeerepoot_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartUp extends AppCompatActivity {

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
            switch (view.getId()){
                case R.id.GoDatabaseButton:
                    navDatabase();
                    break;
                case R.id.GoLoginButton:
                    navLogin();
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
