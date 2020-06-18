package ksu.poma.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("reqmnts")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Requirements {
    @JsonProperty("req_desc")
    private String description;
    @JsonProperty("req_func")
    private boolean functionalFlag;

    public int getTaskTotalDurationInDays() {
        return taskTotalDurationInDays;
    }

    public void setTaskTotalDurationInDays(int taskTotalDurationInDays) {
        this.taskTotalDurationInDays = taskTotalDurationInDays;
    }

    public int getTaskExpendedDuration() {
        return taskExpendedDuration;
    }

    public void setTaskExpendedDuration(int taskExpendedDuration) {
        this.taskExpendedDuration = taskExpendedDuration;
    }

    @JsonProperty("tot_duration_days")
    private int taskTotalDurationInDays;

    @JsonProperty("exp_duration")
    private int taskExpendedDuration;

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
