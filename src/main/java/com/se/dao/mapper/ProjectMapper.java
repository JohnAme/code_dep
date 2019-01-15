package com.se.dao.mapper;

import com.se.model.Project;

import java.util.List;

public interface ProjectMapper {
    public List<Project> findAllProject() throws Exception;

}
