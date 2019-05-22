package com.se.dal;

import com.se.domain.MyClass;
import com.se.util.SharedData;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MyClassRepository extends Neo4jRepository<MyClass, Long> {
    MyClass findByName(String name);

    Set<MyClass> findByProject(String project);

    @Depth(2)
    @Query("Match (c:Class{project:{project}})-[r1:CODE_DEPENDENCY]-() \n" +
            "Where r1.closeness>={directClosenessThreshold}\n" +
            "return distinct c as class\n" +
            "union \n" +
            "Match (d:Class{project:{project}})-[r2:DATA_DEPENDENCY]-()\n" +
            "where r2.closeness>={dataClosenessThreshold}\n" +
            "return distinct d as class")
    Set<MyClass> findByProjectAndThreshold(@Param("project")String project,
                                          @Param("directClosenessThreshold")double directThreshold,
                                          @Param("dataClosenessThreshold")double dataThreshold);

    @Query("match (a:Class {project: {project}})-[r:CODE_DEPENDENCY]->(b)\n" +
            "with r,a,b,\n" +
            "reduce(acc1=0,v1 in [(a)-[r1:CODE_DEPENDENCY]->()|r1.directCD]|acc1+v1) as l1,\n" +
            "reduce(acc2=0,v2 in [()-[r2:CODE_DEPENDENCY]->(b)|r2.directCD]|acc2+v2) as l2\n" +
            "where (l1+l2)>0\n" +
            "set r.closeness=2.0*r.directCD/(l1+l2)")
    public void setDirectCDClosenessFromProject(@Param("project")String projectName);

//    @Query("match (a)-[r:DATA_DEPENDENCY]->(b) where r.sharedData is not null\n" +
//            "with id(r) as id,\n" +
//            "r.sharedData as xDT,\n" +
//            "reduce(l1=[],v1 in [(a)-[r1:DATA_DEPENDENCY]->()|r1.sharedData]|l1+v1) as red1,\n" +
//            "reduce(l2=[],v2 in [()-[r2:DATA_DEPENDENCY]->(b)|r2.sharedData]|l2+v2) as red2\n" +
//            "return id,xDT,red1+red2 as uDT")
//    public List<SharedData> getSharedData();
//
//
//    @Query("match ()-[r:DATA_DEPENDENCY]->() where id(r)={0} \n" +
//            "set r.value={1}")
//    public void setDataClosenessValue(Long id,Double value);

}
