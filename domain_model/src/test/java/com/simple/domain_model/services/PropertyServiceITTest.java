package com.simple.domain_model.services;

import com.simple.domain_model.domain.Property;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-inttest.properties")
public class PropertyServiceITTest {

    @Autowired
    private PropertyService service;

    @Test
    public void propertyServiceTest() {

        String stringName = "stringName";
        String stringDisplayName = "stringDisplayName";
        String stringDescription = "stringDescription";
        String longName = "longName";
        String longDisplayName = "longDisplayName";
        String longDescription = "longDescription";
        String dataName = "dataName";
        String dataDisplayName = "dataDisplayName";
        String dataDescription = "dataDescription";
        String choiceName = "choiceName";
        String choiceDisplayName = "choiceDisplayName";
        String choiceDescription = "choiceDescription";

        Property stringProperty = service.createStringProperty(stringName, stringDisplayName, stringDescription);
        Property longProperty = service.createLongProperty(longName, longDisplayName, longDescription);
        Property dataProperty = service.createDateProperty(dataName, dataDisplayName, dataDescription);
        Property choiceProperty = service.createChoiceProperty(choiceName, choiceDisplayName, choiceDescription);

        Assert.assertNotNull(stringProperty);
        Assert.assertNotNull(longProperty);
        Assert.assertNotNull(dataProperty);
        Assert.assertNotNull(choiceProperty);

        service.save(stringProperty);
        service.saveAll(Arrays.asList(longProperty, dataProperty, choiceProperty));

        Assert.assertNotNull(stringProperty.getId());
        Assert.assertNotNull(longProperty.getId());
        Assert.assertNotNull(dataProperty.getId());
        Assert.assertNotNull(choiceProperty.getId());

        Property newStringProperty = service.findById(stringProperty.getId()).get();
        Property newLongProperty = service.findById(longProperty.getId()).get();
        Property newDataProperty = service.findByInfoName(dataProperty.getInfo().getName()).get();
        Property newChoiceProperty = service.findByInfoName(choiceProperty.getInfo().getName()).get();

        Assert.assertNotNull(newStringProperty);
        Assert.assertNotNull(newLongProperty);
        Assert.assertNotNull(newDataProperty);
        Assert.assertNotNull(newChoiceProperty);

        service.deletePropertyById(newChoiceProperty.getId());
        service.deleteAllProperties(Arrays.asList(newStringProperty,newLongProperty,newDataProperty));

        Assert.assertNull(service.findById(stringProperty.getId()).orElse(null));
        Assert.assertNull(service.findById(longProperty.getId()).orElse(null));
        Assert.assertNull(service.findByInfoName(dataProperty.getInfo().getName()).orElse(null));
        Assert.assertNull(service.findByInfoName(choiceProperty.getInfo().getName()).orElse(null));
    }
}
