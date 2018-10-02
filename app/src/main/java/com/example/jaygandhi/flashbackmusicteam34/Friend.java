package com.example.jaygandhi.flashbackmusicteam34;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.api.services.people.v1.model.Person;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by thinmintz17 on 3/14/18.
 */

public class Friend implements Serializable{
    private String name;
    private String email;


    public Friend(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

}
