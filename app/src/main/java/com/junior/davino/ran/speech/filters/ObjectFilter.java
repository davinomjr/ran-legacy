package com.junior.davino.ran.speech.filters;

import android.content.Context;

import com.junior.davino.ran.interfaces.IWordFilter;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by davin on 18/03/2017.
 */

public class ObjectFilter implements IWordFilter {

    private int minLength, maxLength;
    private Context context;

    public ObjectFilter(Context context, int minLength, int maxLength) {
        this.context = context;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public List<String> filterWords(String sentenceRecognized, String characterSplit) {
        List<String> wordsRecognized = new LinkedList<String>(Arrays.asList(sentenceRecognized.split(characterSplit)));
        ListIterator<String> it = wordsRecognized.listIterator();
        while(it.hasNext()){
            String nextWord = StringUtils.stripAccents(it.next().toLowerCase());
            if(nextWord.isEmpty() || nextWord.length() <= minLength){ // Retirando falso positivo
                it.remove();
            }
        }

        return wordsRecognized;
    }
}
