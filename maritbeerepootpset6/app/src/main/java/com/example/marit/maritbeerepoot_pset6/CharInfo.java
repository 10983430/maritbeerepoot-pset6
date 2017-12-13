package com.example.marit.maritbeerepoot_pset6;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CharInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_info);

        // Get ID from intent
        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        MarvelCharacters character = getIntent().getExtras().getParcelable("Character");

        TextView name = findViewById(R.id.infoName);
        name.setText(character.getName());
        TextView description = findViewById(R.id.infoDescription);
        description.setText(character.getDescription());
        TextView comics = findViewById(R.id.infoComics);
        comics.setText(character.getComics().toString());
        TextView series = findViewById(R.id.infoSeries);
        series.setText(character.getSeries().toString());
        TextView stories = findViewById(R.id.infoStories);
        stories.setText(character.getStories().toString());

        //getdata(id);
        ImageView characterpic = findViewById(R.id.CharacterPic);
        String url = character.getSquareLargeImageURL();
        Picasso.with(getApplicationContext()).load(url).into(characterpic);
    }

    public void getdata(String id) {

    }
}
