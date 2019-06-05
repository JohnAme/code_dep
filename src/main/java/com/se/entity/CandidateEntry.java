package com.se.entity;

import com.se.domain.MyClass;

public class CandidateEntry {
    private long id;
    private long pid;
    private long classId;
    private long ucId;
    private double originScore;
    private double revaluedScore;
    private String status;
    private String algorithm;
    private SqlClass sqlClass;

    public String getAlgorithm() {
        return algorithm;
    }

    public SqlClass getSqlClass() {
        return sqlClass;
    }

    public void setSqlClass(SqlClass sqlClass) {
        this.sqlClass = sqlClass;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }



    public CandidateEntry() {
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

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public long getUcId() {
        return ucId;
    }

    public void setUcId(long ucId) {
        this.ucId = ucId;
    }

    public double getOriginScore() {
        return originScore;
    }

    public void setOriginScore(double originScore) {
        this.originScore = originScore;
    }

    public double getRevaluedScore() {
        return revaluedScore;
    }

    public void setRevaluedScore(double revaluedScore) {
        this.revaluedScore = revaluedScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public static final String RELEVANT="relevant";
    public static final String IRRELEVANT="irrelevant";
    public static final String SKIP="skip";
    public static final String STOP="stop";

    public static final String VSM="vsm";
    public static final String LSI="lsi";
    public static final String JSM="jsm";

}
