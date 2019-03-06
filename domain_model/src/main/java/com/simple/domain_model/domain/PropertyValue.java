package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;

import javax.naming.OperationNotSupportedException;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class PropertyValue {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "valueId")
    private ChoiceValue choiceValue;

    private String stringValue;

    private Timestamp dateValue;

    private Long longValue;

    private PropertyValue(Property property){
        this.property = property;
    }

    PropertyValue(Property property, String stringValue) {
        this(property);
        setStringValue(stringValue);
    }
    PropertyValue(Property property, Long longValue) {
        this(property);
        setLongValue(longValue);
    }
    PropertyValue(Property property, Date dateValue) {
        this(property);
        setDateValue(dateValue);
    }
    PropertyValue(Property property, ChoiceValue choiceValue) {
        this(property);
        setChoiceValue(choiceValue);
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = new Timestamp(dateValue.toInstant().toEpochMilli());
    }

    public void setChoiceValue(ChoiceValue choiceValue) {
        this.choiceValue = choiceValue;
    }

    public String getStringValue() throws OperationNotSupportedException {
        validateType(DataType.STRING, "Instance not contain STRING data");
        return stringValue;
    }

    public Date getDateValue() throws OperationNotSupportedException {
        validateType(DataType.DATE_TIME, "Instance not contain DATE_TIME data");
        return dateValue;
    }

    public Long getLongValue() throws OperationNotSupportedException {
        validateType(DataType.LONG, "Instance not contain LONG data");
        return longValue;
    }

    public ChoiceValue getChoiceValue() throws OperationNotSupportedException {
        validateType(DataType.CHOICE_VALUE, "Instance not contain LONG data");
        return choiceValue;
    }

    public Property getProperty() {
        return property;
    }

    private void validateType(DataType type, String message) throws OperationNotSupportedException {
        if (!checkType(type)) {
            throw new OperationNotSupportedException(message);
        }
    }

    private boolean checkType(DataType type){
        return property.getType() == type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropertyValue)) return false;
        PropertyValue value = (PropertyValue) o;
        return Objects.equals(choiceValue, value.choiceValue) &&
                Objects.equals(stringValue, value.stringValue) &&
                Objects.equals(dateValue, value.dateValue) &&
                Objects.equals(longValue, value.longValue) &&
                property == value.getProperty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(choiceValue, stringValue, dateValue, longValue, property);
    }

    @Override
    public String toString() {
        return "PropertyValue{" +
                "choiceValue=" + choiceValue +
                ", stringValue='" + stringValue + '\'' +
                ", dateValue=" + dateValue +
                ", longValue=" + longValue +
                ", property=" + property +
                '}';
    }
}
