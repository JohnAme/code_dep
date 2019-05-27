package com.se.controller;

import com.se.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/findProject")
    public String findProject(@RequestParam("projectName")String projectName,@RequestParam("direct")String direct,@RequestParam("data") String data){
        System.out.println(projectName);
        System.out.println(direct);
        System.out.println(data);
        return "graph";
    }
}
