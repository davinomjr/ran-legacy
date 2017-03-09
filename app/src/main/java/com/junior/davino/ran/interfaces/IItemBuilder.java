package com.junior.davino.ran.interfaces;

import com.junior.davino.ran.models.Item;

import java.util.List;

/**
 * Created by davin on 07/03/2017.
 */

public interface IItemBuilder {
    List<Item> buildItems(int numberOfItems);
}
