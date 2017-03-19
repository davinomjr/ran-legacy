package com.junior.davino.ran.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by davin on 02/03/2017.
 */

public class ResultSummary implements Parcelable {

    public ResultSummary(){

    }

    private int resultTime;
    private int meanResultTime;
    private int matchesCount;
    private int missesCount;

    public int getResultTime() {
        return resultTime;
    }

    public String getResultTimeInSeconds(){
        return resultTime + "s";
    }

    public void setResultTime(int resultTime) {
        this.resultTime = resultTime;
    }

    public int getMeanResultTime() {
        return meanResultTime;
    }

    public void setMeanResultTime(int meanResultTime) {
        this.meanResultTime = meanResultTime;
    }

    public int getMatchesCount() {
        return matchesCount;
    }

    public void setMatchesCount(int matchesCount) {
        this.matchesCount = matchesCount;
    }

    public int getMissesCount() {
        return missesCount;
    }

    public void setMissesCount(int missesCount) {
        this.missesCount = missesCount;
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
        meanResultTime = in.readInt();
        matchesCount = in.readInt();
        missesCount = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(resultTime);
        dest.writeInt(meanResultTime);
        dest.writeInt(matchesCount);
        dest.writeInt(missesCount);
    }
}
