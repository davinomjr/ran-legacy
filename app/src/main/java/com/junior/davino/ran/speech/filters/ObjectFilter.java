package com.junior.davino.ran.speech.filters;

import android.content.Context;

import com.junior.davino.ran.interfaces.IWordFilter;

import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by davin on 18/03/2017.
 */

public class ObjectFilter implements IWordFilter {

    private static final int MIN_LENGTH = 3;

    private Context context;

    public ObjectFilter(Context context){
        this.context = context;
    }

    @Override
    public void filterWords(List<String> wordsRecognized) {

        Iterator<String> it = wordsRecognized.iterator();
        while(it.hasNext()){
            String nextWord = StringUtils.stripAccents(it.next().toLowerCase());
            if(nextWord.isEmpty() || nextWord.length() <= MIN_LENGTH){ // Retirando falso positivo
                it.remove();
            }
        }
    }
}
