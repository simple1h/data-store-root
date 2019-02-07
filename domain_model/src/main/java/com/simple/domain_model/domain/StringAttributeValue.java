package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class StringAttributeValue extends AttributeValue{

    @NotEmpty
    @Column(name = "string_value")
    private String value;

    StringAttributeValue(Attribute attr) {
        this.attr = attr;
        this.id.setAttrId(attr.getAttributeId());
    }

    StringAttributeValue(Attribute attr, String value) {
        this(attr);
        setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StringAttributeValue)) return false;
        StringAttributeValue that = (StringAttributeValue) o;
        return Objects.equals(object, that.object) &&
                Objects.equals(attr, that.attr) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, attr, value);
    }
}
