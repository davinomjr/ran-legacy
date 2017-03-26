package com.junior.davino.ran.speech;

import com.junior.davino.ran.interfaces.IWordFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davin on 18/03/2017.
 */

public class InputRecognizer {

    IWordFilter filter;
    String characterSplit;

    public InputRecognizer(IWordFilter filter, String characterSplit){
        this.filter = filter;
        this.characterSplit = characterSplit;
    }

    public List<String> getRecognizedWordsByType(String sentenceRecognized) {
        return filter.filterWords(sentenceRecognized, characterSplit);
    }

    public ArrayList<List<String>> getRecognizedWordsByTypeTest(ArrayList<String> sentencesRecognized){
        ArrayList<List<String>> result = new ArrayList<List<String>>();
        result.add(sentencesRecognized);
        return result;
    }


}
