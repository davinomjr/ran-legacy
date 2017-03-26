package com.junior.davino.ran.speech.grammar;

import com.junior.davino.ran.interfaces.IGrammar;

import java.util.List;

/**
 * Created by davin on 23/03/2017.
 */

public abstract class BaseGrammar implements IGrammar {

    public abstract List<String> getGrammarItems();

    public int getMinLength(){
        List<String> words = this.getGrammarItems();
        if(words.size() == 0){
            return 0;
        }

        int minLength = 99999;
        for(String word : words){
            if(word.length() < minLength){
                minLength = word.length();
            }
        }

        return minLength;
    }

    public int getMaxLength(){
        List<String> words = this.getGrammarItems();
        if(words.size() == 0){
            return 0;
        }

        int maxLength = 0;
        for(String word : words){
            if(word.length() > maxLength){
                maxLength = word.length();
            }
        }

        return maxLength;
    }
}
