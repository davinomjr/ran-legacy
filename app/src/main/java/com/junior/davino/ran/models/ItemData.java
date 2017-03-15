package com.junior.davino.ran.models;

import java.io.Serializable;

/**
 * Created by davin on 14/03/2017.
 */

public class ItemData implements Serializable {

    private String title;
    private int imageUrl;

    public ItemData(String title,int imageUrl){

        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getImageUrl() {
        return imageUrl;
    }
}