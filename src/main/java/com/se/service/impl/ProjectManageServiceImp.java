package com.se.service.impl;

import com.se.dal.ProjectRepository;
import com.se.entity.MyClass;
import com.se.service.ProjectManageService;
import com.se.util.CandidateEntry;
import com.se.util.TempUcUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectManageServiceImp implements ProjectManageService {
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<CandidateEntry> calculateCandidateList(long pid) {
        List<CandidateEntry> res=new ArrayList<>();
        res.add(new CandidateEntry("MonitorAdverseEventAction",0.3492));
        res.add(new CandidateEntry("ReportAdverseEvent ",0.2723));
        res.add(new CandidateEntry("MonitorAdverseEvents",0.2568));
        res.add(new CandidateEntry("EditPersonnelAction",0.2349));
        res.add(new CandidateEntry("AdverseEventDAO",0.2286));
        res.add(new CandidateEntry("AdverseEventBeanLoader",0.1926));
        res.add(new CandidateEntry("SendMessageAction",0.1338));
        res.add(new CandidateEntry("EmailUtil",0.0964));
        res.add(new CandidateEntry("PatientDAO",0.0529));
        res.add(new CandidateEntry("AuthDAO",0.0369));
        return res;
    }

    @Override
    public List<MyClass> getCode(long pid) {
        return projectRepository.findCodeByPId(pid);
    }

    @Override
    public String getTempUC() {
        return TempUcUtil.uc18;
    }
}
