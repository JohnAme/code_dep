package com.se.dal;

import com.se.domain.CodeDcy;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CodeDcyRepository extends Neo4jRepository<CodeDcy,Long> {
    @Query("match (a:Class{project:{project}})-[r:CODE_DEPENDENCY]->() where r.closeness>={threshold} return *")
    Set<CodeDcy> getCodeCDFromProjectAndThreshold(@Param("project")String project, @Param("threshold")double threshold);

    @Query("match (a:Class{project:{project}})-[r:CODE_DEPENDENCY]->() return *")
    Set<CodeDcy> getCodeCDFromProject(@Param("project")String project);
}
