package ai.inno.clever.domain;

import ai.inno.clever.domain.enumeration.Provider;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "provider_call")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "providercall")
public class ProviderCall {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String factoringRequestId;

    @Field("provider")
    @NotNull
    private Provider provider;

    @Field("providerMethod")
    private String providerMethod;

    @Field("timestamp")
    private Instant timestamp;

    @Field("info")
    private Map<String, Object> info = new HashMap<>();

    public String getFactoringRequestId() {
        return factoringRequestId;
    }

    public void setFactoringRequestId(String factoringRequestId) {
        this.factoringRequestId = factoringRequestId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getProviderMethod() {
        return providerMethod;
    }

    public void setProviderMethod(String providerMethod) {
        this.providerMethod = providerMethod;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProviderCall)) {
            return false;
        }
        return id != null && id.equals(((ProviderCall) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "ProviderCall{" +
            "id='" + id + '\'' +
            ", factoringRequestId='" + factoringRequestId + '\'' +
            ", provider='" + provider + '\'' +
            ", providerMethod='" + providerMethod + '\'' +
            ", timestamp=" + timestamp +
            ", info=" + info +
            '}';
    }
}
