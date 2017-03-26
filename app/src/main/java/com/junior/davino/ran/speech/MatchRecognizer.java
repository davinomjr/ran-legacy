package com.junior.davino.ran.speech;

import android.util.Log;

import com.junior.davino.ran.interfaces.IGrammar;
import com.junior.davino.ran.models.ResultSummary;
import com.junior.davino.ran.models.TestItem;

import java.util.Iterator;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by davin on 18/03/2017.
 */

public class MatchRecognizer {

    IGrammar grammar;

    public MatchRecognizer(IGrammar grammar){
        this.grammar = grammar;
    }

    public ResultSummary processTestResult(List<TestItem> items, List<String> wordsRecognized, int ellapsedTime){
        int matchResuts = 0;
        int wrongResults = 0;
        int totalItems = items.size();

        Iterator<TestItem> itItems = items.iterator();
        Iterator<String> itWords = wordsRecognized.iterator();

        while(itItems.hasNext()){
            TestItem item = itItems.next();
            if(itWords.hasNext() && grammar.isEqual(item.getName(), itWords.next())){
                item.setResult(true);
                matchResuts++;
            }
            else {
                item.setResult(false);
                wrongResults++;
            }
        }


        ResultSummary result = new ResultSummary();
        result.setResultTime(ellapsedTime);
        result.setMeanResultTime((double)Math.round(ellapsedTime / (float)totalItems * 100d) / 100d);
        result.setStimuliCount(items.size());
        result.setHitsCount(matchResuts);
        result.setMissesCount(wrongResults);
        return result;
    }

}
