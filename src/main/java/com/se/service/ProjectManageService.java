package com.se.service;

import com.se.entity.CandidateEntry;
import com.se.entity.Project;
import com.se.entity.SqlClass;
import com.se.entity.UCase;
import com.se.util.CandidateEntryDeprecatyed;

import java.util.List;

public interface ProjectManageService {
    List<CandidateEntryDeprecatyed> calculateCandidateList(long pid);

    public List<CandidateEntry> computeCandidateList(long pid, String ucname, String algo);

    List<SqlClass> getCode(long pid);

    String getTempUC();//todo For UC file format ask Gao

    boolean existProject(String pname,long uid);

    boolean createNewProject(String pname,long ownerId) ;

    boolean addUC(String name,String content,long pid);

    Project findProjectByPnameAndUid(String pname,long ownerId);

    UCase findUCByPidAndName(long pid,String name);


}
