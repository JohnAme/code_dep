package com.se.entity;

public class Candidate {
    private String c_class;
    private double score;
    private String isVerified;

    public Candidate(String c_class, double score, String isVerified){
        this.c_class = c_class;
        this.score = score;
        this.isVerified = isVerified;
    }

    public String getC_class() {
        return c_class;
    }

    public void setC_class(String c_class) {
        this.c_class = c_class;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }
}
