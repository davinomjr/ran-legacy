package com.junior.davino.ran.interfaces;

import java.util.List;

/**
 * Created by davin on 18/03/2017.
 */

public interface IGrammar {
    boolean isEqual(String expectedWord, String actualWord);
    List<String> getGrammarItems();
    int getMinLength();
    int getMaxLength();
}
