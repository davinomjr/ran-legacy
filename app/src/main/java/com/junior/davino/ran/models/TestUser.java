package com.junior.davino.ran.models;

import org.parceler.Parcel;

/**
 * Created by davin on 26/03/2017.
 */

@Parcel
public class TestUser {

    String userId;
    String name;
    String schoolGrade;
    String gender;
    int age;
    TestUserParent parent;


    public TestUserParent getParent() {
        return parent;
    }

    public void setParent(TestUserParent parent) {
        this.parent = parent;
    }


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

    public String getSchoolGrade() {
        return schoolGrade;
    }

    public void setSchoolGrade(String schoolGrade) {
        this.schoolGrade = schoolGrade;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public TestUser(){
        parent = new TestUserParent();
    }


}
