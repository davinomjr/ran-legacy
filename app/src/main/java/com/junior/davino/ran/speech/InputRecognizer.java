package com.junior.davino.ran.speech;

import com.junior.davino.ran.interfaces.IWordFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by davin on 18/03/2017.
 */

public class InputRecognizer {

    IWordFilter filter;
    String characterSplit;

    public InputRecognizer(IWordFilter filter, String charcterSplit){
        this.filter = filter;
        this.characterSplit = charcterSplit;
    }

    public List<String> getRecognizedWordsByType(String sentenceRecognized) {
        List<String> results = new LinkedList<String>(Arrays.asList(sentenceRecognized.split(characterSplit)));
        filter.filterWords(results);
        return results;
    }

    public ArrayList<List<String>> getRecognizedWordsByTypeTest(ArrayList<String> sentencesRecognized){
        ArrayList<List<String>> result = new ArrayList<List<String>>();
        result.add(sentencesRecognized);
        return result;
    }


}
