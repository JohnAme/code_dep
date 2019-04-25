package com.se.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity(label = "Class")
public class MyClass {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int inDegree;
    private int outDegree;

    @Relationship(type="CODE_DEPENDENCY",direction = Relationship.INCOMING)
    List<MyClass> inClass;
    @Relationship(type="DATA_DEPENDENCY",direction = Relationship.UNDIRECTED)
    List<MyClass> sharedDataClass;

    public MyClass(){

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getInDegree() {
        return inDegree;
    }

    public int getOutDegree() {
        return outDegree;
    }

    public void addInClasses(MyClass myClass) {
        if(null==this.inClass){
            this.inClass=new ArrayList<>();
        }
        this.inClass.add(myClass);
    }
    public void addSharedData(MyClass myClass){
        if(null==this.sharedDataClass){
            this.sharedDataClass=new ArrayList<>();
        }
        this.sharedDataClass.add(myClass);
    }
}
