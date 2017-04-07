package com.junior.davino.ran.models;

import org.parceler.Parcel;

import java.io.Serializable;

/**
 * Created by davin on 24/02/2017.
 */

@Parcel
public class TestItem implements Serializable {

    private int codeTestNumber;
    private int position;
    private String name;
    private boolean result;

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCodeTestNumber() {
        return codeTestNumber;
    }

    public void setCodeTestNumber(int codeTestNumber) {
        this.codeTestNumber = codeTestNumber;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
