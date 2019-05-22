package com.se.service;

import com.se.entity.SqlClass;
import com.se.util.CandidateEntry;

import java.util.List;

public interface ProjectManageService {
    List<CandidateEntry> calculateCandidateList(long pid);

    List<SqlClass> getCode(long pid);

    String getTempUC();//todo For UC file format ask Gao

}
