package com.junior.davino.ran.utils.builders;

import com.junior.davino.ran.R;
import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.Item;

/**
 * Created by davin on 24/02/2017.
 */

public class ObjectBuilder extends BaseBuilder implements IItemBuilder {

    @Override
    protected Item getItem(int code){
        Item item = new Item();
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
}