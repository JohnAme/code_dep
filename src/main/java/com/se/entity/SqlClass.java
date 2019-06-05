package com.se.entity;

import java.util.ArrayList;
import java.util.List;

public class SqlClass {

    private Long classId;
    private Long pid;
    private String path;
    private String className;
    private String content;

    private List<ClassTF> ctfs;


    public SqlClass() {
        ctfs=new ArrayList<>();
    }

    public List<ClassTF> getCtfs() {
        return ctfs;
    }

    public void setCtfs(List<ClassTF> ctfs) {
        this.ctfs = ctfs;
    }


    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
