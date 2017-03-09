package com.junior.davino.ran.utils.builders;

import com.junior.davino.ran.interfaces.IItemBuilder;
import com.junior.davino.ran.models.enums.EnumTestType;

/**
 * Created by davin on 07/03/2017.
 */

public final class FactoryBuilder {

    public static IItemBuilder createItemBuilder(EnumTestType testType){
        if(testType == EnumTestType.COLORS){
            return new ColorBuilder();
        }
        else if(testType == EnumTestType.DIGITS){
            return new DigitBuilder();
        }

        return null;
    }
}
