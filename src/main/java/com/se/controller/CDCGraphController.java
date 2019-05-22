package com.se.controller;

import com.se.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/CDCGraph")
public class CDCGraphController {
    private final GraphService graphService;
    @Autowired
    public CDCGraphController(GraphService graphService){
        this.graphService=graphService;
    }
    @RequestMapping("/{projectName}")
    @ResponseBody
    public Map<String,Object> getCDCGraph(@PathVariable(name="projectName")String projectName){
        return graphService.getCDCGraph(projectName,0,0);
    }
}
