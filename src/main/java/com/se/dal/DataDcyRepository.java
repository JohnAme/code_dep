package com.se.dal;

import com.se.domain.DataDcy;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DataDcyRepository extends Neo4jRepository<DataDcy,Long> {


    @Query("match (a:Class{project:{project}})-[r:DATA_DEPENDENCY]->() return *")
    List<DataDcy> getDataDependencyFromProject(@Param("project")String project);

    @Query("match (a:Class{pid:{pid}})-[r:DATA_DEPENDENCY]->() return *")
    Set<DataDcy> getDataDependencyFromPid(@Param("pid")long pid);


    @Query("match (a:Class{project:{project}})-[r:DATA_DEPENDENCY]->() where r.closeness>={threshold} return *")
    Set<DataDcy> getDataCDFromProjectAndThreshold(@Param("project")String project,@Param("threshold")double threshold);


    Long deleteByCloseness(double closeness);
}
