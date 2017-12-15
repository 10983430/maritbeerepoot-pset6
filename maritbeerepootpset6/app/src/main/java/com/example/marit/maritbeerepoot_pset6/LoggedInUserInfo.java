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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Get user information from Firebase and shows this in combination with a logout button the the user. When user is not logged in it shows a login button.
 */
public class LoggedInUserInfo extends AppCompatActivity {
    private FirebaseUser user;
    public ArrayList<MarvelCharacters> usersFavorites = new ArrayList<>();
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_user_info);
        user = FirebaseAuth.getInstance().getCurrentUser();
        updateUI();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflaterLog = getMenuInflater();
        inflaterLog.inflate(R.menu.actions, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_characterdatabase:
                Intent intentCD = new Intent(LoggedInUserInfo.this, CharacterDatabase.class);
                startActivity(intentCD);
                return true;
            case R.id.action_userdatabase:
                Intent intentUD = new Intent(LoggedInUserInfo.this, UserDatabase.class);
                startActivity(intentUD);
                return true;
            case R.id.action_userinformation:
                Toast.makeText(this, "You are already seeing your user info!", Toast.LENGTH_SHORT).show();
                return true;
        }
        return true;
    }

    /**
     * Updates the database UI on whether a user is logged in or not
     */
    public void updateUI() {
        if (user == null) {
            updateWhenLoggedIn();
        } else {
            updateNotLoggedIn();
        }
    }

    /**
     * Hides and pops up information when the user is logged in
     */
    public void updateWhenLoggedIn() {
        // When there is no user logged in, there is no information to display, so show the user a login button and hide the views
        TextView UserInformationView = findViewById(R.id.UserInformationView);
        TextView UserFavoritesView = findViewById(R.id.UserFavoritesView);
        Button loginbuttonUI = findViewById(R.id.loginbuttonUI);

        UserInformationView.setVisibility(View.GONE);
        UserFavoritesView.setVisibility(View.GONE);
        loginbuttonUI.setVisibility(View.VISIBLE);

        loginbuttonUI.setOnClickListener(new click());
    }

    public void updateNotLoggedIn() {
        // When logged in, this is the place where you can log out, so show the button
        Button logoutbuttonUI = findViewById(R.id.logoutbuttonUI);
        logoutbuttonUI.setVisibility(View.VISIBLE);
        logoutbuttonUI.setOnClickListener(new click());

        // Also when a user is logged in, display the userinformation here
        userid = user.getUid();
        getData();
    }

    private class click implements View.OnClickListener {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loginbuttonUI:
                    // Send user to login activity when pressing login
                    Intent intentlogin = new Intent(LoggedInUserInfo.this, Login.class);
                    startActivity(intentlogin);
                    break;

                case R.id.logoutbuttonUI:
                    // Sign out the user and send him to the startup activity
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(LoggedInUserInfo.this, StartUp.class);
                    startActivity(intent);
                    finish();
                    break;

            }
        }
    }

    /**
     * Gets user data from Firebase
     */
    public void getData() {
        // Set the database references
        FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
        DatabaseReference dbref = fbdb.getReference("User/user/"+userid);

        // Get information from firebase with an listener
        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange (DataSnapshot dataSnapshot){
            // Go through the database to get the username and email
            String username = dataSnapshot.child("username").getValue().toString();
            String email = dataSnapshot.child("email").getValue().toString();

            // Get favorites and add them to a HashMap
            DataSnapshot favoriteskeyvalue = dataSnapshot.child("favorites");
            HashMap<String, MarvelCharacters> favorites = new HashMap<>();
            for (DataSnapshot shot: favoriteskeyvalue.getChildren()){
                MarvelCharacters character = shot.getValue(MarvelCharacters.class);
                favorites.put(character.getId().toString(), character);
            }
            // Create new instance of class user and send it to the handle information methode
            UserInfoClass user = new UserInfoClass(userid, username, favorites, email);
            handleUserInfo(user);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Log error
            Log.d("Database error", databaseError.toString());
            }
        });
    }

    /**
     * Uses the data from the class UserInfoClass to fill out the information about the user
     */
    public void handleUserInfo(UserInfoClass user){
        // Set the textviews
        String username = user.username;
        TextView usernameLogged = findViewById(R.id.usernameLogged);
        usernameLogged.setText(getString(R.string.usernameplaceholder) + username);
        String email = user.email;
        TextView useremailLogged = findViewById(R.id.useremailLogged);
        useremailLogged.setText(getString(R.string.useremailplaceholder)+ email + " (Only you can see your email!)");
        TextView useridLogged = findViewById(R.id.useridLogged);
        useridLogged.setText(getString(R.string.useridplaceholder) + userid);

        // Convert de favorites hashtable into an Arraylist
        HashMap<String, MarvelCharacters> favorites = user.favorites;
        if (favorites.size() != 0){
            for (MarvelCharacters value: favorites.values()){
                usersFavorites.add(value);
            }
            // Make listview from the Arraylist
            makeListView(usersFavorites);
        }
    }

    /**
     * Creates listview from arraylist
     */
    public void makeListView(ArrayList<MarvelCharacters> item) {
        // Link the listview and adapter
        CharacterAdapter adapter;
        ListView view = findViewById(R.id.list_viewLoggedin);
        adapter = new CharacterAdapter(this, item);
        view.setAdapter(adapter);
    }

}
