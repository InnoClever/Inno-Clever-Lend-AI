package ai.inno.clever.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Relation.
 */
@org.springframework.data.elasticsearch.annotations.Document(indexName = "relation")
public class Relation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("name")
    private String name;

    @Field("relations")
    @JsonIgnoreProperties(value = { "collectionContainer", "relation" }, allowSetters = true)
    private Set<Relation> relations = new HashSet<>();

    @Field("value")
    @JsonIgnoreProperties(value = { "collectionContainer", "relation" }, allowSetters = true)
    private Set<Value> values = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getName() {
        return this.name;
    }

    public Relation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    public Relation getRelation() {
    //        return this.relation;
    //    }
    //
    //    public Relation relation(Relation relation) {
    //        this.setRelation(relation);
    //        return this;
    //    }
    //
    //    public void setRelation(Relation relation) {
    //        this.relation = relation;
    //    }

    public Set<Relation> getRelations() {
        return this.relations;
    }

    public Relation relations(Set<Relation> relations) {
        this.setRelations(relations);
        return this;
    }

    public Relation addRelations(Relation relation) {
        this.relations.add(relation);
        return this;
    }

    public Relation removeRelations(Relation relation) {
        this.relations.remove(relation);
        return this;
    }

    public void setRelations(Set<Relation> relations) {
        //        if (this.relations != null) {
        //            this.relations.forEach(i -> i.setRelation(null));
        //        }
        //        if (relations != null) {
        //            relations.forEach(i -> i.setRelation(this));
        //        }
        this.relations = relations;
    }

    public Set<Value> getValues() {
        return this.values;
    }

    public Relation values(Set<Value> values) {
        this.setValues(values);
        return this;
    }

    public Relation addValue(Value value) {
        this.values.add(value);
        return this;
    }

    public Relation removeValue(Value value) {
        this.values.remove(value);
        return this;
    }

    public void setValues(Set<Value> values) {
        //        if (this.values != null) {
        //            this.values.forEach(i -> i.setRelation(null));
        //        }
        //        if (values != null) {
        //            values.forEach(i -> i.setRelation(this));
        //        }
        this.values = values;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Relation)) {
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
        return "Relation{" +
            ", name='" + getName() + "'" +
            ", relations='" + getRelations() + "'" +
            "}";
    }
}
