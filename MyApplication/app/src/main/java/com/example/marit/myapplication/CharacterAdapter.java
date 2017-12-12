package com.example.marit.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Marit on 11-12-2017.
 */

public class CharacterAdapter extends ArrayAdapter<MarvelCharacters>{
    public CharacterAdapter(Context context, ArrayList<MarvelCharacters> characters){
        super(context, 0, characters);
    }

    @Override
    /*public View newView(View view, Context context, ViewGroup group) {
        return LayoutInflater.from(context).inflate(R.layout.rowlayout, group, false);
    }*/

    public View getView(int position, View view, ViewGroup group) {
        MarvelCharacters character = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.rowlayout, group, false);
        }
        TextView NameView = view.findViewById(R.id.nameView);
        NameView.setText(character.getName());
        String url = character.getSquareImageURL();
        Log.d("tijd", "now2");
        final ImageView img = view.findViewById(R.id.imageView);
        /*RequestQueue queue = Volley.newRequestQueue(getContext());
        final ImageView img = view.findViewById(R.id.imageView);
        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                img.setImageBitmap(response);
            }
        }, 0, 0, null, null);
        queue.add(ir);*/
        Picasso.with(getContext()).load(url).into(img);

        return view;
    }

}
