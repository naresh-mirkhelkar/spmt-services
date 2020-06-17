package ksu.poma.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class RevisionedDocument {

    @JsonProperty("_id")
    private String _id;

    @JsonProperty("_rev")
    private String _rev;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RevisionedDocument that = (RevisionedDocument) o;
        return _id.equals(that._id) &&
                _rev.equals(that._rev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, _rev);
    }
}
