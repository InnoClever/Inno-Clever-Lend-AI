package ai.inno.clever.service.flowable.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlowableContentItemDTO {

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("version")
    @Expose
    public int version;

    @SerializedName("versionInfo")
    @Expose
    public String versionInfo;

    @SerializedName("size")
    @Expose
    public int size;

    @SerializedName("mimeType")
    @Expose
    public String mimeType;

    @SerializedName("definitionId")
    @Expose
    public String definitionId;

    @SerializedName("definitionName")
    @Expose
    public String definitionName;

    @SerializedName("taskId")
    @Expose
    public String taskId;

    @SerializedName("processInstanceId")
    @Expose
    public String processInstanceId;

    @SerializedName("scopeId")
    @Expose
    public String scopeId;

    @SerializedName("scopeType")
    @Expose
    public String scopeType;

    @SerializedName("contentStoreId")
    @Expose
    public String contentStoreId;

    @SerializedName("contentStoreName")
    @Expose
    public String contentStoreName;

    @SerializedName("contentAvailable")
    @Expose
    public boolean contentAvailable;

    @SerializedName("versionParentId")
    @Expose
    public String versionParentId;

    @SerializedName("tenantId")
    @Expose
    public String tenantId;

    @SerializedName("created")
    @Expose
    public String created;

    @SerializedName("createdBy")
    @Expose
    public String createdBy;

    @SerializedName("lastModified")
    @Expose
    public String lastModified;

    @SerializedName("lastModifiedBy")
    @Expose
    public String lastModifiedBy;

    @SerializedName("url")
    @Expose
    public String url;

    @SerializedName("type")
    @Expose
    public Object type;

    @SerializedName("subType")
    @Expose
    public String subType;

    @SerializedName("state")
    @Expose
    public String state;

    @SerializedName("subState")
    @Expose
    public String subState;

    @SerializedName("parentFolderId")
    @Expose
    public String parentFolderId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
    }

    public String getDefinitionName() {
        return definitionName;
    }

    public void setDefinitionName(String definitionName) {
        this.definitionName = definitionName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
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

    public String getContentStoreId() {
        return contentStoreId;
    }

    public void setContentStoreId(String contentStoreId) {
        this.contentStoreId = contentStoreId;
    }

    public String getContentStoreName() {
        return contentStoreName;
    }

    public void setContentStoreName(String contentStoreName) {
        this.contentStoreName = contentStoreName;
    }

    public boolean isContentAvailable() {
        return contentAvailable;
    }

    public void setContentAvailable(boolean contentAvailable) {
        this.contentAvailable = contentAvailable;
    }

    public String getVersionParentId() {
        return versionParentId;
    }

    public void setVersionParentId(String versionParentId) {
        this.versionParentId = versionParentId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubState() {
        return subState;
    }

    public void setSubState(String subState) {
        this.subState = subState;
    }

    public String getParentFolderId() {
        return parentFolderId;
    }

    public void setParentFolderId(String parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    @Override
    public String toString() {
        return (
            "FlowableContentItemDTO{" +
            "id='" +
            id +
            '\'' +
            ", name='" +
            name +
            '\'' +
            ", version=" +
            version +
            ", versionInfo='" +
            versionInfo +
            '\'' +
            ", size=" +
            size +
            ", mimeType='" +
            mimeType +
            '\'' +
            ", definitionId='" +
            definitionId +
            '\'' +
            ", definitionName='" +
            definitionName +
            '\'' +
            ", taskId='" +
            taskId +
            '\'' +
            ", processInstanceId='" +
            processInstanceId +
            '\'' +
            ", scopeId='" +
            scopeId +
            '\'' +
            ", scopeType='" +
            scopeType +
            '\'' +
            ", contentStoreId='" +
            contentStoreId +
            '\'' +
            ", contentStoreName='" +
            contentStoreName +
            '\'' +
            ", contentAvailable=" +
            contentAvailable +
            ", versionParentId='" +
            versionParentId +
            '\'' +
            ", tenantId='" +
            tenantId +
            '\'' +
            ", created='" +
            created +
            '\'' +
            ", createdBy='" +
            createdBy +
            '\'' +
            ", lastModified='" +
            lastModified +
            '\'' +
            ", lastModifiedBy='" +
            lastModifiedBy +
            '\'' +
            ", url='" +
            url +
            '\'' +
            ", type=" +
            type +
            ", subType='" +
            subType +
            '\'' +
            ", state='" +
            state +
            '\'' +
            ", subState='" +
            subState +
            '\'' +
            ", parentFolderId='" +
            parentFolderId +
            '\'' +
            '}'
        );
    }
}
