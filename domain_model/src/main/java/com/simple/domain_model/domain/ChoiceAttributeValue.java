package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@NoArgsConstructor
public class ChoiceAttributeValue extends AttributeValue {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "valueId")
    private ChoiceValue value;

    ChoiceAttributeValue(Attribute attr) {
        this.attr = attr;
        this.id.setAttrId(attr.getAttributeId());
    }

    ChoiceAttributeValue(Attribute attr, ChoiceValue value) {
        this(attr);
        setValue(value);
    }

    public ChoiceValue getValue() {
        return value;
    }

    public void setValue(ChoiceValue value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChoiceAttributeValue)) return false;
        ChoiceAttributeValue that = (ChoiceAttributeValue) o;
        return Objects.equals(object, that.object) &&
                Objects.equals(attr, that.attr) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, attr, value);
    }

}
