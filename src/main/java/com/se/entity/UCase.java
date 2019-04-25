package com.se.entity;

import java.io.Serializable;

public class UCase implements Serializable {
    private static final long serialVersionUID = 1L;

    private long pid;
    private int ucId;
    private String content;

    public long getPid() {
        return pid;
    }

    public void setPid(long pj_id) {
        this.pid = pj_id;
    }

    public int getUcId() {
        return ucId;
    }

    public void setUcId(int uc_id) {
        this.ucId = uc_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
