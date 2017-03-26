package com.junior.davino.ran.interfaces;

import java.util.List;

/**
 * Created by davin on 18/03/2017.
 */

public interface IWordFilter {
     List<String> filterWords(String sentenceRecognized, String characterSplit);
}
