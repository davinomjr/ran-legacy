package com.junior.davino.ran.builders;

import com.junior.davino.ran.R;
import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.TestItem;

/**
 * Created by davin on 24/02/2017.
 */

public class ObjectBuilder extends BaseBuilder implements IItemBuilder {

    @Override
    protected TestItem getItem(int code){
        TestItem item = new TestItem();
        if(code == 1){
            item.setName("tesoura");
            item.setCodeTestNumber(R.drawable.tesoura);
        }
        else if(code == 2){
            item.setName("boneca");
            item.setCodeTestNumber(R.drawable.boneca);
        }
        else if(code == 3){
            item.setName("panela");
            item.setCodeTestNumber(R.drawable.panela);
        }
        else if(code == 4){
            item.setName("cachorro");
            item.setCodeTestNumber(R.drawable.cachorro);
        }
        else{
            item.setName("bola");
            item.setCodeTestNumber(R.drawable.bola);
        }

        return item;
    }
}