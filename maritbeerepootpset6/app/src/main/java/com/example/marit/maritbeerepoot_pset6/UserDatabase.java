package com.example.marit.maritbeerepoot_pset6;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        //for (String key : listviewfiller.keySet()){
        //    Log.d("filler", key);
        //}
    }

    public HashMap<String, String> getData() {
        fbdb = FirebaseDatabase.getInstance();
        dbref = fbdb.getReference("User/user/");

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("lllllllllllllllllllllll", dataSnapshot.toString());
                //String id = dataSnapshot.getKey().toString();
                for (DataSnapshot child:dataSnapshot.getChildren()){
                    String userid = child.getKey();
                    String username = dataSnapshot.child(userid).child("username").getValue().toString();
                    String email = dataSnapshot.child(userid).child("email").getValue().toString();
                    DataSnapshot favoriteskeyvalue = dataSnapshot.child(userid).child("favorites");
                    /*Log.d("HOIIIIIIIIIIIIIII", favoriteskeyvalue.toString());
                    if (favoriteskeyvalue != null) {
                        MarvelCharacters character = favoriteskeyvalue.getValue().getValue(MarvelCharacters.class);
                        Log.d("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG", character.getId().toString());
                    }*/
                    HashMap<String, MarvelCharacters> favorites = new HashMap<>();
                    for (DataSnapshot x: favoriteskeyvalue.getChildren()){
                        MarvelCharacters example = x.getValue(MarvelCharacters.class);
                        favorites.put(example.getId().toString(), example);
                        Log.d("GGGGGGGGGGGGGLLLL", x.toString());
                        Log.d("GGGGGGGGGGGGGLLLL", example.getId().toString());
                    }
                    Log.d("GGGGGGGGGGGGGGGGGG", favoriteskeyvalue.getValue().toString());
                    //HashMap<String, MarvelCharacters> favorites = (HashMap) favoriteskeyvalue.getValue();
                    userinfo user = new userinfo(userid, username, favorites, email);

                    //Log.d("qqqqqqqqqqqqqqqqqqqqqqqqqq", favorites.getValue().toString());
                    allusers.add(user);
                    putInfo(username, userid);
                    Log.d("fillernameid", username+"    "+userid);
                }
                Log.d("fillertje", UsernameUserid.toString());
                makeArrayList(UsernameUserid);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return UsernameUserid;
    }

    public void putInfo(String username, String userid) {
        UsernameUserid.put(username,userid);
        Log.d("qqqqq", allusers.toString());
    }

    public void makeArrayList(HashMap hashMap){
        ArrayList<String> users = new ArrayList<String>(hashMap.keySet());
        makeListView(users);
    }

    public void makeListView(ArrayList<String> item) {
        // Link the listview and adapter
        TextView LoadingMessage = findViewById(R.id.loadTextUser);
        ListView view = findViewById(R.id.list_viewUser);
        Log.d("tijd", "now1.7");
        adapter = new UserdatabaseAdapter(this, item);
        LoadingMessage.setVisibility(View.GONE);
        Log.d("tijd", "nowwww");
        view.setAdapter(adapter);
        view.setOnItemClickListener(new clicklistener());
    }

    private class clicklistener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int Int, long idl) {
            TextView usernameView = view.findViewById(R.id.usernameView);
            String username = usernameView.getText().toString();
            Log.d("qqqqqqq", username);
            /*for (userinfo user:allusers) {
                Log.d("qqqq2", user.toString());
                if (user.username.equals(username)){
                    Log.d("qqqqqqq", "hoii");
                    Log.d("qqqqqq", username);
                }
                break;
            }*/

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
