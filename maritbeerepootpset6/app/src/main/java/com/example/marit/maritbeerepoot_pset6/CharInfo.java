package com.example.marit.maritbeerepoot_pset6;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class CharInfo extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase fbdb;
    DatabaseReference dbref;
    DatabaseReference dbref2;
    String id;
    String charid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_info);

        // Get ID from intent
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        MarvelCharacters character = getIntent().getExtras().getParcelable("Character");

        TextView name = findViewById(R.id.infoName);
        name.setText(character.getName());
        TextView description = findViewById(R.id.infoDescription);
        description.setText(character.getDescription());
        TextView comics = findViewById(R.id.infoComics);
        comics.setText(character.getComics().toString());
        TextView series = findViewById(R.id.infoSeries);
        series.setText(character.getSeries().toString());
        TextView stories = findViewById(R.id.infoStories);
        stories.setText(character.getStories().toString());
        charid = character.getId();
        Log.d("lolloll", "k"+charid.toString());
        //getdata(id);
        ImageView characterpic = findViewById(R.id.CharacterPic);
        String url = character.getSquareLargeImageURL();
        Picasso.with(getApplicationContext()).load(url).into(characterpic);

        Button database = findViewById(R.id.favoButton);
        database.setOnClickListener(new Click());

    }

    public void updateFavorites(String userid, HashMap favorites) {
        // Get to the right place in the database
        fbdb = FirebaseDatabase.getInstance();
        dbref2 = fbdb.getReference("User");
        try {
            // Change the value of favorites to a list with the new item
            dbref2.child("user").child(userid).child("favorites").setValue(favorites);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Click implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (user != null) {
                // Get to the right place of the database
                fbdb = FirebaseDatabase.getInstance();
                String userid = user.getUid();
                dbref = fbdb.getReference("User/user/"+userid);

                // Add listener
                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get the hashtable with the already added favorites
                        DataSnapshot value =  dataSnapshot.child("favorites");
                        HashMap<String, String> favorites = (HashMap<String, String>) value.getValue();
                        if (favorites == null){
                            favorites = new HashMap<String, String>();
                        }
                        // Update the values of hashtable favorites
                        favorites.put(charid, charid);

                        // Update the database with the updateFavorites method
                        String userid = user.getUid();
                        updateFavorites(userid, favorites);
                    }
                    @Override
                    // Catch errors
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("onCancelled Charinfo", databaseError.toString());
                    }
                });

                // Let the user know that the item was added
                Toast.makeText(getApplicationContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
            }
            else {
                // Warn user that you can't add an item to the database when you are not logged in
                Toast.makeText(getApplicationContext(), "Please login to favorite something", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
