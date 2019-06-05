package com.se.service.impl;

import com.se.dal.CodeDcyRepository;
import com.se.dal.DataDcyRepository;
import com.se.dal.MyClassRepository;
import com.se.dal.sqlite.CallRelationDao;
import com.se.dal.sqlite.DataRelationDao;
import com.se.dal.sqlite.type.*;
import com.se.domain.CodeDcy;
import com.se.domain.DataDcy;
import com.se.domain.MyClass;
import com.se.service.GenerateGraphService;
import com.se.service.GraphService;
import com.se.util.FileUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class GenerateGraphServiceImpl implements GenerateGraphService {
    private final MyClassRepository classRepository;
    private final DataDcyRepository dataDPRepository;
    private final CodeDcyRepository directDPRepository;

    @Autowired
    public GenerateGraphServiceImpl(MyClassRepository classRepository,
                                    DataDcyRepository dataDcyRepository,
                                    CodeDcyRepository directDPRepository){
        this.classRepository=classRepository;
        this.dataDPRepository=dataDcyRepository;
        this.directDPRepository=directDPRepository;
    }

    @Override
    @Transactional
    public boolean generateNewGraph(String pname, Path codeDir, Path dbDir) {
        if(classRepository.findByProjectName(pname)){
            System.err.println("project already exist!");
            return false;
        }

        Set<String> vertex=getClassSet(codeDir);
        if(0==vertex.size()){
            System.err.println("wrong class dir:"+codeDir.toString());
            return false;
        }

        Set<MyClass> classes=saveAllClass(vertex,pname);

        CallRelationList crLit=CallRelationDao.callAsRelation(dbDir.resolve("call.db"));
        DataRelationDao dataRelationDao=new DataRelationDao(dbDir);
        DataRelationList dt=dataRelationDao.getFullDR();
        DataRelationList usage=dataRelationDao.getUsage();

        saveCallDP(vertex,classes,crLit,usage);
        classRepository.setDirectCDClosenessFromProject(pname);
        saveDataDP(vertex,classes,dt,pname);
        Long deleteLinks=dataDPRepository.deleteByCloseness(0);
        System.out.println("delete closeness=0 records:"+deleteLinks);
        return true;
    }

    private void saveDataDP(Set<String> classNames,Set<MyClass> classes,DataRelationList dtList,String pname){
        Map<String,Integer> occurrence=new HashMap<>();
        Map<DTPair,List<String>> sharedDT=new HashMap<>();

        int size=0;
        for (DataRelation dr : dtList) {//data for
            if (!dr.isUsage()) {
                size++;
                String caller=dr.getCallerClass();
                String callee=dr.getCalleeClass();
                if ( classNames.contains(caller) && classNames.contains(callee)) {
                    DTPair dtPair=new DTPair(caller,callee);
                    if (!caller.equals(callee)) {
                        String type=dr.getType();
                        if(occurrence.containsKey(type)){
                            int count=occurrence.get(type);
                            occurrence.put(type,count+1);
                        }else{
                            occurrence.put(type,1);
                        }
                        if(sharedDT.containsKey(dtPair)){
                            List<String> list=sharedDT.get(dtPair);
                            list.add(type);
                            sharedDT.put(dtPair,list);
                        }else {
                            List<String> list=new ArrayList<>();
                            list.add(type);
                            sharedDT.put(dtPair,list);
                        }
                    }
                }
            }
        }//data for


        saveDataTypes(sharedDT,classes);
        System.out.println("idtf size:"+size);
        Map<String,Double> idtfMap=computIdtf(occurrence,size);
        calculateDataCloseness(pname,idtfMap);

    }


    private void calculateDataCloseness(String project,Map<String,Double> idftMap){
        List<DataDcy> dataDepencency=dataDPRepository.getDataDependencyFromProject(project);
//        System.out.println("cal data closeness for "+project+" dataDP size:"+dataDepencency.size());
        StringBuilder sb=new StringBuilder();
        for(DataDcy dataDcy:dataDepencency){
            MyClass fir=dataDcy.getFirClass();
            MyClass sec=dataDcy.getSecClass();
            List<String> intersectionDT=dataDcy.getDataTypes();
//            System.out.print("size inter:"+intersectionDT.size());
            List<String> unionDT=new ArrayList<>();

            List<DataDcy> allDT=new ArrayList<>();
            allDT.addAll(fir.getSharedDT());
            allDT.addAll(sec.getSharedDT());
//            System.out.print(" fir:"+fir.getSharedDT().size()+" sec:"+sec.getSharedDT().size());
            for(DataDcy temp:allDT){
                unionDT.addAll(temp.getDataTypes());
            }
//            System.out.print(" union:"+unionDT.size());
            double interesect=accumulate(intersectionDT,idftMap,1);
            double union=accumulate(unionDT,idftMap,1);

//            System.out.println(" inter num:"+interesect+" union num:"+union);
            if(0==union){
                continue;
            }
            double closeness=interesect/union;
            dataDcy.setCloseness(closeness);

            dataDPRepository.save(dataDcy,0);
//            if(0==closeness){
//                Set<String> set=new HashSet<>();
//                set.addAll(intersectionDT);
//                set.remove("DAOFactory");
//                if(set.isEmpty()){
//                    continue;
//                }
//                sb.append(closeness+System.lineSeparator()+set.toString()+System.lineSeparator());
//                set.clear();
//                set.addAll(unionDT);
//                sb.append(set.toString()+System.lineSeparator()+System.lineSeparator());
//            }
        }
//        FileUtil.createAndWriteFile(Paths.get("D:\\c.txt"),sb.toString());
    }

    public double accumulate(List<String> types,Map<String,Double> idftMap,double idftThreshold){
        double res=0;
        Set<String> set=new HashSet<>();
        set.addAll(types);
        for(String type:set){
//        for(String type:set){
            Double temp=idftMap.get(type);
            if(temp<idftThreshold){
                continue;
            }
            if(null!=temp){
                res+=temp;
            }
        }
        return res;
    }


    public void saveDataTypes(Map<DTPair,List<String>> sharedDT,Set<MyClass> classes){
        for(DTPair dt:sharedDT.keySet()){
            DataDcy dataDcy=new DataDcy();
            MyClass fir=getClassByName(dt.getCaller(),classes);
            MyClass sec=getClassByName(dt.getCallee(),classes);
            dataDcy.setFirClass(fir);
            dataDcy.setSecClass(sec);
            List<String> sharedList=sharedDT.get(dt);
            if(0==sharedList.size()){
                continue;
            }
            dataDcy.setDataTypes(sharedList);
            dataDPRepository.save(dataDcy);
        }
    }

    private Map<String,Double> computIdtf(Map<String,Integer> occurrence,int size){
        Map<String,Double> res=new HashMap<>();
        for(String type:occurrence.keySet()){
            double occur=occurrence.get(type);
            double idtf=Math.log10(size/(1.0*occur));
            res.put(type,idtf);
        }
        return res;
    }

    private void saveCallDP(Set<String> classNames,Set<MyClass> classes,CallRelationList callList,DataRelationList usage){
        Set<String> usag = new HashSet<>();
        for (DataRelation dataRelation : usage) {
            if (dataRelation.isUsage()) {
                if (classNames.contains(dataRelation.getCallerClass()) && classNames.contains(dataRelation.getCalleeClass())) {
                    if (!dataRelation.getCallerClass().equals(dataRelation.getCalleeClass())) {
                        String u = dataRelation.getCallerClass() + "#" + dataRelation.getCalleeClass();
                        if (!usag.contains(u)) {
                            CallRelation cr = new CallRelation(dataRelation.getCallerClass(), dataRelation.getCalleeClass(),
                                    dataRelation.getCallerMethod(), dataRelation.getCalleeMethod());
                            callList.add(cr);
                            usag.add(u);
                        }
                    }
                }
            }
        }

        Map<CallPair,Integer> callMap=new HashMap<>();
        for(CallRelation cr:callList){
            String caller=cr.getCallerClass();
            String callee=cr.getCalleeClass();
            if(classNames.contains(callee)&&classNames.contains(caller)&&!callee.equals(caller)){
                CallPair callPair=new CallPair(caller,callee);
                if(callMap.containsKey(callPair)){
                    int directCD=callMap.get(callPair);
                    callMap.put(callPair,directCD+1);
                }else{
                    callMap.put(callPair,1);
                }
            }
        }
        for(CallPair callPair:callMap.keySet()){
            MyClass callerClass=getClassByName(callPair.getCaller(),classes);
            MyClass calleeClass=getClassByName(callPair.getCallee(),classes);
            int directCD=callMap.get(callPair);
            CodeDcy codeDcy=new CodeDcy();
            codeDcy.setDirectCD(directCD);
            codeDcy.setOutClass(callerClass);
            codeDcy.setInClass(calleeClass);
            directDPRepository.save(codeDcy);
        }
    }

    private MyClass getClassByName(String name,Set<MyClass> classes){
        Iterator<MyClass> iterator=classes.iterator();
        while (iterator.hasNext()){
            MyClass temp=iterator.next();
            if(temp.getName().equals(name)){
                return temp;
            }
        }
        return null;
    }

    private Set<MyClass> saveAllClass(Set<String> clazz,String project){
        Set<MyClass> res=new HashSet<>();
        for(String str:clazz){
            MyClass temp=new MyClass();
            temp.setName(str);
            temp.setProject(project);
            temp=classRepository.save(temp);
            res.add(temp);
        }
        return res;
    }

    private Set<String> getClassSet(Path dir) {
        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            System.err.println("error get classes at dir:" + dir.toString());
            return Collections.emptySet();
        }

        Set<String> res = new HashSet<>();
        for (File f : dir.toFile().listFiles()) {
            if (f.getName().endsWith(".txt")) {
                res.add(FilenameUtils.getBaseName(f.getName()));
//                System.out.println("add:"+FilenameUtils.getBaseName(f.getName()));
            }
        }

        return res;
    }

    @Override
    public boolean findByName(String name){
        return classRepository.findByProjectName(name);
    }

    @Override
    public long DataDPSize(String pname) {
        return dataDPRepository.getDataDependencyFromProject(pname).size();

    }


}
