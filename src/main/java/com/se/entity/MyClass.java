package com.se.entity;

import java.io.Serializable;

public class MyClass implements Serializable {
    private static final long serialVersionUID = 1L;

    private long pid;
    private String className;
    private String path;
    private String content;
    private long classId;

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getClassName() {
        return className;
    }


    public void setClassId(long classId) {
        this.classId = classId;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getClassId(){return classId;}
}
