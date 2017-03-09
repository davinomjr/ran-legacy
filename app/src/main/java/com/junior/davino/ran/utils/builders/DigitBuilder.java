package com.junior.davino.ran.utils.builders;

import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.Item;

/**
 * Created by davin on 07/03/2017.
 */

public class DigitBuilder extends BaseBuilder implements IItemBuilder {

    @Override
    protected Item getItem(int colorCode){
        Item item = new Item();
        if(colorCode == 1){
            item.setName("6");
            item.setOrderNumber(6);
        }
        else if(colorCode == 2){
            item.setName("4");
            item.setOrderNumber(4);
        }
        else if(colorCode == 3){
            item.setName("7");
            item.setOrderNumber(7);
        }
        else if(colorCode == 4){
            item.setName("9");
            item.setOrderNumber(9);
        }
        else{
            item.setName("2");
            item.setOrderNumber(2);
        }

        return item;
    }

}
