package com.junior.davino.ran.factories;

import android.content.Context;

import com.junior.davino.ran.interfaces.IWordFilter;
import com.junior.davino.ran.models.enums.EnumTestType;
import com.junior.davino.ran.speech.filters.ColorFilter;
import com.junior.davino.ran.speech.filters.DigitFilter;
import com.junior.davino.ran.speech.filters.LetterFilter;
import com.junior.davino.ran.speech.filters.ObjectFilter;

/**
 * Created by davin on 07/03/2017.
 */

public final class WordFilterFactory {

    public static IWordFilter createWordFilter(Context context, EnumTestType testType, int minLength, int maxLength){
        if(testType == EnumTestType.COLORS){
            return new ColorFilter(context, minLength, maxLength);
        }
        else if(testType == EnumTestType.DIGITS){
            return new DigitFilter(context, minLength, maxLength);
        }
        else if(testType == EnumTestType.LETTERS){
            return new LetterFilter(context, minLength, maxLength);
        }
        else{ // OBJECTS
            return new ObjectFilter(context, minLength, maxLength);
        }
    }
}
