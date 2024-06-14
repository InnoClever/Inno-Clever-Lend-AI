package ai.inno.clever.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * A CollectionContainer.
 */
@Mapping(mappingPath = "/config/es/collection-container-mapping.json")
@Setting(settingPath = "/config/es/collection-container-settings.json")
@Document(collection = "collection_container")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "collectioncontainer")
public class CollectionContainer implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("type")
    private String type;


    @Field("taskId")
    private String taskId;

    @Field("created")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant created;

    @Field("updated")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant updated;

    @DBRef
    @Field("factoringRequest")
    @JsonIgnoreProperties(value = {"company", "debtors"}, allowSetters = true)
    private FactoringRequest factoringRequest;


    @Field("values")
    // @org.springframework.data.elasticsearch.annotations.Field(type= FieldType.Nested)
    private Map<String, Object> values = new HashMap<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CollectionContainer id(String id) { //restart
        this.id = id;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public String type(String type) {
        this.type = type;
        return this.type;
    }   //restart

    public void setType(String type) {
        this.type = type;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "CollectionContainer{" +
            "id='" + id + '\'' +
            ", type='" + type + '\'' +
            ", taskId='" + taskId + '\'' +
            ", factoringRequest=" + factoringRequest +
            ", values=" + values +
            '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        CollectionContainer clonedContainer = (CollectionContainer) super.clone();
        clonedContainer.setId(null);
        clonedContainer.setValues(new HashMap<>(this.getValues()));
        clonedContainer.setTaskId(null);

        return clonedContainer;
    }

}
