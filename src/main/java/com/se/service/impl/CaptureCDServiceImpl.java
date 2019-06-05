package com.se.service.impl;

import com.se.bcel.MyClassVisitor;
import com.se.dal.CodeDcyRepository;
import com.se.dal.DataDcyRepository;
import com.se.dal.MyClassRepository;
import com.se.domain.CodeDcy;
import com.se.domain.DataDcy;
import com.se.domain.MyClass;
import com.se.service.CaptureCDService;
import com.se.util.StringUtil;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CaptureCDServiceImpl implements CaptureCDService {
    private final MyClassRepository classRepository;
    private final CodeDcyRepository directCDRepository;
    private final DataDcyRepository dataDcyRepository;
    @Autowired
    public CaptureCDServiceImpl(MyClassRepository myClassRepository,
                                CodeDcyRepository directCDRepository,
                                DataDcyRepository dataDcyRepository){
        this.classRepository=myClassRepository;
        this.directCDRepository=directCDRepository;
        this.dataDcyRepository=dataDcyRepository;
    }

    @Override
    public void getCDFromJarFile(String path) {
        File file=new File(path);
        if(!file.exists()){
            System.err.println("file not found:"+path);
        }

        try (JarFile jarFile=new JarFile(file)){
            String projectName=StringUtil.getDirectory(file.getName());
            Enumeration<JarEntry> entryEnumeration=jarFile.entries();
            List<String> names=new ArrayList<>();
            Set<MyClass> classes=new HashSet<>();
            List<String> nameAsClass=new ArrayList<>();

            while(entryEnumeration.hasMoreElements()){
                JarEntry jarEntry=entryEnumeration.nextElement();
                if(jarEntry.isDirectory()||!jarEntry.getName().endsWith(".class")){
                    continue;
                }else{
                    String entryAsClass= StringUtil.jarEntryAsClassName(jarEntry.getName());
                    String name=StringUtil.getLeaf(entryAsClass);
                    String directory=StringUtil.getDirectory(entryAsClass);
                    if(!name.contains("$")){
                        MyClass myClass=new MyClass();
                        myClass.setPath(directory);
                        myClass.setName(name);
                        myClass.setProject(projectName);
                        myClass=classRepository.save(myClass);
                        classes.add(myClass);
                        names.add(jarEntry.getName());
                        nameAsClass.add(entryAsClass);
                    }
                }
            }

            Map<String,Integer> occurrence=new HashMap<>();
            Map<Set<String>,Set<String>> sharedData=new HashMap<>();
            for(String name:names){
                ClassParser cp=new ClassParser(path,name);
                try{
                    MyClassVisitor myClassVisitor=new MyClassVisitor(cp.parse(),nameAsClass);
                    myClassVisitor.setOccurrence(occurrence);
                    myClassVisitor.setSharedDataType(sharedData);
                    myClassVisitor=myClassVisitor.start();
                    Map<String, Integer> map=myClassVisitor.getClassCall();

                    createDirectCD(classes,map,StringUtil.jarEntryAsClassName(name));
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
            Map<String,Double> idftMap=calculateIdtf(occurrence);
            createDataDependency(classes,sharedData);
            calculateDataCloseness(projectName,idftMap);
            classRepository.setDirectCDClosenessFromProject(projectName);
        }catch (IOException e){
            System.err.println("Error while processing jar: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    @Transactional
    public void calculateDataCloseness(String projectName,Map<String,Double> idftMap){
//        List<DataDcy> dataDepencency=dataDcyRepository.getDataDependencyFromProject(projectName);
//        for(DataDcy dataDcy:dataDepencency){
//            MyClass fir=dataDcy.getFirClass();
//            MyClass sec=dataDcy.getSecClass();
//            Set<String> intersectionDT=dataDcy.getDataTypes();
//            Set<String> unionDT=new HashSet<>();
//
//            Set<DataDcy> allDT=new HashSet<>();
//            allDT.addAll(fir.getSharedDT());
//            allDT.addAll(sec.getSharedDT());
//            for(DataDcy temp:allDT){
//                unionDT.addAll(temp.getDataTypes());
//            }
//            double interesect=accumulate(intersectionDT,idftMap);
//            double union=accumulate(unionDT,idftMap);
//            if(0==union){
//                continue;
//            }
//            dataDcy.setCloseness(interesect/union);
//            dataDcyRepository.save(dataDcy,0);
//        }
    }


    public double accumulate(Set<String> types,Map<String,Double> idftMap){
        double res=0;
        for(String type:types){
            Double temp=idftMap.get(type);
            if(null!=temp){
                res+=temp;
            }
        }
        return res;
    }

//    @Transactional
    public void createDataDependency(Set<MyClass>classes,Map<Set<String>,Set<String>> dataTypes){
        for(Set<String> set:dataTypes.keySet()){
            Iterator<String> iterator=set.iterator();
            Object[] list=set.toArray();
            if(2!=list.length){
                continue;
            }
            String start=(String)list[0];
            String end=(String)list[1];
            MyClass startClass=null;
            MyClass endClass=null;
            for(MyClass myClass:classes){
                if(StringUtil.getLeaf(start).equals(myClass.getName())){
                    startClass=myClass;
                }
                if(StringUtil.getLeaf(end).equals(myClass.getName())){
                    endClass=myClass;
                }
            }
            if(null==startClass||null==endClass){
                continue;
            }
            DataDcy dataDcy=new DataDcy();
//            dataDcy.setDataTypes(dataTypes.get(set));
            dataDcy.setFirClass(startClass);
            dataDcy.setSecClass(endClass);
            dataDcyRepository.save(dataDcy,0);
        }
    }


    public Map<String,Double> calculateIdtf(Map<String, Integer> occurrence) {
        Map<String,Double> res=new HashMap<>();
        int N=0;
        for(String str:occurrence.keySet()){
            N+=occurrence.get(str);
        }
        if(0>N){
            System.err.println("idft calculation error");
            return res;
        }
        for(String str:occurrence.keySet()){
            int ndt=occurrence.get(str);
            double idtf=0;
            if(ndt>0){
                idtf=Math.log10(N/ndt);
                res.put(str,idtf);
            }
        }
        return res;

    }

//    @Transactional
    public void createDirectCD(Set<MyClass> classes,Map<String,Integer> call,String caller){
        Iterator<MyClass> iterator=classes.iterator();
        MyClass startClass=null;
        while(iterator.hasNext()){
            MyClass myClass=iterator.next();
            if(caller.equals(myClass.getPath()+"."+myClass.getName())){
                startClass=myClass;
                break;
            }
        }
        if(null==startClass){
            System.err.println("caller class not found:"+caller);
            return;
        }
        for(String callee:call.keySet()){
            MyClass endClass=null;
            iterator=classes.iterator();
            while(iterator.hasNext()){
                MyClass myClass=iterator.next();
                if(callee.equals(myClass.getPath()+"."+myClass.getName())){
                    endClass=myClass;
                    break;
                }
            }
            if(null==endClass){
                System.out.println("callee class not found:"+callee);
                continue;
            }
            int directCD=call.get(callee);
            CodeDcy codeDcy=new CodeDcy();
            codeDcy.setInClass(endClass);
            codeDcy.setOutClass(startClass);
            codeDcy.setDirectCD(directCD);
            directCDRepository.save(codeDcy);
        }
    }


    public void getCDFromJarFile(String path,long pid,String pname) {
        File file=new File(path);
        if(!file.exists()){
            System.err.println("file not found:"+path);
        }

        try (JarFile jarFile=new JarFile(file)){
//            String projectName=StringUtil.getDirectory(file.getName());
            Enumeration<JarEntry> entryEnumeration=jarFile.entries();
            List<String> names=new ArrayList<>();
            Set<MyClass> classes=new HashSet<>();
            List<String> nameAsClass=new ArrayList<>();

            while(entryEnumeration.hasMoreElements()){
                JarEntry jarEntry=entryEnumeration.nextElement();
                if(jarEntry.isDirectory()||!jarEntry.getName().endsWith(".class")){
                    continue;
                }else{
                    String entryAsClass= StringUtil.jarEntryAsClassName(jarEntry.getName());
                    String name=StringUtil.getLeaf(entryAsClass);
                    String directory=StringUtil.getDirectory(entryAsClass);
                    if(!name.contains("$")){
                        MyClass myClass=new MyClass();
                        myClass.setPath(directory);
                        myClass.setName(name);
//                        myClass.setProject(projectName);
                        myClass.setPid(pid);
                        System.out.println("pid"+myClass.getPid());
                        myClass=classRepository.save(myClass);
                        System.out.println("id:"+myClass.getId());
                        classes.add(myClass);
                        names.add(jarEntry.getName());
                        nameAsClass.add(entryAsClass);
                    }
                }
            }

            Map<String,Integer> occurrence=new HashMap<>();
            Map<Set<String>,Set<String>> sharedData=new HashMap<>();
            for(String name:names){
                ClassParser cp=new ClassParser(path,name);
                try{
                    MyClassVisitor myClassVisitor=new MyClassVisitor(cp.parse(),nameAsClass);
                    myClassVisitor.setOccurrence(occurrence);
                    myClassVisitor.setSharedDataType(sharedData);
                    myClassVisitor=myClassVisitor.start();
                    Map<String, Integer> map=myClassVisitor.getClassCall();

                    createDirectCD(classes,map,StringUtil.jarEntryAsClassName(name));
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
            Map<String,Double> idftMap=calculateIdtf(occurrence);
            createDataDependency(classes,sharedData);
            calculateDataCloseness(pid,idftMap);
            classRepository.setDirectCDClosenessFromPid(pid);
        }catch (IOException e){
            System.err.println("Error while processing jar: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    @Transactional
    public void calculateDataCloseness(long pid,Map<String,Double> idftMap){
//        Set<DataDcy> dataDepencency=dataDcyRepository.getDataDependencyFromPid(pid);
//        for(DataDcy dataDcy:dataDepencency){
//            MyClass fir=dataDcy.getFirClass();
//            MyClass sec=dataDcy.getSecClass();
////            Set<String> intersectionDT=dataDcy.getDataTypes();
//            Set<String> unionDT=new HashSet<>();
//
//            Set<DataDcy> allDT=new HashSet<>();
//            allDT.addAll(fir.getSharedDT());
//            allDT.addAll(sec.getSharedDT());
//            for(DataDcy temp:allDT){
//                unionDT.addAll(temp.getDataTypes());
//            }
//            double interesect=accumulate(intersectionDT,idftMap);
//            double union=accumulate(unionDT,idftMap);
//            if(0==union){
//                continue;
//            }
//            dataDcy.setCloseness(interesect/union);
//            dataDcyRepository.save(dataDcy,0);
//        }
    }
}
