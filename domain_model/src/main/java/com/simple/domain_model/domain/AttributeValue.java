package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@NoArgsConstructor
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy = InheritanceType.JOINED)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn(name = "val_type")
//@DiscriminatorColumn(name="val_type", discriminatorType=DiscriminatorType.STRING)
//@MappedSuperclass
public abstract class AttributeValue{
    @NotNull
    @EmbeddedId
    protected AttributeValueId id = new AttributeValueId();

//    @NotNull
    @ManyToOne
    @MapsId("objectId")
    protected ObjectInstance object;
    @NotNull
    @ManyToOne
    @MapsId("attrId")
    protected Attribute attr;

    protected AttributeValue(@NotNull Attribute attr) {
        this.attr = attr;
        this.id.setAttrId(attr.getAttributeId());
    }

    public Attribute getAttr() {
        return attr;
    }

    public ObjectInstance getObject() {
        return object;
    }

    public void setObject(ObjectInstance object) {
        if (object != null) {
            this.object = object;
            this.id.setObjectId(object.getObjectInstanceId());
        } else {
            this.object = null;
            this.id.setObjectId(null);
        }
    }
}
