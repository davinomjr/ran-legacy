package com.junior.davino.ran.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davin on 02/03/2017.
 */

public class ResultSummary implements Parcelable {

    private int resultTime;
    private List<String> words;
    private List<String> matchWords;
    private List<String> missesWords;


    public ResultSummary(){
        words = new ArrayList();
        matchWords = new ArrayList();
        missesWords = new ArrayList();
    }


    public int getResultTime() {
        return resultTime;
    }

    public String getResultTimeInSeconds(){
        return resultTime + "s";
    }

    public void setResultTime(int resultTime) {
        this.resultTime = resultTime;
    }

    public List<String> getMatchWords() {
        return matchWords;
    }

    public void setMatchWords(List<String> matchWords) {
        this.matchWords = matchWords;
    }

    public List<String> getMissesWords() {
        return missesWords;
    }

    public void setMissesWords(List<String> missesWords) {
        this.missesWords = missesWords;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public List<String> getWords() {
        return words;
    }



    public static final Parcelable.Creator<ResultSummary> CREATOR = new Parcelable.ClassLoaderCreator<ResultSummary>(){
        @Override
        public ResultSummary createFromParcel(Parcel source) {
            return new ResultSummary(source);
        }

        @Override
        public ResultSummary[] newArray(int size) {
            return new ResultSummary[size];
        }

        @Override
        public ResultSummary createFromParcel(Parcel source, ClassLoader loader) {
            return new ResultSummary(source);
        }

    };

    private ResultSummary(Parcel in){
        resultTime = in.readInt();
        words = in.createStringArrayList();
        matchWords = in.createStringArrayList();
        missesWords = in.createStringArrayList();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(resultTime);
        dest.writeStringList(words);
        dest.writeStringList(matchWords);
        dest.writeStringList(missesWords);
    }
}
