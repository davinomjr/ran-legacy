package com.junior.davino.ran.models;

import java.io.Serializable;

/**
 * Created by davin on 24/02/2017.
 */

public class Item implements Serializable {
    private int orderNumber;
    private int position;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
