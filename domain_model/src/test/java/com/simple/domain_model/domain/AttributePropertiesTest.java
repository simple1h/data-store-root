package com.simple.domain_model.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.OperationNotSupportedException;
import java.util.*;

public class AttributePropertiesTest {

    private Property pString;
    private Property pLong;
    private Property pDate;

    private PropertyValue stringVal;
    private PropertyValue longVal;
    private PropertyValue dateVal;

    private static DataModelFactory.PropertyValueFactory propValFactory;
    private static DataModelFactory.PropertyFactory propFactory;
    private static DataModelFactory.EntityInfoFactory infoFactory;

    @BeforeClass
    public static void setUpFactory() throws Exception {
        propValFactory = new DataModelFactory().new PropertyValueFactory();
        propFactory = new DataModelFactory().new PropertyFactory();
        infoFactory = new DataModelFactory().new EntityInfoFactory();

    }

    @Before
    public void setUp() throws Exception {
        pString = propFactory.createStringProperty(infoFactory.createEntityInfo("string property","display string property","best string property an ether"));
        pLong = propFactory.createLongProperty(infoFactory.createEntityInfo("LONG property","display LONG property","best LONG property an ether"));
        pDate = propFactory.createDateProperty(infoFactory.createEntityInfo("Date property","display Date property","best string Date an ether"));


        stringVal = propValFactory.createStringValue(pString,"test_value");
        longVal = propValFactory.createLongValue(pLong, 1L);
        dateVal = propValFactory.createDateValue(pDate, new Date());
    }

    @Test
    public void addPropertiesToMapTest()  {
        AttributeProperties properties = new AttributeProperties();
        properties.addProperty(stringVal)
                .addProperty(longVal)
                .addProperty(dateVal);
        Assert.assertEquals(3,properties.getUnmodifiableMap().size());
    }

    @Test
    public void overwritePropertyValue() throws OperationNotSupportedException {
        AttributeProperties properties = new AttributeProperties();
        properties.addProperty(stringVal);
        stringVal.setStringValue("test_value_1");
        properties.overwriteValue(stringVal);
        Assert.assertEquals(stringVal.getStringValue(), properties.getUnmodifiableMap().get(stringVal.getProperty()).getStringValue());
    }

    @Test
    public void overwritePropertyValue1() throws OperationNotSupportedException {
        AttributeProperties properties = new AttributeProperties();
        properties.addProperty(stringVal);
        PropertyValue value = properties.getUnmodifiableMap().values().iterator().next();
        value.setStringValue("test_value_1");
        properties.overwriteValue(value);
        Assert.assertEquals(value.getStringValue(), properties.getUnmodifiableMap().get(value.getProperty()).getStringValue());

    }

    @Test
    public void removeProperty() {
        AttributeProperties properties = new AttributeProperties();
        properties.addProperty(stringVal);
        properties.removeProperty(stringVal);
        Assert.assertTrue(properties.getUnmodifiableMap().isEmpty());

    }

    @Test (expected = UnsupportedOperationException.class)
    public void immutableMapTest() {
        AttributeProperties properties = new AttributeProperties();
        properties.addProperty(stringVal).addProperty(longVal);
        Map<Property, PropertyValue> newMap = properties.getUnmodifiableMap();
        newMap.put(pDate,dateVal);
    }

    @Test
    public void equalsMapTest() {
        AttributeProperties properties = new AttributeProperties();
        properties.addProperty(stringVal).addProperty(longVal);
        AttributeProperties properties1 = new AttributeProperties();
        properties1.addProperty(stringVal).addProperty(longVal);
        Assert.assertEquals(properties,properties1);
    }

    @Test
    public void notEqualsMapTest() {
        AttributeProperties properties = new AttributeProperties();
        properties.addProperty(stringVal).addProperty(longVal);
        AttributeProperties properties1 = new AttributeProperties();
        properties1.addProperty(longVal);
        Assert.assertNotEquals(properties,properties1);
    }
}