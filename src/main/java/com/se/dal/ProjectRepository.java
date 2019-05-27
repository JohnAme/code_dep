package com.se.dal;

import com.se.entity.Project;
import com.se.entity.SqlClass;
import com.se.entity.UCase;
import com.se.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectRepository {
    List<Project> findProjectsByUId(@Param("uid")long uid);
    List<Project> findProjectsWithCodeAndDocByUId(@Param("uid")long uid);

    Project findProjectByPId(@Param("pid")long pid);
    List<User> findUserGroupByPId(@Param("pid")long pid);
    List<SqlClass> findCodeByPId(@Param("pid")long pid);
    List<UCase> findDocByPId(@Param("pid")long pid);

    boolean insertProject(Project project) throws Exception;
    boolean insertProjectWithCodeAndDoc(Project project);
    boolean insertCode(SqlClass sqlClass);
    boolean insertUC(UCase uc);

    boolean updateProject(Project project);
    boolean updateProjectWithCodeAndDoc(Project project);
    boolean updateCode(SqlClass sqlClass);
    boolean updateUC(UCase uc);

    boolean deleteProjectById(@Param("pid")long pid);
    boolean deleteCode(@Param("classId")long classId);
    boolean deleteUC(UCase uc);
}
