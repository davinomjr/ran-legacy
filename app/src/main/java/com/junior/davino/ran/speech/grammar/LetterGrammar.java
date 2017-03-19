package com.junior.davino.ran.speech.grammar;

import android.content.Context;

import com.junior.davino.ran.R;
import com.junior.davino.ran.interfaces.IGrammar;

/**
 * Created by davin on 18/03/2017.
 */

public class LetterGrammar implements IGrammar {

    Context context;

    public LetterGrammar(Context context){
        this.context = context;
    }

    public boolean isEqual(String expectedDigit, String actualDigit){
        switch(expectedDigit){
            case "a":
                return context.getResources().getString(R.string.letter_a).contains(actualDigit);
            case "d":
                return context.getResources().getString(R.string.letter_d).contains(actualDigit);
            case "o":
                return context.getResources().getString(R.string.letter_o).contains(actualDigit);
            case "s":
                return context.getResources().getString(R.string.letter_s).contains(actualDigit);
            case "p":
                return context.getResources().getString(R.string.letter_p).contains(actualDigit);
            default:
                return false;
        }
    }
}
