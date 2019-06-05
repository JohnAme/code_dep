package com.se.dal;

import com.se.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectRepository {
    List<Project> findProjectsByUId(@Param("uid")long uid);
    List<Project> findProjectsWithCodeAndDocByUId(@Param("uid")long uid);

    Project findProjectByPId(@Param("pid")long pid);
    Project findProjectByPnameAndUid(@Param ("pname")String name,@Param("uid") long uid);

    SqlClass findClassByPidAndPathAndClassname(@Param("pid")long pid,@Param("className")String className,@Param("path")String path);
    SqlClass findClassById(@Param("classId")long classId);

    List<SqlClass> findClassWithTFByPid(@Param("pid")long pid);

    public List<CandidateEntry> findCandidateByPidAndUCNameAndAlgo(@Param("pid")long pid,@Param("ucname")String ucname,@Param("algorithm")String algorithm);

    public List<ClassTF> findClassTFBy(@Param("pid")long pid);

    public List<PJIdf> findPJIdfByPid(@Param("pid")long pid);

    List<User> findUserGroupByPId(@Param("pid")long pid);
    List<SqlClass> findCodeByPId(@Param("pid")long pid);
    List<UCase> findDocByPId(@Param("pid")long pid);

    int countClassByPid(@Param("pid")long pid);

    boolean insertProject(Project project) throws Exception;
    boolean insertProjectWithCodeAndDoc(Project project);
    boolean insertCode(SqlClass sqlClass);
    boolean insertUC(UCase uc);
    boolean insertClassTF(ClassTF cLassTF);
    boolean insertPJIdf(PJIdf pjIdf);
    boolean insertCandidate(CandidateEntry candidateEntry);

    boolean updateProject(Project project);
    boolean updateProjectWithCodeAndDoc(Project project);
    boolean updateCode(SqlClass sqlClass);
    boolean updateUC(UCase uc);

    boolean deleteProjectById(@Param("pid")long pid);
    boolean deleteCode(@Param("classId")long classId);
    boolean deleteUC(UCase uc);

    boolean existProjectByName(@Param("pname")String pname);
    boolean existProjectByNameAndUid(@Param("pname")String pname,@Param("uid")long uid);
}
