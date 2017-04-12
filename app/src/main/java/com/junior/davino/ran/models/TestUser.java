package com.junior.davino.ran.models;

import org.parceler.Parcel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by davin on 26/03/2017.
 */

@Parcel
public class TestUser {

    String testUserId;
    String name;
    String schoolGrade;
    String gender;
    int age;
    TestUserParent parent;
    String userId;
    private List<TestResult> testResults;

    public TestUser(){
        parent = new TestUserParent();
    }

    public String getTestUserId() {
        return testUserId;
    }

    public void setTestUserId(String testUserId) {
        this.testUserId = testUserId;
    }
//    public List<TestResult> getTestResults() {
//        return testResults;
//    }
//
//    public void setTestResults(List<TestResult> testResults) {
//        this.testResults = testResults;
//    }

    public TestResult getLastTestResult(){
        if(testResults != null){
            return testResults.get(testResults.size() - 1);
        }

        return null;
    }

    public TestUserParent getParent() {
        return parent;
    }

    public void setParent(TestUserParent parent) {
        this.parent = parent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolGrade() {
        return schoolGrade;
    }

    public void setSchoolGrade(String schoolGrade) {
        this.schoolGrade = schoolGrade;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void addResult(TestResult result){
        if(testResults == null){
            testResults = new LinkedList<TestResult>();
        }

        testResults.add(result);
    }
}
