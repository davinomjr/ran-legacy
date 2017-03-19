package com.junior.davino.ran.speech.filters;

import android.content.Context;

import com.junior.davino.ran.interfaces.IWordFilter;

import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by davin on 18/03/2017.
 */

public class LetterFilter implements IWordFilter {

    private static final String TAG = "LetterFilter";
    private static final int MAX_LENGTH = 1;

    private Context context;

    public LetterFilter(Context context){
        this.context = context;
    }

    @Override
    public void filterWords(List<String> wordsRecognized) {
        Iterator<String> it = wordsRecognized.iterator();
        while(it.hasNext()){
            String nextWord = StringUtils.stripAccents(it.next().toLowerCase());
//            Log.i(TAG, "WORD BEING FILTERED = " + nextWord);
            if(nextWord.isEmpty() ||    // Retirando falso positivo
                    nextWord.length() > MAX_LENGTH ||
                        !Character.isLetter(nextWord.charAt(0))){

            }
        }
    }
}
