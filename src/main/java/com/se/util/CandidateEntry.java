package com.se.util;

public class CandidateEntry {
    private String className;
    private double score;

    public CandidateEntry(String className,double score){
        this.className=className;
        this.score=score;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
