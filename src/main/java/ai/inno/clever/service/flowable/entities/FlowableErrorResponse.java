package ai.inno.clever.service.flowable.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FlowableErrorResponse {

    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
