package com.se.controller;

import com.se.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
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
    public Map<String,Object> getCDCGraph(@PathVariable(name="projectName",required = true)String projectName,
                                          @RequestParam(value = "cname",required = true)String cname,
                                          @RequestParam(value = "dc")String dc,
                                          @RequestParam(value = "cd")String cd ){
//        return null;

        return graphService.getRegion(projectName,cname,Double.valueOf(dc),Double.valueOf(cd));
//        return graphService.getCDCGraph(projectName,0.45,0.3);
    }



}
