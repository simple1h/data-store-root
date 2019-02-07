package com.simple.domain_model.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@ToString
@NoArgsConstructor
@Entity
@Table  (name = "Property", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Property {

//    TODO заменить идентификатор, оставить только name
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propId;

    @Embedded
    @NotNull
    private EntityInfo info;

    @NotNull
    private DataType type;

    Property(EntityInfo info, DataType type) {
        this.info = info;
        this.type = type;
    }

    public Long getPropId() {
        return propId;
    }

    public DataType getType() {
        return type;
    }

    public EntityInfo getInfo() {
        return info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property)) return false;
        Property property = (Property) o;
        return Objects.equals(propId, property.propId) &&
                Objects.equals(info, property.info) &&
                type == property.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(propId, info, type);
    }
}
