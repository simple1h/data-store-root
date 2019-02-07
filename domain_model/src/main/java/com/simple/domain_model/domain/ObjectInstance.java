package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

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
    private Long objectInstanceId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "class_id")
    private ObjectClass classId;

    @Embedded
    private EntityInfo info;

    @OneToMany (mappedBy = "object",orphanRemoval = true, cascade = CascadeType.ALL)
//    @OneToMany (mappedBy = "object",orphanRemoval = true)
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
//    @MapKeyJoinColumn(name="attribute_id")
//    private Map<Attribute, StringAttributeValue> values = new HashMap<>();
//    private List<StringAttributeValue> values = new ArrayList<>();
    private List<AttributeValue> values = new ArrayList<>();
//    private List<LongAttributeValue> values = new ArrayList<>();

//    todo добавить коллекцию значений аттрибутов - Map<Attribute, AttributeValue>

    protected ObjectInstance(EntityInfo info, ObjectClass aClass) {
        this.info = info;
        this.classId = aClass;
    }

    public Long getObjectInstanceId() {
        return objectInstanceId;
    }

    public ObjectClass getClassId() {
        return classId;
    }

    public EntityInfo getInfo() {
        return info;
    }

    public void addValue(AttributeValue value) {
        this.values.add(value);
        value.setObject(this);
    }

    public void removeValue(AttributeValue value) {
        this.values.remove(value);
        value.setObject(null);
    }

    public List<AttributeValue> getValues() {
        return Collections.unmodifiableList(values);
    }

    public void replaceValue(AttributeValue oldVal, AttributeValue newVal) {
        removeValue(oldVal);
        addValue(newVal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectInstance)) return false;
        ObjectInstance instance = (ObjectInstance) o;
        return Objects.equals(objectInstanceId, instance.objectInstanceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectInstanceId);
    }
}
