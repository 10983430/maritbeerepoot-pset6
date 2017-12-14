package com.example.marit.maritbeerepoot_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import junit.framework.Test;

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
        TextView FavoritesView = findViewById(R.id.FavoritesView);
        FavoritesView.setText("This user didn't add anything to favorites yet");
        try {
            HashMap<String, MarvelCharacters> favorites = (HashMap<String, MarvelCharacters>) intent.getSerializableExtra("favorites");
            FavoritesView.setText(favorites.values().toString());
            Object values = favorites.values();
            Log.d("HOIIIIIIIIIIIIIII", favorites.get("1009378").toString());


            // Get hash and timestamp from the Character Database class
            Log.d("HOIIIIIIIIIIIIIII", favorites.keySet().toString());
            for (MarvelCharacters value: favorites.values()){
                Log.d("HOIIIIIIIIIIIIIIILLL", value.getName());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        /*if (favorites == null){

        }
        else {

        }*/
        TextView temp = findViewById(R.id.textView444);
        temp.setText(username + id + email);
    }
}
