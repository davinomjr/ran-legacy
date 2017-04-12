package com.junior.davino.ran.builders;

import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.TestItem;

/**
 * Created by davin on 07/03/2017.
 */

public class LetterBuilder extends BaseBuilder implements IItemBuilder {

    @Override
    protected TestItem getItem(int colorCode){
        TestItem item = new TestItem();
        if(colorCode == 1){
            item.setName("a");
            item.setCodeTestNumber(1);
        }
        else if(colorCode == 2){
            item.setName("d");
            item.setCodeTestNumber(2);
        }
        else if(colorCode == 3){
            item.setName("o");
            item.setCodeTestNumber(3);
        }
        else if(colorCode == 4){
            item.setName("s");
            item.setCodeTestNumber(4);
        }
        else{
            item.setName("p");
            item.setCodeTestNumber(5);
        }

        return item;
    }
}
