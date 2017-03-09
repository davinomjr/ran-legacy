package com.junior.davino.ran.utils.builders;

import com.junior.davino.ran.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by davin on 07/03/2017.
 */

public abstract class BaseBuilder {

    protected abstract Item getItem(int code);


    public List<Item> buildItems(int numberOfItems) {
        ArrayList<Item> items = new ArrayList<Item>();

        int actualItemOrder,previousItemOrder;

        for(int i = 0; i < numberOfItems; i++){
            Item item;
            previousItemOrder = 0;
            if(items.size() > 0 && i > 0){
                previousItemOrder = items.get(i - 1).getOrderNumber();
            }
            do {
                item = generateRandomItem();
                actualItemOrder = item.getOrderNumber();
            }while(actualItemOrder == previousItemOrder);
            item.setPosition(1);
            items.add(item);
        }

        return items;
    }


    protected Item generateRandomItem(){
        Random random = new Random();
        int code = random.nextInt(5 - 1 + 1) + 1;
        return getItem(code);
    }
}
