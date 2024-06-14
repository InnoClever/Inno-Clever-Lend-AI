package ai.inno.clever.service.flowable.entities;

public class FlowableGroupDTO {

    private String id;
    private String url;
    private String name;
    private String type;
    private String key;
    private String tenantId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return (
            "FlowableGroupDTO{" +
            "id='" +
            id +
            '\'' +
            ", url='" +
            url +
            '\'' +
            ", name='" +
            name +
            '\'' +
            ", type='" +
            type +
            '\'' +
            ", key='" +
            key +
            '\'' +
            ", tenantId='" +
            tenantId +
            '\'' +
            '}'
        );
    }
}
