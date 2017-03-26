package com.junior.davino.ran.factories;

import android.content.Context;
import android.util.Log;

import com.junior.davino.ran.interfaces.IGrammar;
import com.junior.davino.ran.models.enums.EnumTestType;
import com.junior.davino.ran.speech.grammar.ColorGrammar;
import com.junior.davino.ran.speech.grammar.DigitGrammar;
import com.junior.davino.ran.speech.grammar.LetterGrammar;
import com.junior.davino.ran.speech.grammar.ObjectGrammar;

/**
 * Created by davin on 18/03/2017.
 */

public class GrammarFactory {

    public static IGrammar createGrammar(Context context, EnumTestType testType){
        if(testType == EnumTestType.COLORS){
            return new ColorGrammar(context);
        }
        else if(testType == EnumTestType.DIGITS){
            return new DigitGrammar(context);
        }
        else if(testType == EnumTestType.LETTERS){
            return new LetterGrammar(context);
        }
        else{ // OBJECTS
            Log.i("GRAMMARFACTORY", "OBJECT GRAMMAR");
            return new ObjectGrammar(context);
        }
    }
}
