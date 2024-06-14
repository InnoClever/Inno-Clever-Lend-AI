package ai.inno.clever.service.dto;

public class CollectorRequest {
    private String cui;
    private String taskId;
    private String requestId;
    private String callbackUrl;

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    @Override
    public String toString() {
        return "CollectorRequest{" +
            "cui='" + cui + '\'' +
            ", taskId='" + taskId + '\'' +
            ", requestId='" + requestId + '\'' +
            ", callbackUrl='" + callbackUrl + '\'' +
            '}';
    }
}
