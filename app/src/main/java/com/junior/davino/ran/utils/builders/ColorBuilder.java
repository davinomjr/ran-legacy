package com.junior.davino.ran.utils.builders;

import android.graphics.Color;

import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.Item;

/**
 * Created by davin on 24/02/2017.
 */

public class ColorBuilder extends BaseBuilder implements IItemBuilder {


    @Override
    protected Item getItem(int code){
        Item item = new Item();
        if(code == 1){
            item.setName("Vermelho");
            item.setOrderNumber(Color.RED);
        }
        else if(code == 2){
            item.setName("Verde");
            item.setOrderNumber(Color.GREEN);
        }
        else if(code == 3){
            item.setName("Azul");
            item.setOrderNumber(Color.BLUE);
        }
        else if(code == 4){
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
