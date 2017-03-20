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
        return buildAdapter(context, items, testType, false);
    }

    public static IGridAdapter buildAdapter(Context context, List<TestItem> items, EnumTestType testType, boolean isResult){
        if(testType == EnumTestType.COLORS){
            return !isResult ? new ColorItemGridAdapter(context, items) : new ColorItemResultGridAdapter(context, items);
        }
        else if(testType == EnumTestType.DIGITS){
            return !isResult ? new DigitItemGridAdapter(context, items) : new DigitItemResultGridAdapter(context, items);
        }
        else if(testType == EnumTestType.LETTERS){
            return !isResult ? new LetterItemGridAdapter(context, items) : new LetterItemResultGridAdapter(context, items);
        }
        else{ // OBJECTS
            return !isResult ? new ObjectItemGridAdapter(context, items) : new ObjectItemResultGridAdapter(context, items);
        }
    }
}
