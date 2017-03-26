package com.junior.davino.ran.builders;

import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.TestItem;

/**
 * Created by davin on 07/03/2017.
 */

public class DigitBuilder extends BaseBuilder implements IItemBuilder {

    @Override
    protected TestItem getItem(int code){
        TestItem item = new TestItem();
        if(code == 1){
            item.setName("6");
            item.setCodeTestNumber(6);
        }
        else if(code == 2){
            item.setName("4");
            item.setCodeTestNumber(4);
        }
        else if(code == 3){
            item.setName("7");
            item.setCodeTestNumber(7);
        }
        else if(code == 4){
            item.setName("9");
            item.setCodeTestNumber(9);
        }
        else{
            item.setName("2");
            item.setCodeTestNumber(2);
        }

        return item;
    }

}
