package com.se.entity;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    private long pid;
    private String pname;
    private long ownerId;
    private List<SqlClass> code;
    private List<UCase> document;

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setOwnerId(long ownerId){this.ownerId=ownerId;}

    public long getOwnerId(){return ownerId;}

    public List<SqlClass> getCode(){ return code; }

    public void setCode(List<SqlClass> code){this.code=code;}

    public List<UCase> getDocument(){return document;}

    public void setDocument(List<UCase> document){this.document=document;}
}
