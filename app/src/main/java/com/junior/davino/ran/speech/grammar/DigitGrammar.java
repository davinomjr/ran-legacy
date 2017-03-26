package com.junior.davino.ran.speech.grammar;

import android.content.Context;

import com.junior.davino.ran.R;
import com.junior.davino.ran.interfaces.IGrammar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davin on 18/03/2017.
 */

public class DigitGrammar extends BaseGrammar implements IGrammar {

    Context context;

    public DigitGrammar(Context context){
        this.context = context;
    }

    public boolean isEqual(String expectedWord, String actualWord){
        switch(expectedWord){
            case "2":
                return context.getResources().getString(R.string.two).contains(actualWord);
            case "4":
                return context.getResources().getString(R.string.four).contains(actualWord);
            case "6":
                return context.getResources().getString(R.string.six).contains(actualWord);
            case "7":
                return context.getResources().getString(R.string.seven).contains(actualWord);
            case "9":
                return context.getResources().getString(R.string.nine).contains(actualWord);
            default:
                return false;
        }
    }


    @Override
    public List<String> getGrammarItems() {
        List<String> words = new ArrayList<>();
        words.add("6");
        words.add("4");
        words.add("7");
        words.add("9");
        words.add("2");

        words.add("seis");
        words.add("quatro");
        words.add("sete");
        words.add("nove");
        words.add("2");
        return words;
    }
}
