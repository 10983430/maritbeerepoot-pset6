package com.example.marit.maritbeerepoot_pset6;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Created by Marit on 11-12-2017.
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

    public MarvelCharacters() {

    }

    public String getSquareImageURL() {
        String url = imageLink + "/standard_medium." + imageExt;
        return url;
    }

    public String getSquareLargeImageURL() {
        String url = imageLink + "/portrait_incredible." + imageExt;
        return url;
    }

    // Getters
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

    // The code below is based on generated code from http://www.parcelabler.com/
    protected MarvelCharacters(Parcel in) {
        name = in.readString();
        id = in.readString();
        imageLink = in.readString();
        imageExt = in.readString();
        description = in.readString();
        if (in.readByte() == 0x01) {
            comics = new ArrayList<String>();
            in.readList(comics, String.class.getClassLoader());
        } else {
            comics = null;
        }
        if (in.readByte() == 0x01) {
            series = new ArrayList<String>();
            in.readList(series, String.class.getClassLoader());
        } else {
            series = null;
        }
        if (in.readByte() == 0x01) {
            stories = new ArrayList<String>();
            in.readList(stories, String.class.getClassLoader());
        } else {
            stories = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(imageLink);
        dest.writeString(imageExt);
        dest.writeString(description);
        if (comics == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(comics);
        }
        if (series == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(series);
        }
        if (stories == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(stories);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MarvelCharacters> CREATOR = new Parcelable.Creator<MarvelCharacters>() {
        @Override
        public MarvelCharacters createFromParcel(Parcel in) {
            return new MarvelCharacters(in);
        }

        @Override
        public MarvelCharacters[] newArray(int size) {
            return new MarvelCharacters[size];
        }
    };
    // End generated code
}
