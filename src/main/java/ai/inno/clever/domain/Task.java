package ai.inno.clever.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
//
    private String status;
    private Solution solution;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return "Task{" +
            "status='" + status + '\'' +
            ", solution=" + solution +
            '}';
    }
}
