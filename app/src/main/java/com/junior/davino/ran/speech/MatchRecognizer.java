package com.junior.davino.ran.speech;

import com.junior.davino.ran.interfaces.IGrammar;
import com.junior.davino.ran.models.TestResult;
import com.junior.davino.ran.models.TestItem;
import com.junior.davino.ran.utils.Util;

import java.util.Iterator;
import java.util.List;

/**
 * Created by davin on 18/03/2017.
 */

public class MatchRecognizer {

    IGrammar grammar;

    public MatchRecognizer(IGrammar grammar){
        this.grammar = grammar;
    }

    public TestResult processTestResult(List<TestItem> items, List<String> wordsRecognized, int ellapsedTime, String audioFilePath){
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


        TestResult result = new TestResult();
        result.setResultTime(ellapsedTime);
        result.setMeanResultTime((double)Math.round(ellapsedTime / (float)totalItems * 100d) / 100d);
        result.setStimuliCount(items.size());
        result.setHitsCount(matchResuts);
        result.setMissesCount(wrongResults);
        result.setTestDateTime(Util.getCurrentDateTimeFormatted());
        result.setAudioPath(audioFilePath);
        return result;
    }

}
