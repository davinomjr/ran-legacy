package com.junior.davino.ran.utils.builders;

import android.content.Context;

import com.junior.davino.ran.adapters.ColorItemGridAdapter;
import com.junior.davino.ran.adapters.DigitItemGridAdapter;
import com.junior.davino.ran.adapters.LetterItemGridAdapter;
import com.junior.davino.ran.adapters.ObjectItemGridAdapter;
import com.junior.davino.ran.interfaces.IGridAdapter;
import com.junior.davino.ran.models.Item;
import com.junior.davino.ran.models.enums.EnumTestType;

import java.util.List;

/**
 * Created by davin on 14/03/2017.
 */

public class FactoryAdapter {

    public static IGridAdapter buildAdapter(Context context, List<Item> items, EnumTestType testType){
        if(testType == EnumTestType.COLORS){
            return new ColorItemGridAdapter(context, items);
        }
        else if(testType == EnumTestType.DIGITS){
            return new DigitItemGridAdapter(context, items);
        }
        else if(testType == EnumTestType.LETTERS){
            return new LetterItemGridAdapter(context, items);
        }
        else{ // OBJECTS
            return new ObjectItemGridAdapter(context, items);
        }
    }
}
