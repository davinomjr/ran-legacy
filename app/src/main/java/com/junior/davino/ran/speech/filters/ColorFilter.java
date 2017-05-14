package com.junior.davino.ran.speech.filters;

import android.content.Context;
import android.util.Log;

import com.junior.davino.ran.interfaces.IWordFilter;
import com.junior.davino.ran.utils.Constants;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by davin on 18/03/2017.
 */

public class ColorFilter implements IWordFilter {

    private static final String TAG = "ColorFilter";
    private int minLength, maxLength;
    private Context context;

    public ColorFilter(Context context, int minLength, int maxLength) {
        this.context = context;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public List<String> filterWords(String sentenceRecognized, String characterSplit) {
        Log.i(TAG, "MIN LENGTH = " + minLength);
         List<String> wordsRecognized = new LinkedList<String>(Arrays.asList(sentenceRecognized.split(characterSplit)));

        if(wordsRecognized.size() > Constants.ITEMSCOUNT){
            ListIterator<String> it = wordsRecognized.listIterator();
            while(it.hasNext()){
                String nextWord = StringUtils.stripAccents(it.next().toLowerCase());
                if(nextWord.isEmpty() || nextWord.length() <= minLength){ // Retirando falso positivo
                    it.remove();
                }
            }

        }

        return wordsRecognized;
    }
}
