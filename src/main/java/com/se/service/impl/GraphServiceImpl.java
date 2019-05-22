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
        return asD3(classes,directCD,dataCD);
    }
    private Map<String,Object> asD3(Set<MyClass> myClasses,Set<CodeDcy> directCD,Set<DataDcy> dataCD){
        List<Map<String,Object>> nodes=new ArrayList<>();
        List<Map<String,Object>> links=new ArrayList<>();

        Iterator<MyClass> iterator=myClasses.iterator();
        int i=0;
        int source=0;
        while (iterator.hasNext()){
            MyClass myClass=iterator.next();
            Set<CodeDcy> direct=myClass.getOutDirectCD();

            if(addClassToNode(nodes,myClass)){
                i++;
            }
            Iterator<CodeDcy> directIterator=direct.iterator();
            while (directIterator.hasNext()){
                CodeDcy codeDcy=directIterator.next();
                MyClass otherClass=codeDcy.getInClass();
                Map<String,Object> temp=new HashMap<>();
                temp.put("name",StringUtil.getPathAndName(otherClass.getPath(),otherClass.getName()));
                temp.put("title",otherClass.getName());
                int target=nodes.indexOf(temp);
                if(-1==target){
                    nodes.add(temp);
                    target=i++;
                }
                addDependencToLink(links, source, target, "directCD",codeDcy.getCloseness());
            }
            Set<DataDcy> data=myClass.getSharedDT();
            Iterator<DataDcy> dataIterator=data.iterator();
            while (dataIterator.hasNext()){
                DataDcy dataDcy=dataIterator.next();
                MyClass otherClass=dataDcy.otherClass(myClass.getId());
                Map<String,Object> temp=new HashMap<>();
                temp.put("name",StringUtil.getPathAndName(otherClass.getPath(),otherClass.getName()));
                temp.put("title",otherClass.getName());
                int target=nodes.indexOf(temp);
                if(-1==target){
                    nodes.add(temp);
                    target=i++;
                }
                addDependencToLink(links, source, target, "dataCD",dataDcy.getCloseness());
            }
            source++;
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
    private boolean addClassToNode(List<Map<String,Object>> nodes,MyClass myClass){
        Map<String,Object> temp=new HashMap<>();
        temp.put("name", StringUtil.getPathAndName(myClass.getPath(),myClass.getName()));
        temp.put("title",myClass.getName());
        if(!nodes.contains(temp)){
            nodes.add(temp);
            return true;
        }
        return false;
    }
}
