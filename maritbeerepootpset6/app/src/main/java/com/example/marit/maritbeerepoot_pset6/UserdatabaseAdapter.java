package com.example.marit.maritbeerepoot_pset6;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Creates a custom listview adapter for an array with the usernames in it
 */
public class UserdatabaseAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> user;

    public UserdatabaseAdapter(Context context, ArrayList<String> listofusers) {
        super(context, 0, listofusers);
        this.context = context;
        this.user = listofusers;
    }

    @Override
    public View getView(int position, View view, ViewGroup group) {
        //Inflate the layout and set the views with information from the array
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.rowlayoutuserdatabase, null);
        TextView username = view.findViewById(R.id.usernameView);
        username.setText(user.get(position));

        return view;
    }
}

