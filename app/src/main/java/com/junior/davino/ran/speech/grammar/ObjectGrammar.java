package com.junior.davino.ran.speech.grammar;

import android.content.Context;

import com.junior.davino.ran.interfaces.IGrammar;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davin on 18/03/2017.
 */

public class ObjectGrammar extends BaseGrammar implements IGrammar {

    Context context;
    private static final int MIN_LEVENSHTEIN_DISTANCE = 2;
    public ObjectGrammar(Context context){
        this.context = context;
    }

    @Override
    public boolean isEqual(String expectedWord, String actualWord) {
        int levenshteinDistance = StringUtils.getLevenshteinDistance(expectedWord, actualWord);
        actualWord = StringUtils.stripAccents(actualWord.toLowerCase());
        return StringUtils.equals(expectedWord, actualWord) || levenshteinDistance <= MIN_LEVENSHTEIN_DISTANCE;
    }

    @Override
    public List<String> getGrammarItems() {
        List<String> words = new ArrayList<>();
        words.add("tesoura");
        words.add("boneca");
        words.add("panela");
        words.add("cachorro");
        words.add("bola");
        return words;
    }
}
