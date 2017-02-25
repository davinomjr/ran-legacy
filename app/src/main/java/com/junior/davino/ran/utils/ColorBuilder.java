package com.junior.davino.ran.utils;

import android.graphics.Color;

import com.junior.davino.ran.models.Item;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by davin on 24/02/2017.
 */

public class ColorBuilder {

    public static ArrayList<Item> createListOfColors(int numberOfItems){

        ArrayList<Item> items = new ArrayList<Item>();

        int actualColor,previousColor;;

        for(int i = 0; i < numberOfItems; i++){
            previousColor = 0;
            Item item = new Item();
            if(items.size() > 0 && i > 0){
                previousColor = items.get(i - 1).getOrderNumber();
            }
            do {
                actualColor = generateRandomColor(item);
            }while(actualColor == previousColor);
            item.setOrderNumber(actualColor);
            item.setPosition(1);
            items.add(item);
        }

        return items;
    }

    private static int generateRandomColor(Item item){
        Random random = new Random();
        int colorCode = random.nextInt(5 - 1 + 1) + 1;
        return getColor(colorCode, item);
    }


    private static int getColor(int colorCode, Item item){
        if(colorCode == 1){
            item.setName("Vermelho");
            return Color.RED;
        }
        else if(colorCode == 2){
            item.setName("Verde");
            return Color.GREEN;
        }
        else if(colorCode == 3){
            item.setName("Azul");
            return Color.BLUE;
        }
        else if(colorCode == 4){
            item.setName("Preto");
            return Color.BLACK;
        }
        else{
            item.setName("Amarelo");
            return Color.YELLOW;
        }
    }
}
