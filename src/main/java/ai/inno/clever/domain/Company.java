package ai.inno.clever.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
//restart
/**
 * A Company.
 */
@Document(collection = "company")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("cui")
    private String cui;

    @DBRef
    @Field("factoringRequest")
    @JsonIgnoreProperties(value = {"company", "collectionContainers"}, allowSetters = true)
    private Set<FactoringRequest> factoringRequests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Company id(String id) {
        this.id = id;
        return this;
    }

    public String getCui() {
        return this.cui;
    }

    public Company cui(String cui) {
        this.cui = cui;
        return this;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public Set<FactoringRequest> getFactoringRequests() {
        return this.factoringRequests;
    }

    public Company factoringRequests(Set<FactoringRequest> factoringRequests) {
        this.setFactoringRequests(factoringRequests);
        return this;
    }

    public Company addFactoringRequest(FactoringRequest factoringRequest) {
        this.factoringRequests.add(factoringRequest);
        factoringRequest.setCompany(this);
        return this;
    }

    public Company removeFactoringRequest(FactoringRequest factoringRequest) {
        this.factoringRequests.remove(factoringRequest);
        factoringRequest.setCompany(null);
        return this;
    }

    public void setFactoringRequests(Set<FactoringRequest> factoringRequests) {
        if (this.factoringRequests != null) {
            this.factoringRequests.forEach(i -> i.setCompany(null));
        }
        if (factoringRequests != null) {
            factoringRequests.forEach(i -> i.setCompany(this));
        }
        this.factoringRequests = factoringRequests;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", cui='" + getCui() + "'" +
            "}";
    }
}
