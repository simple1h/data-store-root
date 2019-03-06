package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "ObjectClass", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ObjectClass extends DataModelObject{

    @Embedded
    private ObjectClassProperties properties = new ObjectClassProperties();

    @ManyToOne
    private ObjectClass parent;

    @OneToMany (mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ObjectClass> childs = new LinkedHashSet<>();

    @ManyToMany (fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<Attribute> attributes = new LinkedHashSet<>();

    ObjectClass(EntityInfo info) {
        this.info = info;
    }

    public ObjectClassProperties properties() {
        return properties;
    }

    public ObjectClass properties(ObjectClassProperties properties) {
        this.properties = properties;
        return this;
    }

    public ObjectClass addChild(ObjectClass aClass) {
        this.childs.add(aClass.parent(this));
        return this;
    }

    private ObjectClass parent(ObjectClass aClass) {
        this.parent = aClass;
        return this;
    }

    public ObjectClass addChildNode(ObjectClass aClass) {
        this.childs.add(aClass.parent(this));
        return aClass;
    }

    public ObjectClass removeChild(ObjectClass aClass) {
        this.childs.remove(aClass.parent(null));
        return this;
    }

    public ObjectClass parent() {
        return this.parent;
    }

    public Set<ObjectClass> childs() {
        return Collections.unmodifiableSet(childs);
    }

    public ObjectClass addAttribute(Attribute attribute) {
        this.attributes.add(attribute);
        if (!attribute.objectClasses().contains(this)) attribute.addObjectClass(this);
        return this;
    }

    public Set<Attribute> attributes() {
        return Collections.unmodifiableSet(attributes);
    }

    public ObjectClass removeAttribute(Attribute attribute) {
        if (Objects.nonNull(attribute)) {
            this.attributes.remove(attribute);
            if (attribute.objectClasses().contains(this)) attribute.removeObjectClass(this);
        }
        return this;
    }
}
