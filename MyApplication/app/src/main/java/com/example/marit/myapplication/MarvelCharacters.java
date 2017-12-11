package com.example.marit.myapplication;

/**
 * Created by Marit on 11-12-2017.
 */

public class MarvelCharacters {
    private String name;
    private String id;
    private String imageLink;
    private String imageExt;

    public MarvelCharacters(String name, String id, String imageLink, String imageExtention) {
        this.name =  name;
        this.id = id;
        this.imageLink = imageLink;
        this.imageExt = imageExtention;
    }

    //@Override
    public String getName() {
        return name;
    }

    public String getSquareImageURL() {
        String url = imageLink + "/standard_medium." + imageExt;
        return url;
    }

    public String getId() {
        return id;
    }
}
