package com.example.marit.maritbeerepoot_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Converts the user information to the UI
 */
public class UserInformation extends AppCompatActivity {
    public ArrayList<MarvelCharacters> usersFavorites = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        // Get all the information passed by the intent
        Intent intent = getIntent();
        Bundle intentextra = getIntent().getExtras();
        String username = intentextra.getString("username");
        String id = intentextra.getString("id");

        setViews(username, id);

        // Try to make a listview by getting the favorites
        // When a user didn't add any favorites yet, the hashmap will be a null object
        if (intent.getSerializableExtra("favorites") != null){
            // Get favorites Hashmap from intent
            HashMap<String, MarvelCharacters> favorites = (HashMap<String, MarvelCharacters>) intent.getSerializableExtra("favorites");

            // Loop through the favorites to create an arraylist for input listadapter
            for (MarvelCharacters value: favorites.values()){
                usersFavorites.add(value);
            }

            if (usersFavorites.size() != 0) {
                // Remove the warning that a user didn't add anything to favorites yet
                TextView FavoritesView = findViewById(R.id.FavoritesView);
                FavoritesView.setVisibility(View.GONE);

                // Make the list view
                makeListView(usersFavorites);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflaterUI = getMenuInflater();
        inflaterUI.inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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
     * Fills the textviews with the id and username
     */
    public void setViews(String username, String id) {
        // Display the username and the userid
        TextView usernameholder = findViewById(R.id.UsernameView);
        TextView useridholder = findViewById(R.id.UseridView);
        usernameholder.setText(getString(R.string.usernameplaceholder) + username);
        useridholder.setText(getString(R.string.useridplaceholder) + id);
    }

    /**
     * Creates listview from arraylist
     */
    public void makeListView(ArrayList<MarvelCharacters> item) {
        // Link the listview and
        CharacterAdapter adapter;
        ListView view = findViewById(R.id.list_viewfavorites);
        adapter = new CharacterAdapter(this, item);
        view.setAdapter(adapter);
    }
}
