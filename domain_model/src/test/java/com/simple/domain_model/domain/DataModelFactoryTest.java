package com.simple.domain_model.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.OperationNotSupportedException;
import javax.validation.ConstraintViolationException;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-inttest.properties")
public class DataModelFactoryTest {

    @Autowired
    DataModelFactory.PropertyValueFactory propValFactory;
    @Autowired
    DataModelFactory.PropertyFactory propFactory;
    @Autowired
    DataModelFactory.EntityInfoFactory infoFactory;
//    @Autowired
//    DataModelFactory.AttributePropertiesFactory mapFactory;
    @Autowired
    DataModelFactory.AttributeFactory attrFactory;
    @Autowired
    DataModelFactory.ChoiceSetFactory choiceFactory;
    @Autowired
    DataModelFactory.ChoiceValueFactory choiceValFactory;
    @Autowired
    DataModelFactory.ObjectClassFactory objectClassFactory;
    @Autowired
    DataModelFactory.AttributeValueFactory valFactory;


    @Test
    public void createObjectClassTest() {
        ObjectClass aClass = objectClassFactory.createObjectClass(infoFactory.createEntityInfo("objectClassName"));
        Assert.assertNotNull(aClass);
    }

    @Test
    public void createChoiceValueTest() {
        ChoiceValue value = choiceValFactory.createChoiceValue("choiceValue");
        Assert.assertNotNull(value);
    }

    @Test
    public void createChoiceSetTest() {
        ChoiceSet set = choiceFactory.createChoiceSet(infoFactory.createEntityInfo("testChoiceSet"));
        Assert.assertNotNull(set);
    }

    @Test
    public void createAttributeTest() {
        Attribute attribute = attrFactory.createStringAttribute(infoFactory.createEntityInfo("testAttributeName"));
        Assert.assertNotNull(attribute);
        Attribute attribute1 = attrFactory.createStringAttribute(infoFactory.createEntityInfo("testAttributeName"));
//                mapFactory.createEmptyPropMap().
        attribute1.getProperties().addProperty(propValFactory.createStringValue(
                                propFactory.createStringProperty(infoFactory.createEntityInfo("stringProperty")),"test1"));
        Assert.assertNotNull(attribute1);

    }

    @Test
    public void createStringAttributeTest() {
        Attribute attribute = attrFactory.createStringAttribute(infoFactory.createEntityInfo("testAttributeName"));
        Assert.assertNotNull(attribute);
    }

    @Test
    public void createStringAttributeValueTest() {
        Attribute attribute = attrFactory.createStringAttribute(infoFactory.createEntityInfo("testAttributeName"));
        StringAttributeValue value = valFactory.createStringAttrVal(attribute, "test");
        Assert.assertEquals("test", value.getValue());
    }

    @Test
    public void createLongAttributeValueTest() {
        Attribute attribute = attrFactory.createLongAttribute(infoFactory.createEntityInfo("testAttributeName"));
        LongAttributeValue value = valFactory.createLongAttrVal(attribute, 1L);
        Assert.assertEquals(value.getValue(), Long.valueOf(1L));
    }

    @Test
    public void createDateAttributeValueTest() {
        Date current = new Date();
        Attribute attribute = attrFactory.createDateAttribute(infoFactory.createEntityInfo("testAttributeName"));
        DateAttributeValue value = valFactory.createDateAttrVal(attribute, current);
        Assert.assertEquals(value.getValue(), new Timestamp(current.toInstant().toEpochMilli()));
    }

    @Test
    public void createChoiceAttributeValueTest() {
        Attribute attribute = attrFactory.createChoiceAttribute(infoFactory.createEntityInfo("testAttributeName"));
        ChoiceAttributeValue value = valFactory.createChoiceAttrVal(attribute, choiceValFactory.createChoiceValue("test"));
        Assert.assertEquals("test",value.getValue().getValue());
    }

    @Test (expected = ConstraintViolationException.class)
    public void createWrongTypeAttributeValueTest() {
        Attribute attribute = attrFactory.createStringAttribute(infoFactory.createEntityInfo("testAttributeName"));
        LongAttributeValue value = valFactory.createLongAttrVal(attribute, 1L);
    }

    @Test
    public void createStringValueTest() throws  OperationNotSupportedException {
        Property property = propFactory.createStringProperty(infoFactory.createEntityInfo("testPropertyName"));
        PropertyValue value = propValFactory.createStringValue(property, "test");
        Assert.assertEquals("test",value.getStringValue());
    }

    @Test (expected = ConstraintViolationException.class)
    public void validateStringPropertyTypeTest() throws  OperationNotSupportedException {
        Property property = propFactory.createLongProperty(infoFactory.createEntityInfo("testPropertyName"));
        PropertyValue value = propValFactory.createStringValue(property, "test");
        Assert.assertEquals("test",value.getStringValue());
    }

    @Test (expected = ConstraintViolationException.class)
    public void validateLongPropertyTypeTest() throws  OperationNotSupportedException {
        Property property = propFactory.createStringProperty(infoFactory.createEntityInfo("testPropertyName"));
        PropertyValue value = propValFactory.createLongValue(property, 1L);
        Assert.assertEquals("test",value.getStringValue());
    }

    @Test (expected = ConstraintViolationException.class)
    public void validateDatePropertyTypeTest() throws  OperationNotSupportedException {
        Property property = propFactory.createLongProperty(infoFactory.createEntityInfo("testPropertyName"));
        PropertyValue value = propValFactory.createDateValue(property, new Date());
        Assert.assertEquals("test",value.getStringValue());
    }

    @Test (expected = ConstraintViolationException.class)
    public void validateChoicePropertyTypeTest() throws  OperationNotSupportedException {
        Property property = propFactory.createLongProperty(infoFactory.createEntityInfo("testPropertyName"));
        PropertyValue value = propValFactory.createChoiceValue(property, choiceValFactory.createChoiceValue("test"));
        Assert.assertEquals("test",value.getStringValue());
    }

    @Test (expected = ConstraintViolationException.class)
    public void createStringValueValidationTest() throws OperationNotSupportedException {
        Property property = propFactory.createStringProperty(null);
        PropertyValue value = propValFactory.createStringValue(property, "test");
        Assert.assertEquals("test",value.getStringValue());
    }

    @Test (expected = ConstraintViolationException.class)
    public void createEntityEmptyValidationTest() throws OperationNotSupportedException {
        Property property = propFactory.createStringProperty(infoFactory.createEntityInfo(""));
        PropertyValue value = propValFactory.createStringValue(property, "test");
        Assert.assertEquals("test",value.getStringValue());
    }

    @Test
    public void createLongPropertyValueTest() throws OperationNotSupportedException {
        Property property = propFactory.createLongProperty(infoFactory.createEntityInfo("longPropertyName"));
        PropertyValue value = propValFactory.createLongValue(property, 1L);
        Assert.assertEquals(value.getLongValue(), Long.valueOf(1L));
    }
    @Test
    public void createDatePropertyValueTest() throws OperationNotSupportedException {
        Date date = new Date();
        Property property = propFactory.createDateProperty(infoFactory.createEntityInfo("datePropertyName"));
        PropertyValue value = propValFactory.createDateValue(property,date);
        Assert.assertEquals(value.getDateValue(),  new Timestamp(date.toInstant().toEpochMilli()));
    }
    @Test
    public void createChoicePropertyValueTest() throws OperationNotSupportedException {
        Property property = propFactory.createChoiceProperty(infoFactory.createEntityInfo("choicePropertyName"));
        PropertyValue value = propValFactory.createChoiceValue(property, choiceValFactory.createChoiceValue("test"));
        Assert.assertEquals("test",value.getChoiceValue().getValue());
    }

    @Test
    public void createStringPropertyTest() {
        Property property = propFactory.createStringProperty(infoFactory.createEntityInfo("testPropertyName"));
        Assert.assertNotNull(property);
    }
    @Test
    public void createLongPropertyTest() {
        Property property = propFactory.createLongProperty(infoFactory.createEntityInfo("testPropertyName"));
        Assert.assertNotNull(property);
    }
    @Test
    public void createDatePropertyTest() {
        Property property = propFactory.createDateProperty(infoFactory.createEntityInfo("testPropertyName"));
        Assert.assertNotNull(property);
    }
    @Test
    public void createChoicePropertyTest() {
        Property property = propFactory.createChoiceProperty(infoFactory.createEntityInfo("testPropertyName"));
        Assert.assertNotNull(property);
    }

/*    @Test
    public void createPropertiesMapTest()  {
        Property pString = propFactory.createStringProperty(infoFactory.createEntityInfo("string property","display string property","best string property an ether"));
        Property pLong = propFactory.createLongProperty(infoFactory.createEntityInfo("LONG property","display LONG property","best LONG property an ether"));
        Property pDate = propFactory.createDateProperty(infoFactory.createEntityInfo("Date property","display Date property","best string Date an ether"));


        PropertyValue stringVal = propValFactory.createStringValue(pString,"test_value");
        PropertyValue longVal = propValFactory.createLongValue(pLong, 1L);
        PropertyValue dateVal = propValFactory.createDateValue(pDate, new Date());

        List<PropertyValue> list = Arrays.asList(stringVal,longVal,dateVal);
        AttributeProperties properties = mapFactory.createPropMap(list);
        Assert.assertEquals(list.size(),properties.getUnmodifiableMap().values().size());
    }

    @Test
    public void createEmptyPropMapTest() {
        AttributeProperties emptyPropMap = mapFactory.createEmptyPropMap();
        Assert.assertNotNull(emptyPropMap);
        Assert.assertTrue(emptyPropMap.getUnmodifiableMap().isEmpty());
    }

    @Test
    public void clonePropMapTest() {
        Property pString = propFactory.createStringProperty(infoFactory.createEntityInfo("string property","display string property","best string property an ether"));
        Property pLong = propFactory.createLongProperty(infoFactory.createEntityInfo("LONG property","display LONG property","best LONG property an ether"));
        Property pDate = propFactory.createDateProperty(infoFactory.createEntityInfo("Date property","display Date property","best string Date an ether"));


        PropertyValue stringVal = propValFactory.createStringValue(pString,"test_value");
        PropertyValue longVal = propValFactory.createLongValue(pLong, 1L);
        PropertyValue dateVal = propValFactory.createDateValue(pDate, new Date());

        AttributeProperties properties = mapFactory.createPropMap(Arrays.asList(stringVal,longVal,dateVal));
        AttributeProperties properties1 = mapFactory.clonePropMap(properties);
        Assert.assertEquals(properties.getUnmodifiableMap().size(),properties1.getUnmodifiableMap().size());

    }*/

}