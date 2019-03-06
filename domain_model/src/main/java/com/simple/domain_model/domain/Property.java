package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Entity
@Table  (name = "Property", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Property extends DataModelObject {

    @NotNull
    private DataType type;

    Property(EntityInfo info, DataType type) {
        this.info = info;
        this.type = type;
    }

    public DataType getType() {
        return type;
    }
}
