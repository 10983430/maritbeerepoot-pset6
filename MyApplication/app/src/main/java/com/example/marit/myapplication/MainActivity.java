package com.example.marit.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public ArrayList<MarvelCharacters> items = new ArrayList<>();
    Map<String, String> map = new HashMap<String, String>();
    private CharacterAdapter adapter;
    String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the timestamp
        Long timestamp = System.currentTimeMillis()/1000;
        String ts = timestamp.toString();

        // Set listener on listview
        ListView listView = findViewById(R.id.ListView);
        listView.setOnItemClickListener(new clicklistener());
        // Get the string that need to be hashed
        String unhash = createUnhash(ts);

        // Hash the string
        String hash = MD5_Hash(unhash);
        Log.d("hash", hash);

        // Create new queue
        RequestQueue RQ = Volley.newRequestQueue(getApplicationContext());

        // The marvel API requires a hash, made up out of 2 keys and an timestamp
        String url = "https://gateway.marvel.com/v1/public/characters?limit=100&ts=" + ts + "&apikey=4e73b5e53ed10cced509822314fc10a4&hash=" + hash;

        // Add to queue
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Parse JSON to arraylist

                            getTextJSON(response.toString());
                            Log.d("tijd", "now");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RQ.add(stringRequest);
        makeListView(items);
        Log.d("tijd", "now1.5");
    }

    public void makeListView(ArrayList<MarvelCharacters> item) {
        ListView view = findViewById(R.id.ListView);
        Log.d("tijd", "now1.7");
        adapter = new CharacterAdapter(this, item);
        Log.d("tijd", "nowwww");
        view.setAdapter(adapter);

    }

    public void getTextJSON(String string) throws JSONException {
        try {
            // Create a new JSONObject and push the data into it
            JSONObject object = new JSONObject(string);
            JSONObject data = object.getJSONObject("data");

            // Skip the header and get the information itself from the JSON
            JSONArray results = data.getJSONArray("results");

            // Loop through JSONArray to add the items to an Arraylist
            for (int i = 0; i < results.length(); i++) {

                // Get the name, id, image path and image extention
                String name = results.getJSONObject(i).getString("name");
                String id = results.getJSONObject(i).getString("id");
                String pathimg = results.getJSONObject(i).getJSONObject("thumbnail").getString("path");
                String extention = results.getJSONObject(i).getJSONObject("thumbnail").getString("extension");

                // Make a arraylist consisting of 'class' instances
                items.add(new MarvelCharacters(name, id, pathimg, extention));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static String createUnhash(String timestamp){
        // Get the keys
        String pub = "4e73b5e53ed10cced509822314fc10a4";
        String pri = "15bacc1beec22249ce5e2ad8c3c78479dc2552d6";

        // Create the string that need to be hashed
        String unhash = timestamp + pri + pub;
        return unhash;
    }

    // Configure the listener
    private class clicklistener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int Int, long idl) {
            // Find the item that was clicked on
            TextView nameView = view.findViewById(R.id.nameView);
            String name = nameView.getText().toString();

            // Get the ID of the item that was clicked on
            for (MarvelCharacters M:items) {
                if (name.equals(M.getName())){
                    id = M.getId();
                    break;
                }
            }

            // Go to the character page to see the information about the clicked item
            Intent intent = new Intent(getApplicationContext(), CharInfo.class);
            intent.putExtra("ID", id);
            startActivity(intent);

        }
    }


    // The following snippet of code is based on code from: https://www.mail-archive.com/android-beginners@googlegroups.com/msg18680.html
    // I didn't wrote this myself, because I have no idea how MD5 works, but really wanted to use the Marvel API
    // This function hashes the string into md5
    public static String MD5_Hash(String s) {
        MessageDigest m = null;
        try {
            // Get de MD5 method
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // Hash the string
        m.update(s.getBytes(),0,s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }
    // End snippet code


}
