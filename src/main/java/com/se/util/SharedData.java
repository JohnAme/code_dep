package com.se.util;

import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

@QueryResult
public class SharedData {
    private Long id;
    private List<String> xDT;
    private List<String> uDT;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getxDT() {
        return xDT;
    }

    public void setxDT(List<String> xDT) {
        this.xDT = xDT;
    }

    public List<String> getuDT() {
        return uDT;
    }

    public void setuDT(List<String> uDT) {
        this.uDT = uDT;
    }
}
