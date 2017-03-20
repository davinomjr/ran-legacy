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
    private int stimuliCount;
    private int hitsCount;
    private int missesCount;


    public int getStimuliCount() {
        return stimuliCount;
    }

    public void setStimuliCount(int stimuliCount) {
        this.stimuliCount = stimuliCount;
    }

    public int getResultTime() {
        return resultTime;
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

    public int getHitsCount() {
        return hitsCount;
    }

    public void setHitsCount(int hitsCount) {
        this.hitsCount = hitsCount;
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
        stimuliCount = in.readInt();
        hitsCount = in.readInt();
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
        dest.writeInt(stimuliCount);
        dest.writeInt(hitsCount);
        dest.writeInt(missesCount);
    }
}
