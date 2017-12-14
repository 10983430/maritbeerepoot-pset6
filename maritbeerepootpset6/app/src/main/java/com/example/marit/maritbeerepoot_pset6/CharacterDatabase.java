package com.example.marit.maritbeerepoot_pset6;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class CharacterDatabase extends AppCompatActivity {
    public ArrayList<MarvelCharacters> items = new ArrayList<>();
    private CharacterAdapter adapter;
    String idM;
    String imagelinkM;
    String imgexM;
    String descriptionM;
    ArrayList<String> comicsM;
    ArrayList<String> seriesM;
    ArrayList<String> storiesM;
    ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_database);

        // Create the hash and ts needed for the url
        String hash = getHash();
        String ts = getTimestamp();

        // When no search request is made, fill the listview with the first 100 items from the database
        // The marvel API requires a hash, made up out of 2 keys and an timestamp
        String url = "https://gateway.marvel.com/v1/public/characters?limit=100&ts=" + ts + "&apikey=4e73b5e53ed10cced509822314fc10a4&hash=" + hash;

        // Generate listview data
        getData(url);

        // Add listener to the search button
        Button search = findViewById(R.id.searchButton);
        search.setOnClickListener(new Click());

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
                Toast.makeText(this, "You are already in the character database!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_userdatabase:
                Intent intentUD = new Intent(CharacterDatabase.this, UserDatabase.class);
                startActivity(intentUD);
                return true;
            case R.id.action_userinformation:
                Intent intentUI = new Intent(CharacterDatabase.this, LoggedInUserInfo.class);
                startActivity(intentUI);
                return true;
        }
        return true;
    }

    public void getData(String url) {
        // Create new queue
        RequestQueue RQ = Volley.newRequestQueue(getApplicationContext());
        Log.d("Testttttt", "hoi");

        // Create new stringrequest (Volley)
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Let the user know that the data is loading by a loading message
                            // Parse JSON to arraylist
                            getTextJSON(response.toString());
                            Log.d("tijd", "now");
                            makeListView(items);

                            // Refresh the view (this makes the response time a bit shorter, because
                            // it is done in the try, and not after)
                            ListView view = findViewById(R.id.list_view);
                            view.invalidateViews();

                            // Remove the loading message
                            TextView load = findViewById(R.id.loadText);
                            load.setVisibility(View.GONE);

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
        Log.d("tijd", "now1.5");

        // Set listener on listview
        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(new clicklistener());
    }

    private class Click implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            // Get the input of the search field
            EditText searchinputfield = findViewById(R.id.Search);
            String searchinput = searchinputfield.getText().toString();

            // Put back the start list when searchinput is empty
            if (searchinput.length() == 0) {

                // Create the hash and timestamp
                String hash = getHash();
                String ts = getTimestamp();

                // Create url
                String url = "https://gateway.marvel.com/v1/public/characters?limit=100&ts=" + ts + "&apikey=4e73b5e53ed10cced509822314fc10a4&hash=" + hash;

                // Call method to refill listview
                fillListWhenSearching(url);
            }
            // Execute the search when the input is not empty
            else {

                // Create the hash and timestamp
                String hash = getHash();
                String ts = getTimestamp();

                // Create url
                String url = "https://gateway.marvel.com/v1/public/characters?nameStartsWith=" + searchinput + "&ts=" + ts + "&apikey=4e73b5e53ed10cced509822314fc10a4&hash=" + hash;

                // Call method to refill listview
                fillListWhenSearching(url);

            }
        }
    }

    public void fillListWhenSearching(String url){
        // Let the user know the information is loading
        TextView load = findViewById(R.id.loadText);
        load.setVisibility(View.VISIBLE);

        // Make list that fills the listview empty
        items = new ArrayList<>();

        // Get the data
        getData(url);

        // Refresh the listview
        ListView filledview = findViewById(R.id.list_view);
        filledview.invalidateViews();
    }

    public void makeListView(ArrayList<MarvelCharacters> item) {
        // Link the listview and adapter
        ListView view = findViewById(R.id.list_view);
        Log.d("tijd", "now1.7");
        adapter = new CharacterAdapter(this, item);
        Log.d("tijd", "nowwww");
        view.setAdapter(adapter);
    }

    // Configure the listener
    private class clicklistener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int Int, long idl) {
            // Find the item that was clicked on
            TextView nameView = view.findViewById(R.id.nameView);
            String nameM = nameView.getText().toString();

            // Get the ID of the item that was clicked on
            for (MarvelCharacters M:items) {
                if (nameM.equals(M.getName())){
                    idM = M.getId();
                    imagelinkM = M.getImageLink();
                    imgexM = M.getImageExt();
                    descriptionM = M.getDescription();
                    comicsM = M.getComics();
                    seriesM = M.getSeries();
                    storiesM = M.getStories();
                    break;
                }
            }
            // Go to the character page to see the information about the clicked item
            Intent intent = new Intent(CharacterDatabase.this, CharInfo.class);
            MarvelCharacters character = new MarvelCharacters(nameM, idM, imagelinkM, imgexM, descriptionM, comicsM, seriesM, storiesM);
            intent.putExtra("Character", character);
            startActivity(intent);

        }
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
                String description = results.getJSONObject(i).getString("description");

                // Get the comics
                ArrayList<String> comics = new ArrayList<>();
                JSONArray comicinfo = results.getJSONObject(i).getJSONObject("comics").getJSONArray("items");
                for (int x = 0; x < comicinfo.length(); x++) {
                    comics.add(comicinfo.getJSONObject(x).getString("name").toString());
                }

                // Get the series
                ArrayList<String> series = new ArrayList<>();
                JSONArray seriesinfo = results.getJSONObject(i).getJSONObject("series").getJSONArray("items");
                for (int x = 0; x < seriesinfo.length(); x++) {
                    series.add(comicinfo.getJSONObject(x).getString("name").toString());
                }

                // Get the stories:
                ArrayList<String> stories = new ArrayList<>();
                JSONArray storiesinfo = results.getJSONObject(i).getJSONObject("stories").getJSONArray("items");
                for (int x = 0; x < storiesinfo.length(); x++) {
                    stories.add(storiesinfo.getJSONObject(x).getString("name").toString());
                }

                // Make a arraylist consisting of 'class' instances
                items.add(new MarvelCharacters(name, id, pathimg, extention, description, comics, series, stories));
                Log.d("proooooo", name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getHash() {
        // Get the timestamp
        String ts = getTimestamp();

        // Get the string that need to be hashed
        String unhash = createUnhash(ts);

        // Hash the string
        String hash = MD5_Hash(unhash);
        return hash;
    }

    public static String getTimestamp() {
        // Get the timestamp
        Long timestamp = System.currentTimeMillis()/1000;
        String ts = timestamp.toString();
        return ts;
    }

    public static String createUnhash(String timestamp){
        // Get the keys
        String pub = "4e73b5e53ed10cced509822314fc10a4";
        String pri = "15bacc1beec22249ce5e2ad8c3c78479dc2552d6";

        // Create the string that need to be hashed
        String unhash = timestamp + pri + pub;
        return unhash;
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
