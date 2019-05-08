package com.se.dal;

import com.se.domain.MyClass;
import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface MyClassRepository extends Neo4jRepository<MyClass, Long> {
    MyClass findByName(String name);
}
