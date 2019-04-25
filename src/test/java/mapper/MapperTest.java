//package mapper;
//
//import com.se.dal.ProjectRepository;
//import com.se.entity.Project;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//@RunWith(Springrunner.class)
//@MybatisTest
//class MapperTest{
//
//    @Autowired
//    private ProjectRepository projectRepository;
//
//    @Test
//    public void insertProject() throws Exception {
//        Project pj=new Project();
//        pj.setPname("rakan");
//        pj.setOwnerId(22333);
//        projectRepository.insertProject(pj);
//
//    }
//}