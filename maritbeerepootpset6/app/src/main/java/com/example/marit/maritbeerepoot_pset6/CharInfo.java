package com.example.marit.maritbeerepoot_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

/**
 * Converts the parceable to UI and makes it possible for a user to add character to favorites
 */
public class CharInfo extends AppCompatActivity {
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase fbdb;
    private DatabaseReference dbref;
    private String charid;
    private MarvelCharacters character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_info);

        // Get parceable from intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        character = getIntent().getExtras().getParcelable("Character");

        // Set all the textviews
        setTextViews();

        // Declare the userId
        charid = character.getId();

        // Set Imageview
        setImageView();

        Button database = findViewById(R.id.favoButton);
        database.setOnClickListener(new Click());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflatercharinfomenu = getMenuInflater();
        inflatercharinfomenu.inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_characterdatabase:
                Intent intentCD = new Intent(this, CharacterDatabase.class);
                startActivity(intentCD);
                return true;
            case R.id.action_userdatabase:
                Intent intentUD = new Intent(this, UserDatabase.class);
                startActivity(intentUD);
                return true;
            case R.id.action_userinformation:
                Intent intentUI = new Intent(this, LoggedInUserInfo.class);
                startActivity(intentUI);
                return true;
        }
        return true;
    }

    /**
     * Puts character information in UI
     */
    public void setTextViews() {
        // Set the charactername
        TextView name = findViewById(R.id.infoName);
        name.setText(character.getName());

        // Set the other textviews
        setDescription();
        setComics();
        setSeries();
        setStories();
    }

    /**
     * When description is available, set description
     */
    public void setDescription() {
        TextView description = findViewById(R.id.infoDescription);
        if (character.getDescription().length() > 0) {
            description.setText(character.getDescription());
        }
        else {
            description.setText(R.string.noavailable);
        }
    }

    /**
     * When comics are available, set comics
     */
    public void setComics() {
        TextView comics = findViewById(R.id.infoComics);
        if (character.getComics().size() > 0 ) {
            comics.setText(character.getComics().toString());
        }
        else {
            comics.setText(R.string.noavailable);
        }
    }

    /**
     * When series are available, set series
     */
    public void setSeries() {
        TextView series = findViewById(R.id.infoSeries);
        if (character.getSeries().size() > 0 ) {
            series.setText(character.getSeries().toString());
        }
        else {
            series.setText(R.string.noavailable);
        }
    }

    /**
     * When stories are available, set stories
     */
    public void setStories() {
        TextView stories = findViewById(R.id.infoStories);
        if (character.getSeries().size() > 0) {
            stories.setText(character.getStories().toString());
        }
        else {
            stories.setText(R.string.noavailable);
        }
    }

    /**
     * Set imageview with picasso
     */
    public void setImageView(){
        ImageView characterpic = findViewById(R.id.CharacterPic);
        String url = character.getSquareLargeImageURL();
        Picasso.with(getApplicationContext()).load(url).into(characterpic);
    }

    /**
     * Update the favorites in Firebase
     */
    public void updateFavorites(String userid, HashMap favorites) {
        // Get to the right place in the database
        fbdb = FirebaseDatabase.getInstance();
        dbref = fbdb.getReference("User");
        try {
            // Change the value of favorites to a list with the new item
            dbref.child("user").child(userid).child("favorites").setValue(favorites);
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
                        HashMap<String, MarvelCharacters> favorites = (HashMap<String, MarvelCharacters>) value.getValue();

                        // If there are no favorites yet, create a hashmap to store them
                        if (favorites == null){
                            favorites = new HashMap<>();
                        }

                        // Update the values of hashtable favorites
                        favorites.put(charid, character);

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
