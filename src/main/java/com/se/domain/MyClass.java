package com.se.domain;

import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NodeEntity(label = "Class")
public class MyClass {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String path;
    private String project;
    private long pid;



    @Relationship(type = "CODE_DEPENDENCY",direction = Relationship.INCOMING)
    Set<CodeDcy> inDirectCD;
    @Relationship(type = "CODE_DEPENDENCY",direction = Relationship.OUTGOING)
    Set<CodeDcy> outDirectCD;
    @Relationship(type="DATA_DEPENDENCY",direction = Relationship.OUTGOING)
    Set<DataDcy> sharedDT;

    public MyClass(){
        inDirectCD=new HashSet<>();
        outDirectCD=new HashSet<>();
        sharedDT=new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Set<CodeDcy> getInDirectCD() {
        return inDirectCD;
    }

    public void setInDirectCD(Set<CodeDcy> inDirectCD) {
        this.inDirectCD = inDirectCD;
    }

    public Set<CodeDcy> getOutDirectCD() {
        return outDirectCD;
    }

    public void setOutDirectCD(Set<CodeDcy> outDirectCD) {
        this.outDirectCD = outDirectCD;
    }

    public Set<DataDcy> getSharedDT() {
        return sharedDT;
    }

    public void setSharedDT(Set<DataDcy> sharedDT) {
        this.sharedDT = sharedDT;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }
}
