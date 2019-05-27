package com.se.util;

public class SharedDataType {
    private String name;
    private  double idtf;
    private int occurence;

    public SharedDataType(String name, double idtf, int occurence) {
        this.name = name;
        this.idtf = idtf;
        this.occurence = occurence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getIdtf() {
        return idtf;
    }

    public void setIdtf(double idtf) {
        this.idtf = idtf;
    }

    public int getOccurence() {
        return occurence;
    }

    public void setOccurence(int occurence) {
        this.occurence = occurence;
    }
}
