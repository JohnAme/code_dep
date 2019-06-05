package com.se.domain;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "CODE_DEPENDENCY")
public class CodeDcy {
    @Id
    @GeneratedValue
    private Long id;
    private int directCD;
    private double closeness;
    @StartNode
    private MyClass outClass;
    @EndNode
    private MyClass inClass;

    public CodeDcy(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDirectCD() {
        return directCD;
    }

    public void setDirectCD(int directCD) {
        this.directCD = directCD;
    }

    public double getCloseness() {
        return closeness;
    }

    public void setCloseness(double closeness) {
        this.closeness = closeness;
    }

    public MyClass getOutClass() {
        return outClass;
    }

    public void setOutClass(MyClass outClass) {
        this.outClass = outClass;
    }

    public MyClass getInClass() {
        return inClass;
    }

    public void setInClass(MyClass inClass) {
        this.inClass = inClass;
    }
}
