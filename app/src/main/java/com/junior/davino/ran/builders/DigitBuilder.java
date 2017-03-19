package com.junior.davino.ran.builders;

import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.TestItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davin on 07/03/2017.
 */

public class DigitBuilder extends BaseBuilder implements IItemBuilder {

    @Override
    protected TestItem getItem(int code){
        TestItem item = new TestItem();
        if(code == 1){
            item.setName("6");
            item.setOrderNumber(6);
        }
        else if(code == 2){
            item.setName("4");
            item.setOrderNumber(4);
        }
        else if(code == 3){
            item.setName("7");
            item.setOrderNumber(7);
        }
        else if(code == 4){
            item.setName("9");
            item.setOrderNumber(9);
        }
        else{
            item.setName("2");
            item.setOrderNumber(2);
        }

        return item;
    }


    @Override
    public List<String> getGrammarItems() {
        List<String> words = new ArrayList<>();
        words.add("6");
        words.add("4");
        words.add("7");
        words.add("9");
        words.add("2");
        return words;
    }
}
