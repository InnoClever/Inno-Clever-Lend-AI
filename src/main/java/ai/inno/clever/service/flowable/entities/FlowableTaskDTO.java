package ai.inno.clever.service.flowable.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class FlowableTaskDTO {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("owner")
    @Expose
    private String owner;

    @SerializedName("assignee")
    @Expose
    private String assignee;

    @SerializedName("delegationState")
    @Expose
    private String delegationState;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("createTime")
    @Expose
    private String createTime;

    @SerializedName("dueDate")
    @Expose
    private String dueDate;

    @SerializedName("priority")
    @Expose
    private Integer priority;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("endTime")
    private String endTime;

    @JsonProperty("durationInMillis")
    private Long durationInMillis;

    @JsonProperty("workTimeInMillis")
    private Long workTimeInMillis;

    @SerializedName("suspended")
    @Expose
    private Boolean suspended;

//    @SerializedName("documentation")
//    @Expose
//    private Boolean documentation;

    @SerializedName("claimTime")
    @Expose
    private String claimTime;

    @SerializedName("taskDefinitionKey")
    @Expose
    private String taskDefinitionKey;

    @SerializedName("scopeDefinitionId")
    @Expose
    private String scopeDefinitionId;

    @SerializedName("scopeId")
    @Expose
    private String scopeId;

    @SerializedName("scopeType")
    @Expose
    private String scopeType;

    @SerializedName("tenantId")
    @Expose
    private String tenantId;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("formKey")
    @Expose
    private String formKey;

    @SerializedName("parentTaskId")
    @Expose
    private String parentTaskId;

    @SerializedName("parentTaskUrl")
    @Expose
    private String parentTaskUrl;

    @SerializedName("executionId")
    @Expose
    private String executionId;

    @SerializedName("executionUrl")
    @Expose
    private String executionUrl;

    @SerializedName("processInstanceId")
    @Expose
    private String processInstanceId;

    @SerializedName("processInstanceUrl")
    @Expose
    private String processInstanceUrl;

    @SerializedName("processDefinitionId")
    @Expose
    private String processDefinitionId;

    @SerializedName("processDefinitionUrl")
    @Expose
    private String processDefinitionUrl;

    @SerializedName("variables")
    @Expose
    private List<FlowableVariableDTO> variables = null;

    @SerializedName("variablesFormatted")
    @Expose
    private Map<String, Object> variablesFormatted = null;

    public Map<String, Object> getVariablesFormatted() {
        return variablesFormatted;
    }

    public void setVariablesFormatted(Map<String, Object> variablesFormatted) {
        this.variablesFormatted = variablesFormatted;
    }

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getDelegationState() {
        return delegationState;
    }

    public void setDelegationState(String delegationState) {
        this.delegationState = delegationState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public Long getWorkTimeInMillis() {
        return workTimeInMillis;
    }

    public void setWorkTimeInMillis(Long workTimeInMillis) {
        this.workTimeInMillis = workTimeInMillis;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public String getClaimTime() {
        return claimTime;
    }

    public void setClaimTime(String claimTime) {
        this.claimTime = claimTime;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getScopeDefinitionId() {
        return scopeDefinitionId;
    }

    public void setScopeDefinitionId(String scopeDefinitionId) {
        this.scopeDefinitionId = scopeDefinitionId;
    }

    public String getScopeId() {
        return scopeId;
    }

    public void setScopeId(String scopeId) {
        this.scopeId = scopeId;
    }

    public String getScopeType() {
        return scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = scopeType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public String getParentTaskUrl() {
        return parentTaskUrl;
    }

    public void setParentTaskUrl(String parentTaskUrl) {
        this.parentTaskUrl = parentTaskUrl;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getExecutionUrl() {
        return executionUrl;
    }

    public void setExecutionUrl(String executionUrl) {
        this.executionUrl = executionUrl;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceUrl() {
        return processInstanceUrl;
    }

    public void setProcessInstanceUrl(String processInstanceUrl) {
        this.processInstanceUrl = processInstanceUrl;
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

    public List<FlowableVariableDTO> getVariables() {
        return variables;
    }

    public TaskImportance getTaskImportance() throws JsonProcessingException {
        if(this.description == null){
            return null;
        }

        JsonNode parent= new ObjectMapper().readTree(this.description);
        String content = parent.path("importance").asText();


        return TaskImportance.valueOf(parent.path("importance").asText());
    }

    public void setVariables(List<FlowableVariableDTO> variables) {
        this.variables = variables;
    }

    @Override
    public String toString() {
        return (
            "TaskDTO{" +
            "id='" +
            id +
            '\'' +
            ", url='" +
            url +
            '\'' +
            ", owner='" +
            owner +
            '\'' +
            ", assignee='" +
            assignee +
            '\'' +
            ", delegationState='" +
            delegationState +
            '\'' +
            ", name='" +
            name +
            '\'' +
            ", description='" +
            description +
            '\'' +
            ", createTime='" +
            createTime +
            '\'' +
            ", dueDate='" +
            dueDate +
            '\'' +
            ", priority=" +
            priority +
            ", suspended=" +
            suspended +
            ", claimTime='" +
            claimTime +
            '\'' +
            ", taskDefinitionKey='" +
            taskDefinitionKey +
            '\'' +
            ", scopeDefinitionId='" +
            scopeDefinitionId +
            '\'' +
            ", scopeId='" +
            scopeId +
            '\'' +
            ", scopeType='" +
            scopeType +
            '\'' +
            ", tenantId='" +
            tenantId +
            '\'' +
            ", category='" +
            category +
            '\'' +
            ", formKey='" +
            formKey +
            '\'' +
            ", parentTaskId='" +
            parentTaskId +
            '\'' +
            ", parentTaskUrl='" +
            parentTaskUrl +
            '\'' +
            ", executionId='" +
            executionId +
            '\'' +
            ", executionUrl='" +
            executionUrl +
            '\'' +
            ", processInstanceId='" +
            processInstanceId +
            '\'' +
            ", processInstanceUrl='" +
            processInstanceUrl +
            '\'' +
            ", processDefinitionId='" +
            processDefinitionId +
            '\'' +
            ", processDefinitionUrl='" +
            processDefinitionUrl +
            '\'' +
            ", variablesFormatted='" +
            variablesFormatted +
            '\'' +
            ", variables=" +
            variables +
            '}'
        );
    }
}
