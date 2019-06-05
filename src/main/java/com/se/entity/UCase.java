package com.se.entity;

import java.io.Serializable;

public class UCase implements Serializable {
    private static final long serialVersionUID = 1L;

    private long pid;
    private long ucId;
    private String content;
    private String ucName;

    public UCase() {
    }

    public UCase(long pid, long ucId, String content, String name) {
        this.pid = pid;
        this.ucId = ucId;
        this.content = content;
        this.ucName = name;
    }

    public UCase(long pid, String content, String name) {
        this.pid = pid;
        this.content = content;
        this.ucName = name;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getUcId() {
        return ucId;
    }

    public void setUcId(long ucId) {
        this.ucId = ucId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUcName() {
        return ucName;
    }

    public void setUcName(String ucName) {
        this.ucName = ucName;
    }

    public void setName(String name) {
        this.ucName = name;
    }
}
