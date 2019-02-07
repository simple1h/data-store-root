package com.simple.domain_model.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AttributeValueId implements Serializable {

    @Column(name = "object_id")
    private Long objectId;
    @Column(name = "attr_id")
    private Long attrId;

    AttributeValueId() {
    }

    AttributeValueId(Long objectId, Long attrId) {
        this.objectId = objectId;
        this.attrId = attrId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeValueId)) return false;
        AttributeValueId that = (AttributeValueId) o;
        return Objects.equals(objectId, that.objectId) &&
                Objects.equals(attrId, that.attrId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId, attrId);
    }
}
