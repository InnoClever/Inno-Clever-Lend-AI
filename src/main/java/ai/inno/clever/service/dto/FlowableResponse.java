package ai.inno.clever.service.dto;

public class FlowableResponse {
    private String collector;
    private String taxCode;
    private String requestId;
    private String taskId;

    public String getCollector() {
        return collector;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "FlowableResponse{" +
            "collector='" + collector + '\'' +
            ", taxCode='" + taxCode + '\'' +
            ", requestId='" + requestId + '\'' +
            ", taskId='" + taskId + '\'' +
            '}';
    }
}
