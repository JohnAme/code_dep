package com.se.controller;


import com.se.entity.SqlClass;
import com.se.service.ProjectManageService;
import com.se.util.CandidateEntry;

import com.se.util.CandidateUtil;
import com.se.util.TreeViewUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/")
public class StartController {
    private static final Logger logger= LoggerFactory.getLogger(StartController.class);

    private final ProjectManageService projectManageService;
    @Autowired
    public StartController(ProjectManageService projectManageService){
        this.projectManageService=projectManageService;
    }

    @GetMapping(value="/getCandidateList")
    @ResponseBody
    public ResponseEntity<?> getCandidateList(@RequestParam(value="pid",required=true)long pid){
        List<CandidateEntry> list=projectManageService.calculateCandidateList(pid);
        List<CandidateUtil> res=new ArrayList<>();

        if(list.isEmpty()){
            logger.error("Project not found");
            return new ResponseEntity<Object>("project not found",HttpStatus.NOT_FOUND);
        }
        for(CandidateEntry entry:list){
//            System.out.format("class:%s,score:%.4f\n",entry.getClassName(),entry.getScore());
            CandidateUtil row=new CandidateUtil(
                    entry.getClassName(),
                    String.format("%.4f",entry.getScore()),
                    "","");
            res.add(row);
        }
        return new ResponseEntity<List<CandidateUtil>>(res,HttpStatus.OK);
    }

    @GetMapping(value="/getProjectDir")
    @ResponseBody
    public ResponseEntity<?> projectDir(@RequestParam(value = "pid",required = true)long pid,
                                        @RequestParam(value="pname",required = true)String pname){
        List<SqlClass> code=projectManageService.getCode(pid);
        List<Path> pathList=new ArrayList<>();

        for(SqlClass sqlClass:code){
            String path=String.format("%s\\%s",sqlClass.getPath(),sqlClass.getClassName());
            pathList.add(Paths.get(path));
        }
        TreeViewUtil root=new TreeViewUtil(pname);
        root.build(pathList);
        return new ResponseEntity<TreeViewUtil>(root,HttpStatus.OK);

    }

    @GetMapping("/uc")
    @ResponseBody
    public String getUC(){
        return projectManageService.getTempUC();//todo ask Gao
    }
}
