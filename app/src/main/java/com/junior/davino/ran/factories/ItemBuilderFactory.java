package com.junior.davino.ran.factories;

import com.junior.davino.ran.builders.ColorBuilder;
import com.junior.davino.ran.builders.DigitBuilder;
import com.junior.davino.ran.builders.LetterBuilder;
import com.junior.davino.ran.builders.ObjectBuilder;
import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.enums.EnumTestType;

/**
 * Created by davin on 07/03/2017.
 */

public final class ItemBuilderFactory {

    public static IItemBuilder createItemBuilder(EnumTestType testType){
        if(testType == EnumTestType.COLORS){
            return new ColorBuilder();
        }
        else if(testType == EnumTestType.DIGITS){
            return new DigitBuilder();
        }
        else if(testType == EnumTestType.LETTERS){
            return new LetterBuilder();
        }
        else{ // OBJECTS
            return new ObjectBuilder();
        }
    }
}
