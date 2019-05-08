package com.se.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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




}
