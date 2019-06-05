package com.se.entity;

public class PJIdf {
    private long id;
    private long pid;
    private int cindex;
    private String corpus;
    private double idf;

    public PJIdf() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public int getCindex() {
        return cindex;
    }

    public void setCindex(int cindex) {
        this.cindex = cindex;
    }

    public String getCorpus() {
        return corpus;
    }

    public void setCorpus(String corpus) {
        this.corpus = corpus;
    }

    public double getIdf() {
        return idf;
    }

    public void setIdf(double idf) {
        this.idf = idf;
    }
}
