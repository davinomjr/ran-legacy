package com.junior.davino.ran.builders;

import android.graphics.Color;

import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.TestItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davin on 24/02/2017.
 */

public class ColorBuilder extends BaseBuilder implements IItemBuilder {



    @Override
    protected TestItem getItem(int code){
        TestItem item = new TestItem();
        if(code == 1){
            item.setName("vermelho");
            item.setOrderNumber(Color.RED);
        }
        else if(code == 2){
            item.setName("verde");
            item.setOrderNumber(Color.GREEN);
        }
        else if(code == 3){
            item.setName("azul");
            item.setOrderNumber(Color.BLUE);
        }
        else if(code == 4){
            item.setName("preto");
            item.setOrderNumber(Color.BLACK);
        }
        else{
            item.setName("amarelo");
            item.setOrderNumber(Color.YELLOW);
        }

        return item;
    }


    @Override
    public List<String> getGrammarItems() {
        List<String> words = new ArrayList<>();
        words.add("vermelho");
        words.add("verde");
        words.add("azul");
        words.add("preto");
        words.add("amarelo");
        return words;
    }
}
