package com.junior.davino.ran.speech;

import com.junior.davino.ran.interfaces.IGrammar;
import com.junior.davino.ran.models.TestItem;
import com.junior.davino.ran.models.ResultSummary;

import java.util.ArrayList;
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

    public ResultSummary processTestResult(List<TestItem> items, List<String> wordsRecognized, int ellapsedTime){
        int matchResuts = 0;
        int wrongResults = 0;
        List<TestItem> itemsClone = new ArrayList<TestItem>(items);
        Iterator<TestItem> itItems = itemsClone.iterator();
        Iterator<String> itWords = wordsRecognized.iterator();

        while(itItems.hasNext()){
            TestItem item = itItems.next();
            if(itWords.hasNext() && grammar.isEqual(item.getName(), itWords.next())){
                item.setResult(true);
                matchResuts++;
                continue;
            }

            item.setResult(false);
            wrongResults++;
        }


        ResultSummary result = new ResultSummary();
        result.setResultTime(ellapsedTime);
        result.setMeanResultTime(ellapsedTime / items.size());
        result.setStimuliCount(items.size());
        result.setHitsCount(matchResuts);
        result.setMissesCount(wrongResults);
        return result;
    }

}
