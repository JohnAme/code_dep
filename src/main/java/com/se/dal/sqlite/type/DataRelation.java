package com.se.dal.sqlite.type;

import java.io.Serializable;

/**
 * Created by niejia on 15/2/26.
 */

public class DataRelation implements Serializable {
    public String callerClass;
    public String calleeClass;
    public String callerMethod;
    public String calleeMethod;

    public boolean isUsage;

    public String type;
    public String hashcode;

    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        DataRelation that = (DataRelation) y;
        if (this.getCallerClass() != that.getCallerClass()) return false;
        if (this.getCalleeClass() != that.getCalleeClass()) return false;
        if (this.getCallerMethod() != that.getCallerMethod()) return false;
        if (this.getCalleeMethod() != that.getCalleeMethod()) return false;
        if (this.getType() != that.getType()) return false;
        if (this.getHashcode() != that.getHashcode()) return false;
        if (this.isUsage() != that.isUsage()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + getCallerClass().hashCode();
        hash = 31 * hash + getCalleeClass().hashCode();
        hash = 31 * hash + getCallerMethod().hashCode();
        hash = 31 * hash + getCalleeMethod().hashCode();
        hash = 31 * hash + getType().hashCode();
        hash = 31 * hash + getHashcode().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getCallerClass());
        sb.append("::");
        sb.append(getCalleeClass());
        sb.append("::");
        sb.append(getCallerMethod());
        sb.append("::");
        sb.append(getCalleeMethod());
        sb.append("::");
        sb.append(getType());
        sb.append("::");
        sb.append(getHashcode());
        sb.append("::");
        sb.append(isUsage());

        return sb.toString();
    }

    public String getCallerClass() {
        return callerClass;
    }

    public void setCallerClass(String callerClass) {
        this.callerClass = callerClass;
    }

    public String getCalleeClass() {
        return calleeClass;
    }

    public void setCalleeClass(String calleeClass) {
        this.calleeClass = calleeClass;
    }

    public String getCallerMethod() {
        return callerMethod;
    }

    public void setCallerMethod(String callerMethod) {
        this.callerMethod = callerMethod;
    }

    public String getCalleeMethod() {
        return calleeMethod;
    }

    public void setCalleeMethod(String calleeMethod) {
        this.calleeMethod = calleeMethod;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    public boolean isUsage() {
        return isUsage;
    }

    public void setUsage(boolean isUsage) {
        this.isUsage = isUsage;
    }
}
