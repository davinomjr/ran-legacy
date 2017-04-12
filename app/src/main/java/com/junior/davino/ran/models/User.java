package com.junior.davino.ran.models;

import org.parceler.Parcel;

/**
 * Created by davin on 28/03/2017.
 */

@Parcel
public class User {
    String userId;
    String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
