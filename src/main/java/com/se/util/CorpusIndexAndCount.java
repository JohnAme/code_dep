package com.se.util;

public class CorpusIndexAndCount {
    Integer index;
    Integer docNum;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getDocNum() {
        return docNum;
    }

    public void setDocNum(Integer docNum) {
        this.docNum = docNum;
    }

    public CorpusIndexAndCount(Integer index, Integer docNum) {
        this.index = index;
        this.docNum = docNum;
    }

    public CorpusIndexAndCount() {
    }
}
