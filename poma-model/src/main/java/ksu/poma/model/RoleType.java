package ksu.poma.model;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("role_type")
public enum RoleType {
    PM("ProjectManager"),
    TM("TeamMember"),
    TL("TeamLead");

    private final String roleType;
    RoleType(final String roleType) {
        this.roleType = roleType;
    }
    public String getRoleType() { return  this.roleType; }
}
