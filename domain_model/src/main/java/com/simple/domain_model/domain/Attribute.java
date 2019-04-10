package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "Attribute", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Attribute extends DataModelObject{

    private DataType type;

    @Embedded
    private AttributeProperties properties = new AttributeProperties();

    @ManyToMany(mappedBy = "attributes")
    private Set<ObjectClass> objectClasses = new LinkedHashSet<>();

    Attribute(EntityInfo info, DataType type) {
        this.info = info;
        this.type = type;
    }

    public DataType getType() {
        return type;
    }

    public void setProperties(AttributeProperties properties) {
        this.properties = properties;
    }

    public AttributeProperties getProperties() {
        return properties;
    }

    public Attribute addObjectClass(ObjectClass aClass) {
        if (Objects.nonNull(aClass)) {
            this.objectClasses.add(aClass);
            if (!aClass.attributes().contains(this)) aClass.addAttribute(this);
        }
        return this;
    }

    public Set<ObjectClass> objectClasses() {
        return Collections.unmodifiableSet(objectClasses);
    }

    public Attribute removeObjectClass(ObjectClass aClass) {
        if (Objects.nonNull(aClass)) {
            this.objectClasses.remove(aClass);
            if (aClass.attributes().contains(this)) aClass.removeAttribute(this);
        }
        return this;
    }
}
