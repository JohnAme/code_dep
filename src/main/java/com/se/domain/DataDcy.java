package com.se.domain;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type="DATA_DEPENDENCY")
public class DataDcy {
    @Id
    @GeneratedValue
    private Long id;
    private double value;
    private double ddc;

    @StartNode
    private MyClass firClass;
    @EndNode
    private MyClass secClass;

    public DataDcy(){

    }

    public DataDcy(MyClass fir,MyClass sec){
        this.firClass=fir;
        this.secClass=sec;
    }

    public Long getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public double getDdc() {
        return ddc;
    }

    public MyClass getFirClass() {
        return firClass;
    }

    public MyClass getSecClass() {
        return secClass;
    }
}
