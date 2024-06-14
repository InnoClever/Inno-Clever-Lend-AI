package ai.inno.clever.domain.rbrotest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A CollectionContainer.
 */
//@Mapping(mappingPath = "/config/es/collection-container.json")
//@Document(collection = "collection_container")
@JsonIgnoreProperties(ignoreUnknown = true)
//@org.springframework.data.elasticsearch.annotations.Document(indexName = "collectioncontainer")
public class CollectionContainer implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String type;

//    @Field("created")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
//    private Instant created;
//
//    @Field("updated")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
//    private Instant updated;

    private String created;
    @JsonIgnoreProperties(value = { "company" }, allowSetters = true)
    private FactoringRequest factoringRequest;


    private Map<String, Object> values = new HashMap<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CollectionContainer id(String id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public String type(String type) {
        this.type = type;
        return this.type;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FactoringRequest getFactoringRequest() {
        return this.factoringRequest;
    }

    public CollectionContainer factoringRequest(FactoringRequest factoringRequest) {
        this.setFactoringRequest(factoringRequest);
        return this;
    }

    public void setFactoringRequest(FactoringRequest factoringRequest) {
        this.factoringRequest = factoringRequest;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    //    public Set<Relation> getRelations() {
//        return this.relations;
//    }
//
//    public CollectionContainer addRelations(Relation relation) {
//        this.relations.add(relation);
//        return this;
//    }
//
//    public CollectionContainer removeRelations(Relation relation) {
//        this.relations.remove(relation);
//        return this;
//    }
//
//    public void setRelations(Set<Relation> relations) {
//        this.relations = relations;
//    }
//
//    public Set<Value> getValues() {
//        return this.values;
//    }
//
//    public CollectionContainer addValue(Value value) {
//        this.values.add(value);
//        return this;
//    }
//
//    public CollectionContainer removeValue(Value value) {
//        this.values.remove(value);
//        return this;
//    }
//
//    public void setValues(Set<Value> values) {
//        this.values = values;
//    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollectionContainer)) {
            return false;
        }
        return id != null && id.equals(((CollectionContainer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

//    public Instant getCreated() {
//        return created;
//    }
//
//    public void setCreated(Instant created) {
//        this.created = created;
//    }
//
//    public Instant getUpdated() {
//        return updated;
//    }
//
//    public void setUpdated(Instant updated) {
//        this.updated = updated;
//    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollectionContainer{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}

