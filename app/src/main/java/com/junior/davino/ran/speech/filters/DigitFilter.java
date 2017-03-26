package com.junior.davino.ran.speech.filters;

import android.content.Context;
import android.util.Log;

import com.junior.davino.ran.R;
import com.junior.davino.ran.interfaces.IWordFilter;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by davin on 18/03/2017.
 */

public class DigitFilter implements IWordFilter {

    private static final String TAG = "DigitFilter";
    private int minLength, maxLength;
    private Context context;

    public DigitFilter(Context context, int minLength, int maxLength) {
        this.context = context;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public List<String> filterWords(String sentenceRecognized, String characterSplit) {
        sentenceRecognized = sentenceRecognized.replace("dois", "2");
        sentenceRecognized = sentenceRecognized.replace("quatro", "4");
        sentenceRecognized = sentenceRecognized.replace("seis", "6");
        sentenceRecognized = sentenceRecognized.replace("sete", "7");
        sentenceRecognized = sentenceRecognized.replace("nove", "9");

        Log.i(TAG, sentenceRecognized);
        List<String> results = new LinkedList<String>(Arrays.asList(sentenceRecognized.split(characterSplit)));

        ListIterator<String> it = results.listIterator();
        while(it.hasNext()){
            String nextWord = StringUtils.stripAccents(it.next().toLowerCase());
            if(nextWord.isEmpty() ||  // Removendo falso positivo
                    (!StringUtils.isNumeric(nextWord) && !isADigit(nextWord))){
                it.remove();
            }
        }

        for(String word : results){
            Log.i(TAG, word);
        }

        return results;
    }

    private boolean isADigit(String word){
        return  context.getResources().getString(R.string.two).contains(word) ||
                context.getResources().getString(R.string.four).contains(word) ||
                context.getResources().getString(R.string.six).contains(word) ||
                context.getResources().getString(R.string.seven).contains(word) ||
                context.getResources().getString(R.string.nine).contains(word);
    }
}
