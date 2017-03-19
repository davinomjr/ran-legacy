package com.junior.davino.ran.speech.grammar;

import android.content.Context;

import com.junior.davino.ran.R;
import com.junior.davino.ran.interfaces.IGrammar;

/**
 * Created by davin on 18/03/2017.
 */

public class DigitGrammar implements IGrammar {

    Context context;

    public DigitGrammar(Context context){
        this.context = context;
    }

    public boolean isEqual(String expectedDigit, String actualDigit){
        switch(expectedDigit){
            case "2":
                return context.getResources().getString(R.string.two).contains(actualDigit);
            case "4":
                return context.getResources().getString(R.string.four).contains(actualDigit);
            case "6":
                return context.getResources().getString(R.string.six).contains(actualDigit);
            case "7":
                return context.getResources().getString(R.string.seven).contains(actualDigit);
            case "9":
                return context.getResources().getString(R.string.nine).contains(actualDigit);
            default:
                return false;
        }
    }
}
