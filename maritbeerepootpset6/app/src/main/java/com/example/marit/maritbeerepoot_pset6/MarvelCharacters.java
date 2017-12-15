package com.example.marit.maritbeerepoot_pset6;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Contains the information about marval characters
 */

public class MarvelCharacters implements Parcelable {
    private String name;
    private String id;
    private String imageLink;
    private String imageExt;
    private String description;
    ArrayList<String> comics;
    ArrayList<String> series;
    ArrayList<String> stories;


    /**
     * Constructs the class
     */
    public MarvelCharacters(String name, String id, String imageLink, String imageExtention, String description, ArrayList<String> comics, ArrayList<String> series, ArrayList<String> stories) {
        this.name =  name;
        this.id = id;
        this.imageLink = imageLink;
        this.imageExt = imageExtention;
        this.description = description;
        this.comics = comics;
        this.series = series;
        this.stories = stories;
    }

    /**
     * Constructs the class with an default constructor (for getting information from Firebase)
     */
    public MarvelCharacters() {

    }

    /**
     * Constructs a string that contains the image url for an medium size image of an character
     * by combining the imagelink, an format and the image extension
     */

    public String getSquareImageURL() {
        String url = imageLink + "/standard_medium." + imageExt;
        return url;
    }

    /**
     * Constructs a string that contains the image url for an very large size image of an character
     * by combining the imagelink, an format and the image extension
     */
    public String getSquareLargeImageURL() {
        String url = imageLink + "/portrait_incredible." + imageExt;
        return url;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getImageExt() {
        return imageExt;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList getComics() {
        return comics;
    }

    public ArrayList getSeries() {
        return series;
    }

    public ArrayList getStories() {
        return stories;
    }

    /**
     * Stores an instance from an class in a parcel
     */
    protected MarvelCharacters(Parcel in) {
        name = in.readString();
        id = in.readString();
        imageLink = in.readString();
        imageExt = in.readString();
        description = in.readString();
        comics = (ArrayList<String>) in.readSerializable();
        series = (ArrayList<String>) in.readSerializable();
        stories = (ArrayList<String>) in.readSerializable();
    }

    /**
     * Returns a bits. Parcebale requires a int flag, in this case its always zero.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Gets an instance from an class out of the parcel
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(imageLink);
        dest.writeString(imageExt);
        dest.writeString(description);
        dest.writeSerializable(comics);
        dest.writeSerializable(series);
        dest.writeSerializable(stories);
    }

    /**
     * Creates the parcel
     */
    public static final Parcelable.Creator<MarvelCharacters> CREATOR = new Parcelable.Creator<MarvelCharacters>() {

        /**
         * Calls constructor and returns the object
         */
        @Override
        public MarvelCharacters createFromParcel(Parcel in) {
            return new MarvelCharacters(in);
        }

        /**
         * Create an array with MarvelCharacters Objects in it
         */
        @Override
        public MarvelCharacters[] newArray(int size) {
            return new MarvelCharacters[size];
        }
    };
}
