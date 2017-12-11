package com.example.marit.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CharInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_info);

        // Get ID from intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");

        TextView temp = findViewById(R.id.temp);
        temp.setText(id);
    }
}
