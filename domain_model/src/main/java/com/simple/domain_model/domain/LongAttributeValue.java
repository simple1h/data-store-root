package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class LongAttributeValue extends AttributeValue {

    @Column(name = "long_value")
    private Long value;

    private LongAttributeValue(Attribute attr) {
        super(attr);
    }

    LongAttributeValue(Attribute attr, Long value) {
        this(attr);
        setValue(value);
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LongAttributeValue)) return false;
        LongAttributeValue that = (LongAttributeValue) o;
        return Objects.equals(object, that.object) &&
                Objects.equals(attr, that.attr) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, attr, value);
    }
}
