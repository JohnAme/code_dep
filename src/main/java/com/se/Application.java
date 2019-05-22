package com.se;


import com.se.config.StorageProperties;
import com.se.dal.ProjectRepository;
import com.se.dal.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableNeo4jRepositories("com.se.dal")
public class Application {

    public static void main(String[] args){
    SpringApplication.run(Application.class,args);
}
}
