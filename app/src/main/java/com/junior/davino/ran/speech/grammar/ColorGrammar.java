package com.junior.davino.ran.speech.grammar;

import android.content.Context;

import com.junior.davino.ran.interfaces.IGrammar;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davin on 18/03/2017.
 */

public class ColorGrammar extends BaseGrammar implements IGrammar {

    private static final String TAG = "ColorGrammar";
    private static final int MIN_LEVENSHTEIN_DISTANCE = 3;
    private Context context;

    public ColorGrammar(Context context){
        this.context = context;
    }

    @Override
    public boolean isEqual(String expectedWord, String actualWord) {
        int levenshteinDistance = StringUtils.getLevenshteinDistance(expectedWord, actualWord);
        actualWord = StringUtils.stripAccents(actualWord.toLowerCase());
        return StringUtils.equals(expectedWord, actualWord) || levenshteinDistance <= MIN_LEVENSHTEIN_DISTANCE;
    }

    @Override
    public int getMinLength(){
        return 2;
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