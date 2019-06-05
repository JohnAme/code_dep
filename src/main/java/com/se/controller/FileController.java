package com.se.controller;

import com.se.entity.Project;
import com.se.service.FileStorageService;
import com.se.service.GenerateIRListService;
import com.se.service.ProjectManageService;
import com.se.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Paths;

@Controller
@RequestMapping("/")
public class FileController {
    private static final Logger logger= LoggerFactory.getLogger(FileController.class);

    private final FileStorageService fileStorageService;
    private final ProjectManageService projectManageService;
    private final GenerateIRListService generateIRListService;

    @Autowired
    public FileController(FileStorageService fileStorageService,
                          ProjectManageService projectManageService,
                          GenerateIRListService generateIRListService){
        this.fileStorageService=fileStorageService;
        this.projectManageService=projectManageService;
        this.generateIRListService=generateIRListService;
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "file") MultipartFile file){
        String res="";
        if(!file.isEmpty()){
            fileStorageService.store(file);
            res="Upload success. File name: "+file.getOriginalFilename();
        }else{
            res="Upload fail. Selected File invalid";
        }
        return res;
    }

    @PostMapping("/uploadNewProject")
    @ResponseBody
    public String uploadNewProject(@RequestParam(value = "uc") MultipartFile uc,
                                   @RequestParam(value = "code") MultipartFile code,
                                   @RequestParam(value = "compile") MultipartFile compile,
                                   @RequestParam(value = "projectName") String projectName,
                                   HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(uc.isEmpty()||code.isEmpty()||compile.isEmpty()||0==projectName.trim().length()){
            return "Incomplete upload!";
        }
//        if(projectManageService.existProject(projectName.trim(),1000001)){//todo addUser id to evaluate
//            return "Project name already exists!";
//        }
        System.out.println(projectManageService.createNewProject(projectName.trim(),1000001));
        long pid=projectManageService.findProjectByPnameAndUid(projectName.trim(),1000001).getPid();
        generateIRListService.initialNewProject(pid,projectName,uc,code,compile);

        System.out.println("complete");
        return "upload controller reached";

    }

}
