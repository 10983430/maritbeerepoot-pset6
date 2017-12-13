package com.example.marit.maritbeerepoot_pset6;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDatabase extends AppCompatActivity {
    FirebaseDatabase fbdb;
    DatabaseReference dbref;
    HashMap<String, String> listviewfiller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_database);
        listviewfiller = new HashMap<String, String>();
        HashMap data = getData();
        Log.d("filler", listviewfiller.toString());
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

                for (DataSnapshot child:dataSnapshot.getChildren()){
                    String userid = child.getKey();
                    String username = dataSnapshot.child(userid).child("username").getValue().toString();
                    //listviewfiller.put(username, userid);
                    putInfo(username, userid);
                    Log.d("fillernameid", username+"    "+userid);
                }
                Log.d("fillertje", listviewfiller.toString());
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return listviewfiller;
    }

    public void putInfo(String username, String userid) {
        listviewfiller.put(username,userid);
    }
}
