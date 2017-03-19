package com.junior.davino.ran.builders;

import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.TestItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davin on 07/03/2017.
 */

public class LetterBuilder extends BaseBuilder implements IItemBuilder {

    @Override
    protected TestItem getItem(int colorCode){
        TestItem item = new TestItem();
        if(colorCode == 1){
            item.setName("a");
            item.setOrderNumber(1);
        }
        else if(colorCode == 2){
            item.setName("d");
            item.setOrderNumber(2);
        }
        else if(colorCode == 3){
            item.setName("o");
            item.setOrderNumber(3);
        }
        else if(colorCode == 4){
            item.setName("s");
            item.setOrderNumber(4);
        }
        else{
            item.setName("p");
            item.setOrderNumber(5);
        }

        return item;
    }


    @Override
    public List<String> getGrammarItems() {
        List<String> words = new ArrayList<>();
        words.add("f");
        words.add("g");
        words.add("r");
        words.add("h");
        words.add("e");
        return words;
    }

}
