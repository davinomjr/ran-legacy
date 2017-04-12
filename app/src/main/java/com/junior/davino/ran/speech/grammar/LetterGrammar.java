package com.junior.davino.ran.speech.grammar;

import android.content.Context;

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
        return true;
//        switch(expectedWord){
//            case "a":
//                return context.getResources().getString(R.string.letter_a).contains(actualWord);
//            case "d":
//                return context.getResources().getString(R.string.letter_d).contains(actualWord);
//            case "o":
//                return context.getResources().getString(R.string.letter_o).contains(actualWord);
//            case "s":
//                return context.getResources().getString(R.string.letter_s).contains(actualWord);
//            case "p":
//                return context.getResources().getString(R.string.letter_p).contains(actualWord);
//            default:
//                return false;
        }


    @Override
    public List<String> getGrammarItems() {
        List<String> words = new ArrayList<>();
        words.add("a");
        words.add("d");
        words.add("o");
        words.add("s");
        words.add("p");
        return words;
    }
}
