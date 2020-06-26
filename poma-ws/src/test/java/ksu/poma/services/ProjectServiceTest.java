package ksu.poma.services;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
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

    @Test
    void testGetProjectInformation_fail() {
        given()
                .when().get("/prj-mgmt/get/KSUProjec")
                .then()
                .assertThat().body("error", equalTo("not_found"));

    }

}