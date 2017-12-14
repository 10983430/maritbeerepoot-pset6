package com.example.marit.maritbeerepoot_pset6;

import android.app.Activity;
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
import java.util.List;

/**
 * Created by Marit on 14-12-2017.
 */

public class UserdatabaseAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> user;
    public UserdatabaseAdapter(Context context, ArrayList<String> listofusers){
        super(context, 0, listofusers);
        this.context = context;
        this.user = listofusers;
    }

    /*@Override
    public View newView(int position, View view, ViewGroup group){
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.rowlayoutuserdatabase, group, false);
        }
        return LayoutInflater.from(getContext()).inflate(R.layout.rowlayoutuserdatabase, group, false);
    }

    public void bindView
    TextView username = view.findViewById(R.id.usernameView);
}*/
    @Override
    public View getView(int position, View view, ViewGroup group) {
        Context context = getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View lastview = inflater.inflate(R.layout.rowlayoutuserdatabase, null);
        TextView username = lastview.findViewById(R.id.usernameView);
        username.setText(user.get(position));

        return lastview;
    }
}

/*
public class CharacterAdapter extends ArrayAdapter<MarvelCharacters> {
    public CharacterAdapter(Context context, ArrayList<MarvelCharacters> characters){
        super(context, 0, characters);
    }

    @Override
    public View getView(int position, View view, ViewGroup group) {
        // Get the position of the item that was clicked
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

}*/