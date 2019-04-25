package com.se;


import com.se.dal.ProjectRepository;
import com.se.dal.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories("com.se.dal")
public class Application {

//    private final ProjectRepository repository;
//
//    public Application(ProjectRepository repository){
//        this.repository=repository;
//    }
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println(this.repository.findUserByUname("jerry").getUname());
//        System.out.println(this.repository.findUserGroupByPId(1000000).size());
//    }
    public static void main(String[] args){
    SpringApplication.run(Application.class,args);
}
}
