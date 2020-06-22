package ksu.poma.dbutils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import ksu.poma.dbutils.model.CustomHttpResponse;
import ksu.poma.dbutils.model.DocumentHttpResponse;
import ksu.poma.model.ProjectInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class DocumentClientTest {

    private CouchClient couchClient = null;
    private DocumentClient documentClient =null;
    CustomHttpResponse customHttpResponse = null;

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
    void getAll() {
    }

    @Test
    void update() {
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
    void create() {

    }
}