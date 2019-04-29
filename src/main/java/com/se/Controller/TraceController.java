package com.se.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller()
@RequestMapping(value="/")
public class TraceController {
    @GetMapping(value="/")
    public String index(){
        return "index";
    }

    @RequestMapping("/start")
    public String lab(){
//        System.out.println("lab1");
        return "start";
    }
    @RequestMapping("/project")
    public String lab2(){
        System.out.println("lab3");
        return "project";
    }




}
