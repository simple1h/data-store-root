package com.simple.domain_model.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Embeddable
public class ObjectClassProperties {

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyJoinColumn(name = "property_key")
    @CollectionTable( name = "object_class_properties_value",joinColumns = @JoinColumn( name = "object_class_id" ) )
    private Map<Property, PropertyValue> properties = new HashMap<>();

    public Map<Property, PropertyValue> getUnmodifiableMap() {
        return Collections.unmodifiableMap(properties);
    }

    public ObjectClassProperties addProperty(PropertyValue value){
        if (Objects.nonNull(value)) this.properties.put(value.getProperty(),value);
        return this;
    }

    public ObjectClassProperties overwriteValue(PropertyValue value){
        if (Objects.nonNull(value)) this.properties.replace(value.getProperty(),value);
        return this;
    }

    public ObjectClassProperties removeProperty (PropertyValue value){
        if (Objects.nonNull(value)) this.properties.remove(value.getProperty(),value);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectClassProperties)) return false;
        ObjectClassProperties that = (ObjectClassProperties) o;
        return Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }

}
