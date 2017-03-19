package com.junior.davino.ran.speech.grammar;

import android.content.Context;

import com.junior.davino.ran.interfaces.IGrammar;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by davin on 18/03/2017.
 */

public class ObjectGrammar implements IGrammar {

    Context context;

    public ObjectGrammar(Context context){
        this.context = context;
    }

    @Override
    public boolean isEqual(String expectedDigit, String actualDigit) {
        int levensteinDistance = StringUtils.getLevenshteinDistance(expectedDigit, actualDigit);
        return levensteinDistance != 0;
    }
}
