package com.simple.domain_model.services;

import com.simple.domain_model.domain.Attribute;
import com.simple.domain_model.domain.Property;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-inttest.properties")
public class AttributeServiceITTest {

    @Autowired
    private AttributeService attrService;
    @Autowired
    private PropertyService propService;

    private static final String STR_NAME = "strName";
    private static final String LONG_NAME = "lName";

    public void init() {
        List<Property> properties = new ArrayList<>();
        properties.add(propService.createStringProperty(STR_NAME,"strDisplayName", "strDescription"));
        properties.add(propService.createLongProperty(LONG_NAME,"lDisplayName", "lDescription"));
        propService.saveAll(properties);

    }

    @Test
    @DirtiesContext
    public void attributeServiceTest() {
        init();
        Property sProperty = propService.findByInfoName(STR_NAME).get();
        Property lProperty = propService.findByInfoName(LONG_NAME).get();

        Attribute stringAttribute = attrService.createStringAttribute("name", "displayName", "description");
        Attribute dateAttribute = attrService.createDateAttribute("name1", "displayName1", "description1");
        Attribute longAttribute = attrService.createLongAttribute("name2", "displayName2", "description2");
        stringAttribute.getProperties().addProperty(attrService.getValueFactory().createStringValue(sProperty,"value"));
        stringAttribute.getProperties().addProperty(attrService.getValueFactory().createLongValue(lProperty,1L));

        attrService.save(stringAttribute);
        attrService.saveAll(Arrays.asList(dateAttribute,longAttribute));


        Assert.assertNotNull(stringAttribute.getId());
        Assert.assertNotNull(dateAttribute.getId());
        Assert.assertNotNull(longAttribute.getId());

        Attribute newAttribute = attrService.findById(stringAttribute.getId()).get();
        Assert.assertNotNull(newAttribute);
        Assert.assertEquals(2, newAttribute.getProperties().getUnmodifiableMap().keySet().size());

        Attribute newDateAttribute = attrService.findByInfoName(dateAttribute.getInfo().getName()).get();
        Attribute newLongAttribute = attrService.findByInfoName(longAttribute.getInfo().getName()).get();
        Assert.assertNotNull(newDateAttribute);
        Assert.assertNotNull(newLongAttribute);

        attrService.deleteById(newAttribute.getId());
        attrService.deleteAll(Arrays.asList(newDateAttribute,newLongAttribute));

        Assert.assertNull(attrService.findById(newAttribute.getId()).orElse(null));
        Assert.assertNull(attrService.findById(newDateAttribute.getId()).orElse(null));
        Assert.assertNull(attrService.findById(newLongAttribute.getId()).orElse(null));
    }
}
