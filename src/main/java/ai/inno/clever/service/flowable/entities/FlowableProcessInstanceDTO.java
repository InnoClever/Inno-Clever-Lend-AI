package ai.inno.clever.service.flowable.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class FlowableProcessInstanceDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("url")
    private String url;

    @JsonProperty("name")
    private Object name;

    @JsonProperty("businessKey")
    private Object businessKey;

    @JsonProperty("suspended")
    private Boolean suspended;

    @JsonProperty("ended")
    private Boolean ended;

    @JsonProperty("processDefinitionId")
    private String processDefinitionId;

    @JsonProperty("processDefinitionUrl")
    private String processDefinitionUrl;

    @JsonProperty("processDefinitionName")
    private String processDefinitionName;

    @JsonProperty("processDefinitionDescription")
    private Object processDefinitionDescription;

    @JsonProperty("activityId")
    private Object activityId;

    @JsonProperty("startUserId")
    private String startUserId;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("endTime")
    private String endTime;

    @JsonProperty("durationInMillis")
    private Long durationInMillis;

    @JsonProperty("deletedReason")
    private String deletedReason;

    @JsonProperty("variables")
    private List<FlowableVariableDTO> variables;

    @SerializedName("variablesFormatted")
    @Expose
    private Map<String, Object> variablesFormatted = null;

    @JsonProperty("callbackId")
    private Object callbackId;

    @JsonProperty("callbackType")
    private Object callbackType;

    @JsonProperty("referenceId")
    private Object referenceId;

    @JsonProperty("referenceType")
    private Object referenceType;

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("completed")
    private Boolean completed;

    @JsonProperty("tasks")
    private List<FlowableTaskDTO> tasks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(Object businessKey) {
        this.businessKey = businessKey;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Boolean getEnded() {
        return ended;
    }

    public void setEnded(Boolean ended) {
        this.ended = ended;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionUrl() {
        return processDefinitionUrl;
    }

    public void setProcessDefinitionUrl(String processDefinitionUrl) {
        this.processDefinitionUrl = processDefinitionUrl;
    }

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }

    public Object getProcessDefinitionDescription() {
        return processDefinitionDescription;
    }

    public void setProcessDefinitionDescription(Object processDefinitionDescription) {
        this.processDefinitionDescription = processDefinitionDescription;
    }

    public Object getActivityId() {
        return activityId;
    }

    public void setActivityId(Object activityId) {
        this.activityId = activityId;
    }

    public String getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getDurationInMillis() {
        return durationInMillis;
    }

    public void setDurationInMillis(Long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

    public String getDeletedReason() {
        return deletedReason;
    }

    public void setDeletedReason(String deletedReason) {
        this.deletedReason = deletedReason;
    }

    public List<FlowableVariableDTO> getVariables() {
        return variables;
    }

    public void setVariables(List<FlowableVariableDTO> variables) {
        this.variables = variables;
    }

    public Object getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(Object callbackId) {
        this.callbackId = callbackId;
    }

    public Object getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(Object callbackType) {
        this.callbackType = callbackType;
    }

    public Object getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Object referenceId) {
        this.referenceId = referenceId;
    }

    public Object getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(Object referenceType) {
        this.referenceType = referenceType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Map<String, Object> getVariablesFormatted() {
        return variablesFormatted;
    }

    public void setVariablesFormatted(Map<String, Object> variablesFormatted) {
        this.variablesFormatted = variablesFormatted;
    }

    public List<FlowableTaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<FlowableTaskDTO> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return (
            "ProcessInstanceDTO{" +
            "id='" +
            id +
            '\'' +
            ", url='" +
            url +
            '\'' +
            ", name=" +
            name +
            ", businessKey=" +
            businessKey +
            ", suspended=" +
            suspended +
            ", ended=" +
            ended +
            ", processDefinitionId='" +
            processDefinitionId +
            '\'' +
            ", processDefinitionUrl='" +
            processDefinitionUrl +
            '\'' +
            ", processDefinitionName='" +
            processDefinitionName +
            '\'' +
            ", processDefinitionDescription=" +
            processDefinitionDescription +
            ", activityId=" +
            activityId +
            ", startUserId='" +
            startUserId +
            '\'' +
            ", startTime='" +
            startTime +
            '\'' +
            ", variables=" +
            variables +
            ", callbackId=" +
            callbackId +
            ", callbackType=" +
            callbackType +
            ", referenceId=" +
            referenceId +
            ", referenceType=" +
            referenceType +
            ", tenantId='" +
            tenantId +
            '\'' +
            ", completed=" +
            completed +
            '}'
        );
    }
}
