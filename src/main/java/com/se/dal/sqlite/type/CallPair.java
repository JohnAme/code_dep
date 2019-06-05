package com.se.dal.sqlite.type;

import java.util.Objects;

public class CallPair {
    private String caller;
    private String callee;

    public CallPair() {
    }

    public CallPair(String caller, String callee) {
        this.caller = caller;
        this.callee = callee;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CallPair callPair = (CallPair) o;
        return Objects.equals(caller, callPair.caller) &&
                Objects.equals(callee, callPair.callee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caller, callee);
    }
}
