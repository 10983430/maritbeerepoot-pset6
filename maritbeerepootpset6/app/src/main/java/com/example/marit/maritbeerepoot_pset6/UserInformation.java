package com.example.marit.maritbeerepoot_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

public class UserInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        Bundle intentextra = getIntent().getExtras();
        String username = intentextra.getString("username");
        String id = intentextra.getString("id");
        String email = intentextra.getString("email");
        Intent intent = getIntent();
        //HashMap<String, String> favorites = (HashMap<String, String>)intent.getSerializableExtra("favorites");
        TextView temp = findViewById(R.id.textView444);
        temp.setText(username + id + email); //+favorites.keySet().toString());
    }
}
