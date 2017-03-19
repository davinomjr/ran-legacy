package com.junior.davino.ran.speech.filters;

import android.content.Context;

import com.junior.davino.ran.R;
import com.junior.davino.ran.interfaces.IWordFilter;

import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by davin on 18/03/2017.
 */

public class DigitFilter implements IWordFilter {

    Context context;

    public DigitFilter(Context context){
        this.context = context;
    }

    @Override
    public void filterWords(List<String> wordsRecognized) {
        Iterator<String> it = wordsRecognized.iterator();
        while(it.hasNext()){
            String nextWord = StringUtils.stripAccents(it.next().toLowerCase());
            if(nextWord.isEmpty() ||  // Removendo falso positivo
                    (!StringUtils.isNumeric(nextWord) && !isADigit(nextWord))){
                it.remove();
            }
        }
    }

    private boolean isADigit(String word){
        return  context.getResources().getString(R.string.two).contains(word) ||
                context.getResources().getString(R.string.four).contains(word) ||
                context.getResources().getString(R.string.six).contains(word) ||
                context.getResources().getString(R.string.seven).contains(word) ||
                context.getResources().getString(R.string.nine).contains(word);
    }
}
