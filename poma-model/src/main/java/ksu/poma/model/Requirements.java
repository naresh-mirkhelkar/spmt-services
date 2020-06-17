package ksu.poma.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("reqmnts")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Requirements {
    @JsonProperty("req_desc")
    String description;
    @JsonProperty("req_func")
    boolean functionalFlag;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFunctionalFlag() {
        return functionalFlag;
    }

    public void setFunctionalFlag(boolean functionalFlag) {
        this.functionalFlag = functionalFlag;
    }
}
