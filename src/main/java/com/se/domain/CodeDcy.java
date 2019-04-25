package com.se.domain;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "CODE_DEPENDENCY")
public class CodeDcy {
    @Id
    @GeneratedValue
    private Long id;
    private String from;
    private String to;
    private double value;
    private double cdc;

    @StartNode
    private MyClass outClass;

    @EndNode
    private MyClass inClass;

    public CodeDcy(){

    }

    public CodeDcy(MyClass out, MyClass in){
        this.outClass=out;
        this.inClass=in;
    }

    public Long getId() {
        return id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getValue() {
        return value;
    }

    public double getCdc() {
        return cdc;
    }
    public MyClass getOutClass() {
        return outClass;
    }

    public MyClass getInClass() {
        return inClass;
    }
}
