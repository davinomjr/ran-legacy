package com.junior.davino.ran.builders;

import com.junior.davino.ran.R;
import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.TestItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davin on 24/02/2017.
 */

public class ObjectBuilder extends BaseBuilder implements IItemBuilder {

    @Override
    protected TestItem getItem(int code){
        TestItem item = new TestItem();
        if(code == 1){
            item.setName("Tesoura");
            item.setOrderNumber(R.drawable.tesoura);
        }
        else if(code == 2){
            item.setName("Boneca");
            item.setOrderNumber(R.drawable.boneca);
        }
        else if(code == 3){
            item.setName("Panela");
            item.setOrderNumber(R.drawable.panela);
        }
        else if(code == 4){
            item.setName("Cachorro");
            item.setOrderNumber(R.drawable.cachorro);
        }
        else{
            item.setName("Bola");
            item.setOrderNumber(R.drawable.bola);
        }

        return item;
    }

    @Override
    public List<String> getGrammarItems() {
        List<String> words = new ArrayList<>();
        words.add("Tesoura");
        words.add("Boneca");
        words.add("Panela");
        words.add("Cachorro");
        words.add("Bola");
        return words;
    }
}