package com.junior.davino.ran.factories;

import android.content.Context;

import com.junior.davino.ran.adapters.ColorItemGridAdapter;
import com.junior.davino.ran.adapters.ColorItemResultGridAdapter;
import com.junior.davino.ran.adapters.DigitItemGridAdapter;
import com.junior.davino.ran.adapters.DigitItemResultGridAdapter;
import com.junior.davino.ran.adapters.LetterItemGridAdapter;
import com.junior.davino.ran.adapters.LetterItemResultGridAdapter;
import com.junior.davino.ran.adapters.ObjectItemGridAdapter;
import com.junior.davino.ran.adapters.ObjectItemResultGridAdapter;
import com.junior.davino.ran.interfaces.IGridAdapter;
import com.junior.davino.ran.models.TestItem;
import com.junior.davino.ran.models.enums.EnumTestType;

import java.util.List;

/**
 * Created by davin on 14/03/2017.
 */

public class AdapterFactory {

    public static IGridAdapter buildAdapter(Context context, List<TestItem> items, EnumTestType testType) {
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

    public static IGridAdapter buildAdapterResult(Context context, List<TestItem> items, EnumTestType testType){
        if(testType == EnumTestType.COLORS){
            return new ColorItemResultGridAdapter(context, items);
        }
        else if(testType == EnumTestType.DIGITS){
            return new DigitItemResultGridAdapter(context, items);
        }
        else if(testType == EnumTestType.LETTERS){
            return new LetterItemResultGridAdapter(context, items);
        }
        else{ // OBJECTS
            return new ObjectItemResultGridAdapter(context, items);
        }
    }
}
