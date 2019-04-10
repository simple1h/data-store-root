package com.simple.domain_model.services;

import com.simple.domain_model.domain.Attribute;
import com.simple.domain_model.domain.ObjectClass;
import com.simple.domain_model.domain.Property;
import com.simple.domain_model.domain.PropertyValue;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-inttest.properties")
public class ObjectClassServiceITTest {

    @Autowired
    private ObjectClassService service;
    @Autowired
    private PropertyService prpService;
    @Autowired
    private AttributeService atrService;

    private static final String DATE_NAME = "dateName";
    private static final String STR_NAME = "strName";
    private static final String LONG_NAME = "lName";

    private static final String STR_ATTR_NAME = "strAttrName";
    private static final String LONG_ATTR_NAME = "longAttrName";
    private static final String DATE_ATTR_NAME = "dateAttrName";

    @Test
    @Transactional
    public void objectClassServiceTest() {
        initEnvironmentEntities();
        initObjectClass();
        ObjectClass objectClass = service.findByInfoName("mainClass").get();
        Assert.assertNotNull(objectClass);
        Assert.assertEquals(4,objectClass.childs().size());
        Assert.assertEquals(3, objectClass.attributes().size());
        Assert.assertEquals(3,objectClass.properties().getUnmodifiableMap().size());
        modifyObjectClass();
        ObjectClass objectClass1 = service.findByInfoName("mainClass").get();
        Assert.assertEquals(2,objectClass1.childs().size());
        Assert.assertEquals(2, objectClass1.attributes().size());
        Assert.assertEquals(2,objectClass1.properties().getUnmodifiableMap().size());
        service.deleteById(objectClass1.getId());
        ObjectClass objectClass2 = service.findByInfoName("mainClass").orElse(null);
        Assert.assertNull(objectClass2);
    }

    private void modifyObjectClass() {
        ObjectClass objectClass = service.findByInfoName("mainClass").get();
        ObjectClass forDel1 = service.findByInfoName("childClass2").get();
        ObjectClass forDel2 = service.findByInfoName("childClass3").get();
        Property lProperty = prpService.findByInfoName(LONG_NAME).get();
        Attribute forDelAttr = atrService.findByInfoName(DATE_ATTR_NAME).get();
        PropertyValue forDelPV = objectClass.properties().getUnmodifiableMap().get(lProperty);
        objectClass.removeChild(forDel1);
        objectClass.removeChild(forDel2);
        objectClass.removeAttribute(forDelAttr);
        objectClass.properties().removeProperty(forDelPV);
        service.save(objectClass);
    }

    private void initObjectClass() {
        ObjectClass objectClass = createObjectClass("mainClass", "mainDisplayName", "mainDescription");
        getPropertyValues().forEach(value -> objectClass.properties().addProperty(value));
        getAttributes().forEach(objectClass::addAttribute);
        getChildObjectClasses().forEach(objectClass::addChild);
        service.save(objectClass);
    }

    private List<ObjectClass> getChildObjectClasses() {
        return Arrays.asList(
                createObjectClass("childClass1", "childDisplayName1", "childDescription1"),
                createObjectClass("childClass2", "childDisplayName2", "childDescription2"),
                createObjectClass("childClass3", "childDisplayName3", "childDescription3"),
                createObjectClass("childClass4", "childDisplayName4", "childDescription4")
        );
    }

    private List<Attribute> getAttributes() {
        return Arrays.asList(atrService.findByInfoName(STR_ATTR_NAME).get(),
                            atrService.findByInfoName(LONG_ATTR_NAME).get(),
                            atrService.findByInfoName(DATE_ATTR_NAME).get());
    }
    private List<PropertyValue> getPropertyValues() {
        List<PropertyValue> result = new ArrayList<>();
        Property sProperty = prpService.findByInfoName(STR_NAME).get();
        Property lProperty = prpService.findByInfoName(LONG_NAME).get();
        Property dProperty = prpService.findByInfoName(DATE_NAME).get();
        result.add(service.getValueFactory().createStringValue(sProperty,"value"));
        result.add(service.getValueFactory().createLongValue(lProperty,1L));
        result.add(service.getValueFactory().createDateValue(dProperty,new Date()));
        return result;
    }

    void initEnvironmentEntities() {
        createProperties();
        createAttributes();
    }

    private void createAttributes() {
        atrService.saveAll(
                Arrays.asList(
                        atrService.createStringAttribute(STR_ATTR_NAME,"strDisplayName", "strDescription"),
                        atrService.createLongAttribute(LONG_ATTR_NAME,"longDisplayName", "longDescription"),
                        atrService.createDateAttribute(DATE_ATTR_NAME,"dateDisplayName", "dateDescription")
                ));
    }

    private void createProperties() {
        prpService.saveAll(
                Arrays.asList(
                        prpService.createStringProperty(STR_NAME, "strDisplayName", "strDescription"),
                        prpService.createLongProperty(LONG_NAME, "lDisplayName", "lDescription"),
                        prpService.createDateProperty(DATE_NAME, "dDisplayName", "dDescription")
                ));
    }

    private ObjectClass createObjectClass(String name, String displayName, String description) {
        return service.createObjectClass(name, displayName, description);
    }
}
