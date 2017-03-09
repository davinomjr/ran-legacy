package com.junior.davino.ran.utils.builders;

import android.graphics.Color;

import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.Item;

/**
 * Created by davin on 24/02/2017.
 */

public class ColorBuilder extends BaseBuilder implements IItemBuilder {


    @Override
    protected Item getItem(int colorCode){
        Item item = new Item();
        if(colorCode == 1){
            item.setName("Vermelho");
            item.setOrderNumber(Color.RED);
        }
        else if(colorCode == 2){
            item.setName("Verde");
            item.setOrderNumber(Color.GREEN);
        }
        else if(colorCode == 3){
            item.setName("Azul");
            item.setOrderNumber(Color.BLUE);
        }
        else if(colorCode == 4){
            item.setName("Preto");
            item.setOrderNumber(Color.BLACK);
        }
        else{
            item.setName("Amarelo");
            item.setOrderNumber(Color.YELLOW);
        }

        return item;
    }


}
