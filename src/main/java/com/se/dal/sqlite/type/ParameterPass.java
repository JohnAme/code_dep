package com.se.dal.sqlite.type;

import java.io.Serializable;

/**
 * Created by niejia on 15/1/4.
 */
public class ParameterPass extends FieldMonitor implements Serializable {


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
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
        ParameterPass that = (ParameterPass) y;
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
