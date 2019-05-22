package com.se.dal;

import com.se.domain.DataDcy;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DataDcyRepository extends Neo4jRepository<DataDcy,Long> {


    @Query("match (a:Class{project:{project}})-[r:DATA_DEPENDENCY]->() return r")
    Set<DataDcy> getDataDependencyFromProject(@Param("project")String project);


    @Query("match (a:Class{project:{project}})-[r:DATA_DEPENDENCY]->() where r.closeness>={threshold} return r")
    Set<DataDcy> getDataCDFromProjectAndThreshold(@Param("project")String project,@Param("threshold")double threshold);
}
