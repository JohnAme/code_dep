package com.se.domain;

import org.neo4j.ogm.annotation.*;

import java.util.List;
import java.util.Set;

@RelationshipEntity(type="DATA_DEPENDENCY")
public class DataDcy {
    @Id
    @GeneratedValue
    private Long id;
    private List<String> dataTypes;
    private double closeness;

    @StartNode
    private MyClass firClass;
    @EndNode
    private MyClass secClass;

    public DataDcy(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(List<String> dataTypes) {
        this.dataTypes = dataTypes;
    }

    public double getCloseness() {
        return closeness;
    }

    public void setCloseness(double closeness) {
        this.closeness = closeness;
    }

    public MyClass getFirClass() {
        return firClass;
    }

    public void setFirClass(MyClass firClass) {
        this.firClass = firClass;
    }

    public MyClass getSecClass() {
        return secClass;
    }

    public void setSecClass(MyClass secClass) {
        this.secClass = secClass;
    }

    public MyClass otherClass(long id){
        if(null==firClass||null==secClass){
            return null;
        }
        if(firClass.getId()==id){
            return secClass;
        }else if(secClass.getId()==id){
            return firClass;
        }
        return null;
    }
}
