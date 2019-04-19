package com.simple.domain_model.services;

import com.simple.domain_model.domain.Attribute;
import com.simple.domain_model.domain.ObjectClass;
import com.simple.domain_model.domain.Property;
import com.simple.domain_model.domain.PropertyValue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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

    private ObjectServiceTestHelper helper;

    @Before
    public void setUp() throws Exception {
        helper = new ObjectServiceTestHelper(service, prpService, atrService);
    }

    @Test
    @Transactional
    public void objectClassServiceTest() {
        helper.initEnvironmentEntities();
        helper.initObjectClass();
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
        Property lProperty = prpService.findByInfoName(ObjectServiceTestHelper.LONG_NAME).get();
        Attribute forDelAttr = atrService.findByInfoName(ObjectServiceTestHelper.DATE_ATTR_NAME).get();
        PropertyValue forDelPV = objectClass.properties().getUnmodifiableMap().get(lProperty);
        objectClass.removeChild(forDel1);
        objectClass.removeChild(forDel2);
        objectClass.removeAttribute(forDelAttr);
        objectClass.properties().removeProperty(forDelPV);
        service.save(objectClass);
    }
}
