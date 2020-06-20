package ksu.poma.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class ProjectInfoTest {

    @Test
    public void testSerializeProjectInfo() throws JsonProcessingException {
        ProjectInfo projectInfo = new ProjectInfo();

        ProjectDetails projectDetails = new ProjectDetails();
        projectDetails.setProjectName("ProjectPlanningAndManagement");
        projectDetails.setProjectDescription("This project is about maintaining information about other projects");
        projectInfo.setProjectDetails(projectDetails);

        Map<String, RoleType> teamMemRoles = new HashMap<>();
        teamMemRoles.put("Giovanni", RoleType.PM);
        teamMemRoles.put("Sameeraja", RoleType.TL);
        teamMemRoles.put("Srija",RoleType.TL);
        teamMemRoles.put("Naresh",RoleType.TM);
        TeamDetails teamDetails = new TeamDetails();
        teamDetails.setTeamStructure(teamMemRoles);
        projectInfo.setTeamDetails(teamDetails);

        List<Requirements> requirements = new ArrayList<>();
        Requirements requirements1 = new Requirements();
        requirements1.setDescription("This is test functional requirement");
        requirements1.setFunctionalFlag(true);
        requirements1.setTaskTotalDurationInDays(10);
        requirements1.setTaskExpendedDuration(6);
        requirements.add(requirements1);


        Requirements requirements2 = new Requirements();
        requirements2.setDescription("This is test non-functional requirement");
        requirements2.setFunctionalFlag(false);
        requirements2.setTaskTotalDurationInDays(17);
        requirements2.setTaskExpendedDuration(7);
        requirements.add(requirements2);
        projectInfo.setRequirements(requirements);
        projectInfo.set_id("id_1123456");
        projectInfo.set_rev("rev_123456");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonValue = objectMapper.writeValueAsString(projectInfo);

        System.out.println(jsonValue);
        assertNotNull(jsonValue);
    }

}