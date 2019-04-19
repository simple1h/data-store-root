package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NamedEntityGraph(
        name = "instance_attribute_values",
        attributeNodes = {
                @NamedAttributeNode("values"),
        }
)
@NoArgsConstructor
@Entity
public class ObjectInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "object_inst")
    @SequenceGenerator(name="object_inst", sequenceName = "object_inst", allocationSize=10, initialValue = 10)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "class_id")
    private ObjectClass classId;

    @OneToMany (mappedBy = "object",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<AttributeValue> values = new ArrayList<>();
//    todo добавить коллекцию значений аттрибутов - Map<Attribute, AttributeValue>

    protected ObjectInstance(@NotNull ObjectClass classId) {
        this.classId = classId;
    }

    public Long getId() {
        return id;
    }

    public ObjectClass getClassId() {
        return classId;
    }

    public void addAttrValue(AttributeValue value) {
        this.values.add(value);
        value.setObject(this);
    }

    public void removeAttrValue(AttributeValue value) {
        this.values.remove(value);
        value.setObject(null);
    }

    public List<AttributeValue> getAttrValues() {
        return Collections.unmodifiableList(values);
    }

    public AttributeValue getAttrValue(String attrName) {
        return getAttrValues().stream()
                .filter(attributeValue -> attributeValue.getAttr().getInfo().getName().equals(attrName))
                .findAny().orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectInstance)) return false;
        ObjectInstance instance = (ObjectInstance) o;
        return Objects.equals(id, instance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
