package ai.inno.clever.service.flowable.entities;

import ai.inno.clever.domain.FactoringRequest;

public class FlowableStatusDTO {
    private String requestId;
    private int percentage;
    private String message;
    private byte[] diagram;
    private FactoringRequest factoringRequest;


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
    public byte[] getDiagram() {
        return diagram;
    }

    public void setDiagram(byte[] diagram) {
        this.diagram = diagram;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FactoringRequest getFactoringRequest() {
        return factoringRequest;
    }

    public void setFactoringRequest(FactoringRequest factoringRequest) {
        this.factoringRequest = factoringRequest;
    }

    @Override
    public String toString() {
        return "FlowableStatusDTO{" +
            ", requestId='" + requestId + '\'' +
            ", percentage='" + percentage + '\'' +
            '}';
    }
}
