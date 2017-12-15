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

public class LoggedInUserInfo extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public ArrayList<MarvelCharacters> usersFavorites = new ArrayList<>();
    FirebaseDatabase fbdb;
    DatabaseReference dbref;
    String userid;
    CharacterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_user_info);
        TextView UserInformationView = findViewById(R.id.UserInformationView);
        TextView UserFavoritesView = findViewById(R.id.UserFavoritesView);

        if (user == null) {
            // When there is no user logged in, there is no information to display, so show the user a login button
            Button loginbuttonUI = findViewById(R.id.loginbuttonUI);
            loginbuttonUI.setVisibility(View.VISIBLE);
            UserFavoritesView.setVisibility(View.GONE);
            UserInformationView.setVisibility(View.GONE);
            loginbuttonUI.setOnClickListener(new click());
        } else {
            // When logged in, this is the place where you can log out, so show the button
            Button logoutbuttonUI = findViewById(R.id.logoutbuttonUI);
            logoutbuttonUI.setVisibility(View.VISIBLE);
            logoutbuttonUI.setOnClickListener(new click());

            // Also when a user is logged in, display the userinformation here
            userid = user.getUid();
            getData();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
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


    private class click implements View.OnClickListener {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loginbuttonUI:
                    // Send user to login activity when pressing login
                    Intent intentlogin = new Intent(LoggedInUserInfo.this, Login.class);
                    Log.d("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG", "sdfsdfsd");
                    startActivity(intentlogin);
                case R.id.logoutbuttonUI:
                    // Log the user out and send him to the startup activity
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(LoggedInUserInfo.this, StartUp.class);
                    startActivity(intent);
                    finish();
                    break;

            }
        }
    }


    public void getData() {

        // Set the database references
        fbdb = FirebaseDatabase.getInstance();
        dbref = fbdb.getReference("User/user/"+userid);

        // Get information from firebase
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
            // Create new instance of class user
            userinfo user = new userinfo(userid, username, favorites, email);
            handleUserInfo(user);
        }

        @Override
        // Log error
        public void onCancelled(DatabaseError databaseError) {
            Log.d("Database error", databaseError.toString());
            }
        });
    }


    public void handleUserInfo(userinfo user){
        // Set the textviews
        String username = user.username;
        TextView usernameLogged = findViewById(R.id.usernameLogged);
        usernameLogged.setText("Username = " + username);
        String email = user.email;
        TextView useremailLogged = findViewById(R.id.useremailLogged);
        useremailLogged.setText("Email = "+ email + " (Only you can see your email!)");
        TextView useridLogged = findViewById(R.id.useridLogged);
        useridLogged.setText("Userid = " + userid);

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


    public void makeListView(ArrayList<MarvelCharacters> item) {
        // Link the listview and adapter
        ListView view = findViewById(R.id.list_viewLoggedin);
        adapter = new CharacterAdapter(this, item);
        view.setAdapter(adapter);
    }
}
