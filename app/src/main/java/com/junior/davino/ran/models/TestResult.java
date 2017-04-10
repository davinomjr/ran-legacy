package com.junior.davino.ran.models;

import com.junior.davino.ran.models.enums.EnumTestType;

import java.util.List;

/**
 * Created by davin on 02/03/2017.
 */

@org.parceler.Parcel
public class TestResult {

    String resultId;
    int resultTime;
    double meanResultTime;
    int stimuliCount;
    int hitsCount;
    int missesCount;
    String audioPath;
    String testDateTime;
    EnumTestType testType;
    private List<TestItem> testItems;

    public TestResult(){
    }
//
//    public List<TestItem> getTestItems() {
//        return testItems;
//    }
//
//    public void setTestItems(List<TestItem> testItems) {
//        this.testItems = testItems;
//    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public EnumTestType getTestType() {
        return testType;
    }

    public void setTestType(EnumTestType testType) {
        this.testType = testType;
    }

    public String getTestDateTime() {
        return testDateTime;
    }

    public void setTestDateTime(String testDateTime) {
        this.testDateTime = testDateTime;
    }

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

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public double getMeanResultTime() {
        return meanResultTime;
    }

    public void setMeanResultTime(double meanResultTime) {
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

    @Override
    public String toString(){
        return this.getTestDateTime() + " - " + this.getTestType().toString();
    }
}
