package com.se.service.impl;

import com.se.dal.ProjectRepository;
import com.se.dal.UCRepository;
import com.se.entity.CandidateEntry;
import com.se.entity.Project;
import com.se.entity.SqlClass;
import com.se.entity.UCase;
import com.se.service.ProjectManageService;
import com.se.util.CandidateEntryDeprecatyed;
import com.se.util.TempUcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectManageServiceImp implements ProjectManageService {

    private ProjectRepository projectRepository;
    private UCRepository ucRepository;
    @Autowired
    public ProjectManageServiceImp(ProjectRepository projectRepository,UCRepository ucRepository){
        this.projectRepository=projectRepository;
        this.ucRepository=ucRepository;
    }

    @Override
    public List<CandidateEntryDeprecatyed> calculateCandidateList(long pid) {
        List<CandidateEntryDeprecatyed> res=new ArrayList<>();
        res.add(new CandidateEntryDeprecatyed("MonitorAdverseEventAction",0.3492));
        res.add(new CandidateEntryDeprecatyed("ReportAdverseEvent ",0.2723));
        res.add(new CandidateEntryDeprecatyed("MonitorAdverseEvents",0.2568));
        res.add(new CandidateEntryDeprecatyed("EditPersonnelAction",0.2349));
        res.add(new CandidateEntryDeprecatyed("AdverseEventDAO",0.2286));
        res.add(new CandidateEntryDeprecatyed("AdverseEventBeanLoader",0.1926));
        res.add(new CandidateEntryDeprecatyed("SendMessageAction",0.1338));
        res.add(new CandidateEntryDeprecatyed("EmailUtil",0.0964));
        res.add(new CandidateEntryDeprecatyed("PatientDAO",0.0529));
        res.add(new CandidateEntryDeprecatyed("AuthDAO",0.0369));
        return res;

//        return null;
    }

    public List<CandidateEntry> computeCandidateList(long pid,String ucname,String algo){
        List<CandidateEntry> list=projectRepository.findCandidateByPidAndUCNameAndAlgo(pid,ucname,algo);
        if(null!=list&&list.size()>0){
            return list;
        }
        return null;
    }


    @Override
    public List<SqlClass> getCode(long pid) {
        return projectRepository.findCodeByPId(pid);
    }

    @Override
    public String getTempUC() {
        return TempUcUtil.uc18;
    }

    @Override
    public boolean existProject(String pname,long uid) {
        return projectRepository.existProjectByNameAndUid(pname,uid);
    }

    @Override
    public boolean createNewProject(String pname, long ownerId)  {
        Project project=new Project();
        project.setPname(pname);
        project.setOwnerId(ownerId);
        try {
            boolean res = projectRepository.insertProject(project);
            return res;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addUC(String name, String content, long pid) {
        return ucRepository.insertUC(pid,content,name);
    }

    @Override
    public Project findProjectByPnameAndUid(String pname, long ownerId) {
        return projectRepository.findProjectByPnameAndUid(pname,ownerId);
    }

    @Override
    public UCase findUCByPidAndName(long pid, String name) {
        return ucRepository.findUCByPidAndUCName(pid,name);
    }
}
