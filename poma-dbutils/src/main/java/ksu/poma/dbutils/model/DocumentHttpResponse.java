package ksu.poma.dbutils.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentHttpResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("rev")
    private String revision;
    @JsonProperty("ok")
    private boolean ok;
    @JsonProperty("error")
    private String error;
    @JsonProperty("reason")
    private String reason;

    public DocumentHttpResponse() {
        this.ok = Boolean.FALSE;
    }

    @JsonIgnore
    public boolean isOk() {
        return this.ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRevision() {
        return this.revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
