package ai.inno.clever.service.flowable.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FlowableVariableDTO implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @JsonProperty("value")
    @SerializedName("value")
    private Object value;

    @SerializedName("scope")
    private String scope;

    public FlowableVariableDTO() {}

    public FlowableVariableDTO(String name, String value) {
        this.name = name;
        this.type = "string";
        this.value = value;
    }

    public FlowableVariableDTO(String name, int value) {
        this.name = name;
        this.type = "integer";
        this.value = value;
    }

    public FlowableVariableDTO(String name, Long value) {
        this.name = name;
        this.type = "long";
        this.value = value;
    }

    public FlowableVariableDTO(String name, Boolean value) {
        this.name = name;
        this.type = "boolean";
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return (
            "ProcessVariableDTO{" +
            "name='" +
            name +
            '\'' +
            ", type='" +
            type +
            '\'' +
            ", value='" +
            value +
            '\'' +
            ", scope='" +
            scope +
            '\'' +
            '}'
        );
    }
}
