package com.simple.domain_model.domain;

import com.simple.domain_model.domain.validators.CheckDataType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Component
@Validated
public class DataModelFactory {

//    todo вынести ObjectInstanceFactory и AttributeValueFactory в DataInstanceFactory (DS-19)
    @Component
    @Validated
    public class AttributeValueFactory {
        public StringAttributeValue createStringAttrVal(@Valid @NotNull
                @CheckDataType(type = DataType.STRING, message = "Only DataType.STRING allowed") Attribute attribute, String value) {
            return new StringAttributeValue(attribute, value);
        }

        public LongAttributeValue createLongAttrVal(@Valid @NotNull
                @CheckDataType(type = DataType.LONG, message = "Only DataType.LONG allowed") Attribute attribute, Long value) {
            return new LongAttributeValue(attribute, value);
        }

        public DateAttributeValue createDateAttrVal(@Valid @NotNull
                @CheckDataType(type = DataType.DATE_TIME, message = "Only DataType.DATE_TIME allowed") Attribute attribute, Date value) {
            return new DateAttributeValue(attribute, value);
        }

        public ChoiceAttributeValue createChoiceAttrVal(@Valid @NotNull
                @CheckDataType(type = DataType.CHOICE_VALUE, message = "Only DataType.CHOICE_VALUE allowed") Attribute attribute, @Valid @NotNull ChoiceValue value) {
            return new ChoiceAttributeValue(attribute, value);
        }
    }

    @Component
    @Validated
    public class ObjectInstanceFactory {
        public ObjectInstance createObjectInstance(@Valid @NotNull EntityInfo info, @Valid @NotNull ObjectClass aClass) {
            return new ObjectInstance(info, aClass);
        }

    }
    @Component
    @Validated
    public class PropertyValueFactory {


        public PropertyValue createStringValue(@Valid @NotNull
                @CheckDataType(type = DataType.STRING, message = "Only DataType.STRING allowed") Property property, @NotNull String value) {
            return new PropertyValue(property, value);
        }

        public PropertyValue createLongValue(@Valid @NotNull
                @CheckDataType(type = DataType.LONG, message = "Only DataType.LONG allowed") Property property, @NotNull Long value) {
            return new PropertyValue(property, value);
        }
        public PropertyValue createDateValue(@Valid @NotNull
                @CheckDataType(type = DataType.DATE_TIME, message = "Only DataType.DATE_TIME allowed") Property property, @NotNull Date value) {
            return new PropertyValue(property, value);
        }

        public PropertyValue createChoiceValue(@Valid @NotNull
               @CheckDataType(type = DataType.CHOICE_VALUE, message = "Only DataType.CHOICE_VALUE allowed") Property property, @Valid @NotNull ChoiceValue value) {
            return new PropertyValue(property, value);
        }
    }
    @Component
    @Validated
    public class PropertyFactory {


        public Property createStringProperty(@Valid @NotNull EntityInfo info) {
            return create(info, DataType.STRING);
        }

        public Property createLongProperty(@Valid @NotNull EntityInfo info) {
            return create(info, DataType.LONG);
        }

        public Property createDateProperty(@Valid @NotNull EntityInfo info) {
            return create(info, DataType.DATE_TIME);
        }

        public Property createChoiceProperty(@Valid @NotNull EntityInfo info) {
            return create(info, DataType.CHOICE_VALUE);
        }

        private  Property create(EntityInfo info, DataType type) {
            return new Property(info, type);
        }
    }
    @Component
    @Validated
    public class EntityInfoFactory {


        public EntityInfo createEntityInfo(@NotEmpty String name, String displayName) {
            return createEntityInfo(name, displayName, null);
        }

        public EntityInfo createEntityInfo(@NotEmpty String name) {
            return createEntityInfo(name, null, null);
        }
        public EntityInfo createEntityInfo(@NotEmpty String name, String displayName, String description) {
            return new EntityInfo(name, displayName, description);
        }
        //    Для клонирования объектов

        public EntityInfo createEntityInfo(@Valid @NotNull EntityInfo info) {
            return createEntityInfo(info.getName(), info.getDisplayName(), info.getDescription());
        }
    }
    @Component
    @Validated
    public class AttributeFactory {


        public Attribute createAttribute(@Valid @NotNull EntityInfo attributeInfo, DataType type) {
            return new Attribute(attributeInfo, type);
        }

        public Attribute createStringAttribute(@Valid @NotNull EntityInfo info) {
            return new Attribute(info, DataType.STRING);
        }
        public Attribute createLongAttribute(@Valid @NotNull EntityInfo info) {
            return new Attribute(info, DataType.LONG);
        }
        public Attribute createDateAttribute(@Valid @NotNull EntityInfo info) {
            return new Attribute(info, DataType.DATE_TIME);
        }
        public Attribute createChoiceAttribute(@Valid @NotNull EntityInfo info) {
            return new Attribute(info, DataType.CHOICE_VALUE);
        }
    }
    @Component
    @Validated
    public class ChoiceSetFactory {


        public ChoiceSet createChoiceSet(@Valid @NotNull EntityInfo info) {
            return new ChoiceSet(info);
        }
    }
    @Component
    @Validated
    public class ChoiceValueFactory {


        public ChoiceValue createChoiceValue(@NotEmpty String value, @NotNull Long valueId) {
            return new ChoiceValue(valueId, value);
        }
    }
    @Component
    @Validated
    public class ObjectClassFactory {


        public ObjectClass createObjectClass(@Valid @NotNull EntityInfo info) {
            return new ObjectClass(info);
        }

    }
}
