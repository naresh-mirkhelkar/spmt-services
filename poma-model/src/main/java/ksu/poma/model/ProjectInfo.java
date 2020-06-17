package ksu.poma.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/*
This is the main class object which will be returned to the consumer
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectInfo extends RevisionedDocument implements Serializable {

    @JsonProperty("prj_dtls")
    private ProjectDetails projectDetails;

    @JsonProperty("team_dtls")
    private TeamDetails teamDetails;

    @JsonProperty("rqmts")
    private Requirements requirements;

    public ProjectDetails getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(ProjectDetails projectDetails) {
        this.projectDetails = projectDetails;
    }

    public TeamDetails getTeamDetails() {
        return teamDetails;
    }

    public void setTeamDetails(TeamDetails teamDetails) {
        this.teamDetails = teamDetails;
    }

    public Requirements getRequirements() {
        return requirements;
    }

    public void setRequirements(Requirements requirements) {
        this.requirements = requirements;
    }

}
