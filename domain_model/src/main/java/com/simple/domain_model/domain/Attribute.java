package com.simple.domain_model.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@NoArgsConstructor
@Entity
@Table(name = "Attribute", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
//todo добавить поддержку типов аттрибутов
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attr_owner_gen")
    @SequenceGenerator(name = "attr_owner_gen", sequenceName = "attr_owner_gen", allocationSize = 10, initialValue = 10000)
    private Long attributeId;

    @Embedded
    @NotNull
    private EntityInfo info;

    @Embedded
    private AttributeProperties properties = new AttributeProperties();

    @ManyToMany(mappedBy = "attributes")
    private Set<ObjectClass> objectClasses = new LinkedHashSet<>();

    private DataType type;

    Attribute(EntityInfo info, DataType type) {
        this.info = info;
        this.type = type;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public EntityInfo getInfo() {
        return info;
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
        this.objectClasses.add(aClass);
        if (!aClass.attributes().contains(this)) aClass.addAttribute(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attribute)) return false;
        Attribute attribute = (Attribute) o;
        return Objects.equals(attributeId, attribute.attributeId) &&
                Objects.equals(info, attribute.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeId, info);
    }
}
