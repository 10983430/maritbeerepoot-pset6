package com.example.marit.maritbeerepoot_pset6;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creates a listview of all the usernames (out of firebase) of the users
 */
public class UserDatabase extends AppCompatActivity {
    private HashMap<String, String> UsernameUserid;
    private HashMap<String,String> favorites;
    private ArrayList<UserInfoClass> allusers = new ArrayList<UserInfoClass>();
    private String id;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_database);
        checkConnectivity();
        UsernameUserid = new HashMap<>();
        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflaterUD = getMenuInflater();
        inflaterUD.inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_characterdatabase:
                Intent intentUD = new Intent(UserDatabase.this, CharacterDatabase.class);
                startActivity(intentUD);
                return true;
            case R.id.action_userdatabase:
                Toast.makeText(this, "You are already in the user database!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_userinformation:
                Intent intentUI = new Intent(UserDatabase.this, LoggedInUserInfo.class);
                startActivity(intentUI);
                return true;
        }
        return true;
    }

    /**
     * Checks if user is connected to the internet, if not it notifies the user and sends him to the starup screen
     */
    public void checkConnectivity() {
        Context context = getApplicationContext();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null){
            Toast.makeText(context, "You are not connected to the internet, please try again after connecting", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, StartUp.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * Gets the data about the users from firebase
     */
    public void getData() {
        // Set database references
        FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
        DatabaseReference dbref = fbdb.getReference("User/user/");

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Loop through firebase to get the needed information
                for (DataSnapshot child:dataSnapshot.getChildren()){
                    String userid = child.getKey();
                    String username = dataSnapshot.child(userid).child("username").getValue().toString();
                    String email = dataSnapshot.child(userid).child("email").getValue().toString();

                    // Get favorites
                    DataSnapshot favoriteskeyvalue = dataSnapshot.child(userid).child("favorites");
                    HashMap<String, MarvelCharacters> favorites = new HashMap<>();
                    for (DataSnapshot x: favoriteskeyvalue.getChildren()){
                        MarvelCharacters example = x.getValue(MarvelCharacters.class);
                        favorites.put(example.getId().toString(), example);
                    }

                    // Create new instance of class UserInfoClass with the gathered information
                    UserInfoClass user = new UserInfoClass(userid, username, favorites, email);

                    // Add user to alluser and hashmap with names and IDs
                    allusers.add(user);
                    UsernameUserid.put(username,userid);
                }
                makeListView(UsernameUserid);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Database or connectivity error", databaseError.toString());
            }
        });
    }

    /**
     * Creates listview from arraylist by first getting the needed information from the hasmap
     */
    public void makeListView(HashMap hashMap) {
        ArrayList<String> users = new ArrayList<String>(hashMap.keySet());

        // Link the listview and adapter
        ListView view = findViewById(R.id.list_viewUser);
        UserdatabaseAdapter adapter;
        adapter = new UserdatabaseAdapter(this, users);

        // Remove the loading message
        TextView LoadingMessage = findViewById(R.id.loadTextUser);
        LoadingMessage.setVisibility(View.GONE);
        view.setAdapter(adapter);

        // Making clicking on an item in the listview possible
        view.setOnItemClickListener(new clicklistener());
    }

    private class clicklistener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int Int, long idl) {
            // Get the username
            TextView usernameView = view.findViewById(R.id.usernameView);
            String username = usernameView.getText().toString();

            // Loop trough all of the users to get the user matching the username/clicked item and get his information
            for (int i = 0; i < allusers.size(); i++) {
                if (allusers.get(i).username.toString().equals(username)){
                    id = allusers.get(i).id.toString();
                    email = allusers.get(i).email.toString();
                    favorites = allusers.get(i).favorites;
                }
            }

            // Navigate user
            goToDetails(username);
        }
    }

    /**
     * Puts information in intent and navigates the user to the page with details about the user
     */
    public void goToDetails(String username) {
        Intent intent = new Intent(UserDatabase.this, UserInformation.class);
        intent.putExtra("username", username);
        intent.putExtra("id", id);
        intent.putExtra("email", email);
        intent.putExtra("favorites", favorites);
        startActivity(intent);
    }

}
