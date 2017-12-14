package com.example.marit.maritbeerepoot_pset6;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.List;

public class UserDatabase extends AppCompatActivity {
    private UserdatabaseAdapter adapter;
    FirebaseDatabase fbdb;
    DatabaseReference dbref;
    HashMap<String, String> UsernameUserid;
    ArrayList<userinfo> allusers = new ArrayList<userinfo>();
    String id;
    String email;
    HashMap<String,String> favorites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_database);
        UsernameUserid = new HashMap<String, String>();
        HashMap data = getData();
        Log.d("filler", UsernameUserid.toString());
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

    public HashMap<String, String> getData() {
        fbdb = FirebaseDatabase.getInstance();
        dbref = fbdb.getReference("User/user/");

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child:dataSnapshot.getChildren()){
                    String userid = child.getKey();
                    String username = dataSnapshot.child(userid).child("username").getValue().toString();
                    String email = dataSnapshot.child(userid).child("email").getValue().toString();
                    DataSnapshot favoriteskeyvalue = dataSnapshot.child(userid).child("favorites");
                    HashMap<String, MarvelCharacters> favorites = new HashMap<>();
                    for (DataSnapshot x: favoriteskeyvalue.getChildren()){
                        MarvelCharacters example = x.getValue(MarvelCharacters.class);
                        favorites.put(example.getId().toString(), example);
                    }
                    userinfo user = new userinfo(userid, username, favorites, email);
                    allusers.add(user);
                    putInfo(username, userid);
                }
                makeArrayList(UsernameUserid);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Database error", databaseError.toString());
            }
        });
        return UsernameUserid;
    }

    public void putInfo(String username, String userid) {
        UsernameUserid.put(username,userid);
    }

    public void makeArrayList(HashMap hashMap){
        ArrayList<String> users = new ArrayList<String>(hashMap.keySet());
        makeListView(users);
    }

    public void makeListView(ArrayList<String> item) {
        // Link the listview and adapter
        ListView view = findViewById(R.id.list_viewUser);
        adapter = new UserdatabaseAdapter(this, item);

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
            TextView usernameView = view.findViewById(R.id.usernameView);
            String username = usernameView.getText().toString();

            for (int i = 0; i < allusers.size(); i++){
                if (allusers.get(i).username.toString().equals(username)){
                    id = allusers.get(i).id.toString();
                    email = allusers.get(i).email.toString();
                    favorites = allusers.get(i).favorites;
                }
            }

            Intent intent = new Intent(UserDatabase.this, UserInformation.class);
            intent.putExtra("username", username);
            intent.putExtra("id", id);
            intent.putExtra("email", email);
            intent.putExtra("favorites", favorites);
            startActivity(intent);
        }
    }

}
