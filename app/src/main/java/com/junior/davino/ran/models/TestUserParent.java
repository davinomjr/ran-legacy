package com.junior.davino.ran.models;

import org.parceler.Parcel;

/**
 * Created by davin on 03/04/2017.
 */

@Parcel
public class TestUserParent {

    public TestUserParent(){}

    String name;
    String email;
    String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
