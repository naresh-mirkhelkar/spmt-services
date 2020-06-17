package ksu.poma.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamDetails {
    @JsonProperty("team_details")
    public Map <String, RoleType> teamStructure;

    public Map<String, RoleType> getTeamStructure() {
        return teamStructure;
    }

    public void setTeamStructure(Map<String, RoleType> teamStructure) {
        this.teamStructure = teamStructure;
    }
}
