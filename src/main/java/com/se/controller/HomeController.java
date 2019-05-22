package com.se.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping(value="/")
public class HomeController {
    @GetMapping(value="/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/start")
    public String start(){
//        System.out.println("lab1");
        return "start";
    }
    @RequestMapping("/project")
    public String project(){
        return "project";
    }
    @RequestMapping("/friends")
    public String friends(){
        return "friends";
    }
    @RequestMapping("/movie_graph")
    public String graph(){
        return "graph";
    }

    @RequestMapping("/CDCGraph")
    public String toCDCGraph(){
        return "graph";
    }


}
