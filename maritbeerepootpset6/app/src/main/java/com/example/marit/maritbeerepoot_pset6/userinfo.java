package com.example.marit.maritbeerepoot_pset6;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Marit on 13-12-2017.
 */

public class userinfo {
    public String id;
    public String username;
    public HashMap favorites;
    public String email;

    public userinfo(String id, String username, HashMap favorites, String email) {
        this.username = username;
        this.favorites = favorites;
        this.email = email;
        this.id = id;
    }
}
