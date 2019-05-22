package com.se.service;

import java.util.Map;

public interface GraphService {
    public Map<String,Object> getCDCGraph(String project,double callThreshold,double dataThreshold);
}
