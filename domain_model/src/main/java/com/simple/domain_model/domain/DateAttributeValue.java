package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class DateAttributeValue extends AttributeValue {

    @Column(name = "date_value")
    private Timestamp value;

    private DateAttributeValue(Attribute attr) {
        this.attr = attr;
        this.id.setAttrId(attr.getId());
    }

    DateAttributeValue(Attribute attr, Date value) {
        this(attr);
        setValue(value);
    }

    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
        this.value = new Timestamp(value.toInstant().toEpochMilli());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateAttributeValue)) return false;
        DateAttributeValue that = (DateAttributeValue) o;
        return Objects.equals(object, that.object) &&
                Objects.equals(attr, that.attr) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, attr, value);
    }
}
