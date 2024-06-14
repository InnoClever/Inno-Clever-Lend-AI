package ai.inno.clever.service.dto;

import ai.inno.clever.domain.enumeration.CollectorStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;

public class TaskResult {

    private CollectorStatus status; // OK, WARNING, ERROR
    private String errorMsg; // 'Debtor1 taxCode is invalid' <- Display on UI
    private String errorStackTrace; // 'HttpClientError: kesysfin api_key invalid"
    private String type;

    @JsonIgnoreProperties(value = {"factoringRequest"}, allowSetters = true)
    private HashMap<String, Object> data = new HashMap<>();



    public CollectorStatus getStatus() {
        return status;
    }

    public void setStatus(CollectorStatus status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorStackTrace() {
        return errorStackTrace;
    }

    public void setErrorStackTrace(String errorStackTrace) {
        this.errorStackTrace = errorStackTrace;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DataContainerDTO{" +
//            "taskId='" + taskId + '\'' +
//            ", requestId='" + requestId + '\'' +
            ", status='" + status + '\'' +
            ", errorMsg='" + errorMsg + '\'' +
            ", data=" + data.toString() +
            '}';
    }
}
