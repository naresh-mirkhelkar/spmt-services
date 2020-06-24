package ksu.poma.dbutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import ksu.poma.dbutils.model.CustomHttpResponse;
import ksu.poma.dbutils.model.DocumentHttpResponse;
import ksu.poma.dbutils.model.DocumentsAllHttpResponse;
import ksu.poma.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DocumentClientTest {

    private CouchClient couchClient = null;
    private DocumentClient documentClient =null;
    CustomHttpResponse customHttpResponse = null;
    String idToDelete;

    @BeforeEach
    public void initialize() {
        couchClient = new CouchClient("http://localhost:5984",5,"admin","password");
        documentClient =  new DocumentClient(couchClient,"prj-mgmt", new ObjectMapper());
        customHttpResponse = documentClient.get("3d48324a18484bf2f2f14cdbd10023a2");
    }

    @Test
    void testGet() {
        assertNotNull(customHttpResponse);
        assertNotNull(customHttpResponse.getContent());
        assertEquals(200, customHttpResponse.getStatusCode());
        System.out.println(customHttpResponse.getContent());
    }

    @Test
    void testGetAll() {
       DocumentsAllHttpResponse documentsAllHttpResponse = documentClient.getAll();
       assertNotNull(documentsAllHttpResponse);
       documentsAllHttpResponse.getRows().forEach(x-> {
           System.out.println(x.getId());
       });
//       System.out.println(documentsAllHttpResponse.getTotal_rows());
    }

    @Test
    void testUpdate() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ProjectInfo projectInfo = objectMapper.readerFor(ProjectInfo.class).readValue(customHttpResponse.getContent());
            assertNotNull(projectInfo);
            System.out.println(projectInfo.getProjectDetails().getProjectDescription());
            projectInfo.getProjectDetails().setProjectDescription("Modifying description from test");
            System.out.println(projectInfo.getProjectDetails().getProjectDescription());
            DocumentHttpResponse documentHttpResponse = documentClient.update(projectInfo);
            assertNotNull(documentHttpResponse);
            assertEquals(true, documentHttpResponse.isOk());
            customHttpResponse = documentClient.get("3d48324a18484bf2f2f14cdbd10023a2");
            assertNotNull(customHttpResponse);
            assertNotNull(customHttpResponse.getContent());
            assertEquals(200, customHttpResponse.getStatusCode());
            System.out.println(customHttpResponse.getContent());

        }catch (JsonProcessingException jex) {
            System.out.println(jex.getMessage());
        }

    }

    @Test
    void testCreate() {

        ProjectInfo projectInfo = new ProjectInfo();
        int random = new Random().nextInt();
        ProjectDetails projectDetails = new ProjectDetails();
        projectDetails.setProjectName("AnotherProject" + String.valueOf(random));
        projectInfo.set_id(projectDetails.getProjectName());
        projectDetails.setProjectDescription("This is a test for another project using a random number:" + String.valueOf(random));
        projectInfo.setProjectDetails(projectDetails);

        Map<String, RoleType> teamMemRoles = new HashMap<>();
        teamMemRoles.put("Giovanni", RoleType.TL);
        teamMemRoles.put("Sameeraja", RoleType.TM);
        teamMemRoles.put("Srija",RoleType.PM);
        teamMemRoles.put("Naresh",RoleType.TM);
        TeamDetails teamDetails = new TeamDetails();
        teamDetails.setTeamStructure(teamMemRoles);
        projectInfo.setTeamDetails(teamDetails);

        List<Requirements> requirements = new ArrayList<>();
        Requirements requirements1 = new Requirements();
        requirements1.setDescription("This is test functional requirement " + String.valueOf(random));
        requirements1.setFunctionalFlag(true);
        requirements1.setTaskTotalDurationInDays(10);
        requirements1.setTaskExpendedDuration(6);
        requirements.add(requirements1);


        Requirements requirements2 = new Requirements();
        requirements2.setDescription("This is test non-functional requirement " + String.valueOf(random));
        requirements2.setFunctionalFlag(false);
        requirements2.setTaskTotalDurationInDays(17);
        requirements2.setTaskExpendedDuration(7);
        requirements.add(requirements2);
        projectInfo.setRequirements(requirements);

        DocumentHttpResponse documentHttpResponse = documentClient.create(projectInfo);
        assertNotNull(documentHttpResponse);
        assertNotNull(documentHttpResponse.getId());
        //Assign the value for next test
        idToDelete = documentHttpResponse.getId();
        System.out.println(documentHttpResponse.getId());
    }

    @Test
    @Disabled
    void testDelete(){
      DocumentHttpResponse documentHttpResponse = documentClient.delete("ae1a46c8-eb58-4452-9b83-cf786715e8e0","1-d2464599770ffba3b6797359b75e30f6");
      assertNotNull(documentHttpResponse);
      assertTrue(documentHttpResponse.isOk());
    }


}