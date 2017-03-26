package com.junior.davino.ran.speech.grammar;

import android.content.Context;

import com.junior.davino.ran.R;
import com.junior.davino.ran.interfaces.IGrammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davin on 18/03/2017.
 */

public class LetterGrammar extends BaseGrammar implements IGrammar {

    Context context;

    public LetterGrammar(Context context){
        this.context = context;
    }

    public boolean isEqual(String expectedWord, String actualWord){
        switch(expectedWord){
            case "a":
                return context.getResources().getString(R.string.letter_a).contains(actualWord);
            case "f":
                return context.getResources().getString(R.string.letter_f).contains(actualWord);
            case "s":
                return context.getResources().getString(R.string.letter_s).contains(actualWord);
            case "g":
                return context.getResources().getString(R.string.letter_g).contains(actualWord);
            case "u":
                return context.getResources().getString(R.string.letter_u).contains(actualWord);
            default:
                return false;
        }
    }


    @Override
    public List<String> getGrammarItems() {
        List<String> words = new ArrayList<>();
        words.add("a");
        words.add("f");
        words.add("s");
        words.add("g");
        words.add("u");
        return words;
    }
}
