package com.se.dal;

import com.se.domain.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author pdtyreus
 * @author Mark Angrish
 */
public interface PersonRepository {

    Person findByName(String name);

}