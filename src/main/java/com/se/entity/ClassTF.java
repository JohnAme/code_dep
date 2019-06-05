package com.se.entity;

public class ClassTF {
    private long id;
    private long classId;
    private int corpusIndex;
    private int tf;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
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

    public ClassTF(long classId, int corpusIndex, int tf) {
        this.classId = classId;
        this.corpusIndex = corpusIndex;
        this.tf = tf;
    }

    public ClassTF() {
    }
}
