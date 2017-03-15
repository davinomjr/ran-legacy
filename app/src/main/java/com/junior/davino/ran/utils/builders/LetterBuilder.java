package com.junior.davino.ran.utils.builders;

import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.Item;

/**
 * Created by davin on 07/03/2017.
 */

public class LetterBuilder extends BaseBuilder implements IItemBuilder {

    @Override
    protected Item getItem(int colorCode){
        Item item = new Item();
        if(colorCode == 1){
            item.setName("a");
            item.setOrderNumber(1);
        }
        else if(colorCode == 2){
            item.setName("b");
            item.setOrderNumber(2);
        }
        else if(colorCode == 3){
            item.setName("f");
            item.setOrderNumber(3);
        }
        else if(colorCode == 4){
            item.setName("t");
            item.setOrderNumber(4);
        }
        else{
            item.setName("r");
            item.setOrderNumber(5);
        }

        return item;
    }

}
