package com.se.service;

import com.se.entity.MyClass;
import com.se.util.CandidateEntry;

import java.util.List;
import java.util.Map;

public interface ProjectManageService {
    List<CandidateEntry> calculateCandidateList(long pid);

    List<MyClass> getCode(long pid);

}
