package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Embeddable
public class AttributeProperties {

    @ElementCollection (fetch = FetchType.EAGER)
    @MapKeyJoinColumn (name = "property_key")
    @CollectionTable ( name = "attribute_properties_value",joinColumns = @JoinColumn( name = "attribute_id" ) )
    private Map<Property, PropertyValue> properties = new HashMap<>();

    public Map<Property, PropertyValue> getUnmodifiableMap() {
        return Collections.unmodifiableMap(properties);
    }

    public AttributeProperties addProperty(PropertyValue value){
        if (Objects.nonNull(value)) this.properties.put(value.getProperty(),value);
        return this;
    }

    public AttributeProperties overwriteValue(PropertyValue value){
        if (Objects.nonNull(value)) this.properties.replace(value.getProperty(),value);
        return this;
    }

    public AttributeProperties removeProperty (PropertyValue value){
        if (Objects.nonNull(value)) this.properties.remove(value.getProperty(),value);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeProperties)) return false;
        AttributeProperties that = (AttributeProperties) o;
        return Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }
}
