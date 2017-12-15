package com.example.marit.maritbeerepoot_pset6;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Creates a custom listview adapter for arrays with objects from
 * the MarvelCharacters class in it
 */

public class CharacterAdapter extends ArrayAdapter<MarvelCharacters>{
    public CharacterAdapter(Context context, ArrayList<MarvelCharacters> characters){
        super(context, 0, characters);
    }

    /**
     * Puts the information from the array into the listview
     */
    @Override
    public View getView(int position, View view, ViewGroup group) {
        MarvelCharacters character = getItem(position);

        // Inflate the layout
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.rowlayout, group, false);
        }

        // Fill the texview with the charactername
        TextView NameView = view.findViewById(R.id.nameView);
        NameView.setText(character.getName());

        // Get imageurl for setting the image
        String url = character.getSquareImageURL();
        Log.d("tijd", "now2");

        // Fill the imageview with the character picture
        final ImageView img = view.findViewById(R.id.imageView);
        Picasso.with(getContext()).load(url).into(img);

        return view;
    }

}
