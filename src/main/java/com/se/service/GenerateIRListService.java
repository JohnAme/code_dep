package com.se.service;

import com.se.entity.CandidateEntry;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GenerateIRListService {
    public boolean generateDocCorpus(String path);

    public boolean generateCodeCorpus(String path);

    public boolean initialNewProject(long pid, String pname,MultipartFile uc,MultipartFile code,MultipartFile compile);

    public List<CandidateEntry> computeCandidateList(long pid, String ucname, String algo);
}
