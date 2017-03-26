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

public class LetterFilter implements IWordFilter {

    private static final String TAG = "LetterFilter";
    private int minLength, maxLength;
    private Context context;

    public LetterFilter(Context context, int minLength, int maxLength) {
        this.context = context;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public List<String> filterWords(String sentenceRecognized, String characterSplit) {
        List<String> wordsRecognized = new LinkedList<String>(Arrays.asList(sentenceRecognized.split(characterSplit)));
        ListIterator<String> it = wordsRecognized.listIterator();
        while (it.hasNext()) {
            String nextWord = StringUtils.stripAccents(it.next().toLowerCase());
            Log.i(TAG, "WORD BEING FILTERED = " + nextWord);
            if (nextWord.isEmpty()
                    || nextWord.length() > maxLength
                    || !Character.isLetter(nextWord.charAt(0))
                    || !isATestCharacter(nextWord)) {
                it.remove();
            }
        }

        Log.i(TAG, "FINAL WORD = " + wordsRecognized.toString());
        return wordsRecognized;
    }


    private boolean isATestCharacter(String word) {
        return context.getResources().getString(R.string.letter_a).contains(word) ||
                context.getResources().getString(R.string.letter_d).contains(word) ||
                context.getResources().getString(R.string.letter_s).contains(word) ||
                context.getResources().getString(R.string.letter_p).contains(word) ||
                context.getResources().getString(R.string.letter_o).contains(word);
    }
}
