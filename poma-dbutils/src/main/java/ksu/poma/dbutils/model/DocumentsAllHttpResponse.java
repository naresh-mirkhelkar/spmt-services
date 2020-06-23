package ksu.poma.dbutils.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentsAllHttpResponse {

    @JsonProperty("total_rows")
    private float total_rows;

    @JsonProperty("offset")
    private float offset;

    @JsonProperty("rows")
    ArrayList < Rows > rows = new ArrayList <Rows>();

    public ArrayList<Rows> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Rows> rows) {
        this.rows = rows;
    }

    public DocumentsAllHttpResponse(){}

    // Getter Methods
    public float getTotal_rows() {
        return total_rows;
    }

    public float getOffset() {
        return offset;
    }

    // Setter Methods

    public void setTotal_rows(float total_rows) {
        this.total_rows = total_rows;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }



    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Rows {
        @JsonProperty("id")
        private String id;
        @JsonProperty("key")
        private String key;

        @JsonProperty("value")
        Object ValueObject;

        public Rows() {}

        // Getter Methods
        public String getId() {
            return id;
        }

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return ValueObject;
        }

        // Setter Methods

        public void setId(String id) {
            this.id = id;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(Object valueObject) {
            this.ValueObject = valueObject;
        }
    }

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    public class Value {
//        @JsonProperty("rev")
//        private String rev;
//
//        public Value(){}
//
//        // Getter Methods
//        public String getRev() {
//            return rev;
//        }
//
//        // Setter Methods
//
//        public void setRev(String rev) {
//            this.rev = rev;
//        }
//    }
}
