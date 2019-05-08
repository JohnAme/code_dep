package com.se.Controller;

import com.se.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Iterator;

@Controller
@RequestMapping("/")
public class FileController {
    private static final Logger logger= LoggerFactory.getLogger(FileController.class);

    private final FileStorageService fileStorageService;

    @Autowired
    public FileController(FileStorageService fileStorageService){
        this.fileStorageService=fileStorageService;
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

}
