package com.se.dal.sqlite.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DTPair {
    private String caller;
    private String callee;

    public DTPair(String caller, String callee) {
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
        DTPair dtPair = (DTPair) o;

        if(Objects.equals(caller, dtPair.caller) &&
                Objects.equals(callee, dtPair.callee)){
            return true;
        }
        if(Objects.equals(caller,dtPair.callee)&&
                Objects.equals(callee,dtPair.caller)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(caller, callee);
    }

    public static void saveDataTypes(Map<DTPair, String> sharedDT){
        for(DTPair dt:sharedDT.keySet()){

        }
    }
    public static void main(String[] args){
        String fir="aro";
        String sec="mave";
        DTPair a=new DTPair(fir,sec);
        DTPair b=new DTPair(sec,fir);
        Map<DTPair,String> map=new HashMap<>();
        map.put(new DTPair(a.getCaller(),a.getCallee()),"a");
        map.put(new DTPair(b.getCaller(),b.getCallee()),"b");
        System.out.println(map.containsKey(a));
        System.out.println(map.containsKey(b));
    }
}
