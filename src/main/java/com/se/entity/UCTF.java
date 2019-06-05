package com.se.entity;

public class UCTF {
    private long id;
    private long ucId;
    private int corpusIndex;
    private int tf;

    public UCTF(long ucId, int corpusIndex, int tf) {
        this.ucId = ucId;
        this.corpusIndex = corpusIndex;
        this.tf = tf;
    }

    public UCTF() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUcId() {
        return ucId;
    }

    public void setUcId(long ucId) {
        this.ucId = ucId;
    }

    public int getCorpusIndex() {
        return corpusIndex;
    }

    public void setCorpusIndex(int corpusIndex) {
        this.corpusIndex = corpusIndex;
    }

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }
}
