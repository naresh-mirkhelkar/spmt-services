package ksu.poma.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import ksu.poma.model.ProjectInfo;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ProjectServiceTest {

    @Test
    void testGetProjectInformation() {
        String storeInfo = given()
                .when().get("/prj-mgmt/get/KSUProject2")
                .then()
                .statusCode(HttpStatus.SC_OK).extract().asString();
        System.out.println(storeInfo);
    }

}