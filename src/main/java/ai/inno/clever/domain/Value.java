package ai.inno.clever.domain;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * A Value.
 */
@org.springframework.data.elasticsearch.annotations.Document(indexName = "value")
public class Value implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("field")
    private String field;

    @Field("value")
    private String value;


    @Field("valuesList")
    private List<String> valuesList;

    //    @JsonIgnoreProperties(value = { "collectionContainer", "relation", "relations", "values" }, allowSetters = true)
    //    private Relation relation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getField() {
        return this.field;
    }

    public Value field(String field) {
        this.field = field;
        return this;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return this.value;
    }

    public Value value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getValuesList() {
        return valuesList;
    }

    public void setValuesList(List<String> valuesList) {
        this.valuesList = valuesList;
    }

    //    public Relation getRelation() {
    //        return this.relation;
    //    }
    //
    //    public Value relation(Relation relation) {
    //        this.setRelation(relation);
    //        return this;
    //    }
    //
    //    public void setRelation(Relation relation) {
    //        this.relation = relation;
    //    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Value)) {
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Value{" +
            ", field='" + getField() + "'" +
            ", value='" + getValue() + "'" +
            ", valuesList='" + getValuesList() + "'" +
//            ", relations='" + getRelations() + "'" +
            "}";
    }
}
