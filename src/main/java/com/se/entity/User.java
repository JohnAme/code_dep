package com.se.entity;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

public class User implements Serializable {
    private final static long serialVersionUID=1L;

    private long uid;
    private String uname;
    private String password;
    private String signature;
    private String loc;
    private String email;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
