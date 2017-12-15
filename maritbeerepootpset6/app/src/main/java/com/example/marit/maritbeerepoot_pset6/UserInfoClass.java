package com.example.marit.maritbeerepoot_pset6;

import java.util.HashMap;

/**
 * Contains the user information
 */
public class UserInfoClass {
    public String id;
    public String username;
    public HashMap favorites;
    public String email;

    /**
     * Constructs the class
     */
    public UserInfoClass(String id, String username, HashMap favorites, String email) {
        this.username = username;
        this.favorites = favorites;
        this.email = email;
        this.id = id;
    }

    /**
     * Constructs the class with an default constructor (for getting information from Firebase)
     */
    public UserInfoClass() {

    }
}
