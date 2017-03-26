package com.junior.davino.ran.builders;

import com.junior.davino.ran.models.TestItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by davin on 07/03/2017.
 */

public abstract class BaseBuilder {

    protected abstract TestItem getItem(int code);


    public List<TestItem> buildItems(int numberOfItems) {
        ArrayList<TestItem> items = new ArrayList<TestItem>();

        int actualItemOrder,previousItemOrder;

        for(int i = 0; i < numberOfItems; i++){
            TestItem item;
            previousItemOrder = 0;
            if(items.size() > 0 && i > 0){
                previousItemOrder = items.get(i - 1).getCodeTestNumber();
            }
            do {
                item = generateRandomItem();
                actualItemOrder = item.getCodeTestNumber();
            }while(actualItemOrder == previousItemOrder);
            item.setPosition(1);
            items.add(item);
        }

        return items;
    }


    protected TestItem generateRandomItem(){
        Random random = new Random();
        int code = random.nextInt(5 - 1 + 1) + 1;
        return getItem(code);
    }
}
