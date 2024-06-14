package ai.inno.clever.service.dto;

import java.util.HashMap;

public class SignTaskDTO {

    private String requestId;
    private String taskId;

    private HashMap<String, String> variables = new HashMap<>();

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

    public HashMap<String, String> getVariables() {
        return variables;
    }

    public void setVariables(HashMap<String, String> variables) {
        this.variables = variables;
    }

    @Override
    public String toString() {
        return "SignTaskDTO{" +
            "requestId='" + requestId + '\'' +
            ", taskId='" + taskId + '\'' +
            ", variables='" + variables.toString() + '\'' +
            '}';
    }
}
