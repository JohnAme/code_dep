package com.se.dal.sqlite.type;

import java.io.Serializable;

/**
 * Created by niejia on 15/2/27.
 */

/*
 * 两种情况：
 * 1. 函数中若要访问accessFieldinClass中的域，必先创建accessFieldinClass的实例，通过accessFieldinClass的方法进行调用。
 *  		此时accessFieldinClass.equals(accessInvokerMethodClass)
 * 2. 若类中的域没有访问器(或是枚举)，则!accessFieldinClass.equals(accessInvokerMethodClass)
 */

public class FieldAccess extends FieldMonitor implements Serializable {
  /*
   *类(accessFieldinClass)下的域(fieldName，域的类型为fieldType，Hashcode为fieldHashcode)在实例(该实例的Hashcode为classInstanceHashcode)中，
   *被类(accessInvokerMethodClass)通过的函数(accessInvokerMethodClass)访问
   */

    private String cSignature;
    private String oHashcode;

    public String getcSignature() {
        return cSignature;
    }

    public void setcSignature(String cSignature) {
        this.cSignature = cSignature;
    }

    public String getoHashcode() {
        return oHashcode;
    }

    public void setoHashcode(String oHashcode) {
        this.oHashcode = oHashcode;
    }

    public String getfSignature() {
        return fSignature;
    }

    public void setfSignature(String fSignature) {
        this.fSignature = fSignature;
    }

    public String getfHashcode() {
        return fHashcode;
    }

    public void setfHashcode(String fHashcode) {
        this.fHashcode = fHashcode;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getMcSignature() {
        return McSignature;
    }

    public void setMcSignature(String mcSignature) {
        McSignature = mcSignature;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodSignature() {
        return methodSignature;
    }

    public void setMethodSignature(String methodSignature) {
        this.methodSignature = methodSignature;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(cSignature);
        sb.append(" ");
        sb.append(oHashcode);
        sb.append(" ");
        sb.append(fSignature);
        sb.append(" ");
        sb.append(fHashcode);
        sb.append(" ");
        sb.append(fName);
        sb.append(" ");
        sb.append(McSignature);
        sb.append(" ");
        sb.append(methodName);
        sb.append(" ");
        sb.append(methodSignature);
        sb.append("\n");

        return sb.toString();
    }

    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        FieldAccess that = (FieldAccess) y;
        if (this.cSignature != that.cSignature) return false;
        if (this.oHashcode != that.oHashcode) return false;
        if (this.fSignature != that.fSignature) return false;
        if (this.fHashcode != that.fHashcode) return false;
        if (this.fName != that.fName) return false;
        if (this.McSignature != that.McSignature) return false;
        if (this.methodName != that.methodName) return false;
        if (this.methodSignature != that.methodSignature) return false;
        if (this.type != that.type) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + cSignature.hashCode();
        hash = 31 * hash + oHashcode.hashCode();
        hash = 31 * hash + fSignature.hashCode();
        hash = 31 * hash + fHashcode.hashCode();
        hash = 31 * hash + fName.hashCode();
        hash = 31 * hash + McSignature.hashCode();
        hash = 31 * hash + methodName.hashCode();
        hash = 31 * hash + methodSignature.hashCode();
        hash = 31 * hash + type.hashCode();
        return hash;
    }
}
