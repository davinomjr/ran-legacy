package com.junior.davino.ran.builders;

import android.graphics.Color;

import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.TestItem;

/**
 * Created by davin on 24/02/2017.
 */

public class ColorBuilder extends BaseBuilder implements IItemBuilder {

    @Override
    protected TestItem getItem(int code){
        TestItem item = new TestItem();
        if(code == 1){
            item.setName("vermelho");
            item.setCodeTestNumber(Color.RED);
        }
        else if(code == 2){
            item.setName("verde");
            item.setCodeTestNumber(Color.GREEN);
        }
        else if(code == 3){
            item.setName("azul");
            item.setCodeTestNumber(Color.BLUE);
        }
        else if(code == 4){
            item.setName("preto");
            item.setCodeTestNumber(Color.BLACK);
        }
        else{
            item.setName("amarelo");
            item.setCodeTestNumber(Color.YELLOW);
        }

        return item;
    }
}
