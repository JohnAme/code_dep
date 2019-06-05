package com.se.dal.sqlite.type;

import java.io.Serializable;

/**
 * Created by niejia on 15/2/25.
 */
public class CallRelation implements Serializable {
    private String callerClass;
    private String calleeClass;

    private String callerMethod;
    private String calleeMethod;

    public CallRelation(String callerMethod, String calleeMethod) {
            this.setCallerMethod(callerMethod);
            this.setCalleeMethod(calleeMethod);
            this.setCallerClass(callerMethod.split("#")[0]);
            this.setCalleeClass(calleeMethod.split("#")[0]);
    }

    public CallRelation(String callerClass, String calleeClass, String callerMethod, String calleeMethod) {
        this.setCallerClass(callerClass);
        this.setCalleeClass(calleeClass);
        this.setCallerMethod(callerMethod);
        this.setCalleeMethod(calleeMethod);
    }

    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        CallRelation that = (CallRelation) y;
        if (this.getCallerClass() != that.getCallerClass()) return false;
        if (this.getCalleeClass() != that.getCalleeClass()) return false;
        if (this.getCallerMethod() != that.getCallerMethod()) return false;
        if (this.getCalleeMethod() != that.getCalleeMethod()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + getCallerClass().hashCode();
        hash = 31 * hash + getCalleeClass().hashCode();
        hash = 31 * hash + getCallerMethod().hashCode();
        hash = 31 * hash + getCalleeMethod().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(getCallerMethod());
        sb.append("::");
        sb.append(getCalleeMethod());
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
}
