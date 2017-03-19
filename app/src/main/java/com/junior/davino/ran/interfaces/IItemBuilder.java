package com.junior.davino.ran.interfaces;

import com.junior.davino.ran.models.TestItem;

import java.util.List;

/**
 * Created by davin on 07/03/2017.
 */

public interface IItemBuilder {
    List<TestItem> buildItems(int numberOfItems);
    List<String> getGrammarItems();
}
