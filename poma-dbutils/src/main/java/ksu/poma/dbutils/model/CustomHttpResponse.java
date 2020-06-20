package ksu.poma.dbutils.model;

import java.util.Objects;

public class CustomHttpResponse {
    private int statusCode;
    private String content;

    public CustomHttpResponse(){}

    public CustomHttpResponse(int statusCode, String content) {
        this.statusCode = statusCode;
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomHttpResponse that = (CustomHttpResponse) o;
        return statusCode == that.statusCode &&
                content.equals(that.content);
    }

    public int hashCode() {
        return Objects.hash(statusCode, content);
    }
}
