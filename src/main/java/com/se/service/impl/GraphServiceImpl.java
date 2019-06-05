package com.se.service.impl;

import com.se.dal.CodeDcyRepository;
import com.se.dal.DataDcyRepository;
import com.se.dal.MyClassRepository;
import com.se.domain.CodeDcy;
import com.se.domain.DataDcy;
import com.se.domain.MyClass;
import com.se.service.GraphService;
import com.se.util.SharedData;
import com.se.util.SharedDataHelper;
import com.se.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GraphServiceImpl implements GraphService {
    private final MyClassRepository myClassRepository;
    private final CodeDcyRepository codeDcyRepository;
    private final DataDcyRepository dataDcyRepository;

    @Autowired
    public GraphServiceImpl(MyClassRepository myClassRepository,
                            CodeDcyRepository codeDcyRepository,
                            DataDcyRepository dataDcyRepository){
        this.myClassRepository=myClassRepository;
        this.codeDcyRepository=codeDcyRepository;
        this.dataDcyRepository=dataDcyRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getCDCGraph(String project, double callThreshold, double dataThreshold) {
        Set<MyClass> classes=myClassRepository.findByProjectAndThreshold(project,callThreshold,dataThreshold);
        Set<CodeDcy> directCD=codeDcyRepository.getCodeCDFromProjectAndThreshold(project,callThreshold);
        Set<DataDcy> dataCD=dataDcyRepository.getDataCDFromProjectAndThreshold(project,dataThreshold);

        return asD3(classes);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String,Object> getRegion(String project,String cname,double dc,double cd){
//        System.out.println("flag0");
//        if(myClassRepository.hasDirectNeighbor(project,cname,dc)||myClassRepository.hasDataNeighbor(project,cname,cd)){
//
//            System.out.println("flag1");
//            return asD3(set);
//        }else{
//            System.out.println("flag2");
//        }
//        System.out.println("flag3");
        Set<MyClass> set=myClassRepository.getDirectRegion(project,cname,dc);
        System.out.println("region size:"+set.size());
        for(MyClass c:set){
            System.out.println(c.getName());
        }
        MyClass center=myClassRepository.findByProjectAndName(project,cname);
        set.add(center);
        System.out.println("center:"+center.getName());
        System.out.println(center.getInDirectCD().size());
        System.out.println(center.getOutDirectCD().size());
        System.out.println(center.getSharedDT().size());
        return asD3(set);
    }


    private Map<String,Object> asD3(Set<MyClass> myClasses){
        List<Map<String,Object>> nodes=new ArrayList<>();
        List<Map<String,Object>> links=new ArrayList<>();

        Iterator<MyClass> iterator=myClasses.iterator();

        while (iterator.hasNext()){
            MyClass myClass=iterator.next();
            Set<CodeDcy> direct=myClass.getOutDirectCD();

            int source=getOrInsertToNodes(nodes,myClass.getPath(),myClass.getName());

            Iterator<CodeDcy> directIterator=direct.iterator();
            while (directIterator.hasNext()){
                CodeDcy codeDcy=directIterator.next();
                MyClass otherClass=codeDcy.getInClass();
                int target=getOrInsertToNodes(nodes,otherClass.getPath(),otherClass.getName());

                addDependencToLink(links, source, target, "directCD",codeDcy.getCloseness());
            }

            Iterator<DataDcy> dataIterator=myClass.getSharedDT().iterator();
            while (dataIterator.hasNext()){
                DataDcy dataDcy=dataIterator.next();
                MyClass otherClass=dataDcy.otherClass(myClass.getId());
                int target=getOrInsertToNodes(nodes,otherClass.getPath(),otherClass.getName());

                addDependencToLink(links, source, target, "dataCD",dataDcy.getCloseness());
            }
        }
        Map<String,Object> res=new HashMap<>();
        res.put("nodes",nodes);
        res.put("links",links);
        return res;
    }

    private boolean existTargetAndSource(List<Map<String,Object>> nodes,String source,String target){
        boolean s=false,t=false;
        for(Map<String,Object> temp:nodes){
            String fullName=(String)temp.get("name");
            if(!s&&fullName.equals(source)){
                s=true;
                continue;
            }
            if(!t&&fullName.equals(target)){
                t=true;
            }
        }
        return s&&t;
    }

    private boolean addDependencToLink(List<Map<String,Object>> links,int source,int target,String type,Double closeness){
        Map<String,Object> temp=new HashMap<>();
        temp.put("source",source);
        temp.put("target",target);
        temp.put("closeness",closeness);
        temp.put("type",type);
        if(!links.contains(temp)){
            links.add(temp);
            return true;
        }
        return false;
    }

    private int getOrInsertToNodes(List<Map<String,Object>> nodes,String path,String name){
        Map<String,Object> temp=new HashMap<>();
        temp.put("name",StringUtil.getPathAndName(path,name));
        temp.put("title",name);
        int res=nodes.indexOf(temp);
        if(-1==res){
            nodes.add(temp);
            res=nodes.size()-1;
        }
        return res;
    }

    private boolean addClassToNode(List<Map<String,Object>> nodes,String path,String name){
        Map<String,Object> temp=new HashMap<>();
        temp.put("name", StringUtil.getPathAndName(path,name));
        temp.put("title",name);
        if(!nodes.contains(temp)){
            nodes.add(temp);
            return true;
        }
        return false;
    }
}
