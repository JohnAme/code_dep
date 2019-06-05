package com.se.config;


import com.se.dal.CodeDcyRepository;
import com.se.dal.DataDcyRepository;
import com.se.dal.MyClassRepository;
import com.se.service.GenerateGraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class StartUpRunner implements ApplicationRunner {
    @Autowired
    private GenerateGraphService generateGraphService;
    private final DataDcyRepository dataDcyRepository;
    private final MyClassRepository myClassRepository;
    private final CodeDcyRepository codeDcyRepository;
    @Autowired
    public StartUpRunner(DataDcyRepository dataDcyRepository, MyClassRepository myClassRepository, CodeDcyRepository codeDcyRepository){
        this.dataDcyRepository=dataDcyRepository;
        this.myClassRepository=myClassRepository;
        this.codeDcyRepository=codeDcyRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("start up");
        initalITrustGraph();
//        System.out.println(myClassRepository.getDataDependencyFromProject("itrust6"));
//        System.out.println(myClassRepository.findByProject("itrust5").size());
//        System.out.println(codeDcyRepository.getCodeCDFromProject("itrust5"));

        System.out.println("init complete");

    }

    private void initalITrustGraph(){
        String pname="iTrust";
        if(generateGraphService.findByName(pname)){
            System.out.println("already exist");
            return;
        }
        Path codeDir= Paths.get("D:\\repository\\code_dep\\src\\main\\resources\\project\\ITrust\\class\\code");
        Path relationDir=Paths.get("D:\\repository\\code_dep\\src\\main\\resources\\project\\ITrust\\relation");

        generateGraphService.generateNewGraph(pname,codeDir,relationDir);

    }
}
