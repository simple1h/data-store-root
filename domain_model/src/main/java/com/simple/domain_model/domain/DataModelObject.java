package com.simple.domain_model.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class DataModelObject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "data_model_id_gen")
    @SequenceGenerator(name = "data_model_id_gen", sequenceName = "data_model_id_gen", allocationSize = 10, initialValue = 10000)
    protected Long id;

    @Embedded
    @NotNull
    protected EntityInfo info;

    public Long getId() {
        return id;
    }

    public EntityInfo getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "DataModelObject{" +
                "id=" + id +
                ", info=" + info +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataModelObject)) return false;
        DataModelObject that = (DataModelObject) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, info);
    }
}
