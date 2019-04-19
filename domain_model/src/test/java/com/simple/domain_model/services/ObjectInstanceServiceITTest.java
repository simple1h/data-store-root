package com.simple.domain_model.services;

import com.simple.domain_model.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/*
1. создать модель данных (свойства/атрибуты/классы)
2. создать экзмепляр данных
3. заполнить атрибуты в нем
4. сохранить
5. найти в базе
6. удалить/отредактировать занчения атрибутов
7. сохранить
8. найти в базе
9. удалить
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-inttest.properties")
@ContextConfiguration
public class ObjectInstanceServiceITTest {

    @Autowired
    private ObjectClassService classService;
    @Autowired
    private PropertyService prpService;
    @Autowired
    private AttributeService atrService;

    @Autowired
    private ObjectServiceTestHelper helper;

    @Autowired
    private ObjectInstanceService service;

    @Test
    public void objectInstanceServiceTest() {
        helper.initEnvironmentEntities();
        helper.initObjectClass();
        ObjectClass objectClass = classService.findByInfoName("mainClass").get();
        Assert.assertNotNull(objectClass);
        ObjectInstance instance =service.createObject(objectClass);
        Assert.assertNotNull(instance);

        Attribute strAttr = atrService.findByInfoName(ObjectServiceTestHelper.STR_ATTR_NAME).get();
        Attribute longAttr = atrService.findByInfoName(ObjectServiceTestHelper.LONG_ATTR_NAME).get();
        Attribute dateAttr = atrService.findByInfoName(ObjectServiceTestHelper.DATE_ATTR_NAME).get();
        Assert.assertNotNull(strAttr);
        Assert.assertNotNull(longAttr);
        Assert.assertNotNull(dateAttr);


        AttributeValue strAttrVal = service.createAttrVal(strAttr, "TestAttrValue");
        AttributeValue longAttrVal = service.createAttrVal(longAttr, 123L);
        AttributeValue dateAttrVal = service.createAttrVal(dateAttr, new Date());
        Assert.assertNotNull(strAttrVal);
        Assert.assertNotNull(longAttrVal);
        Assert.assertNotNull(dateAttrVal);

        service.save(instance);
        instance.addAttrValue(strAttrVal);
        instance.addAttrValue(longAttrVal);
        instance.addAttrValue(dateAttrVal);

        service.save(instance);

        ObjectInstance newInstance = service.findById(instance.getId()).get();
        Assert.assertNotNull(newInstance);

        Assert.assertEquals(3, newInstance.getAttrValues().size());
        AttributeValue oldStrAttrVal = newInstance.getAttrValue(ObjectServiceTestHelper.STR_ATTR_NAME);
        AttributeValue oldLongAttrVal = newInstance.getAttrValue(ObjectServiceTestHelper.LONG_ATTR_NAME);
        Assert.assertEquals("TestAttrValue", ((StringAttributeValue)oldStrAttrVal).getValue());
        Assert.assertEquals(Long.valueOf(123L), ((LongAttributeValue)oldLongAttrVal).getValue());

        newInstance.removeAttrValue(dateAttrVal);
        ((StringAttributeValue)oldStrAttrVal).setValue("NewTestAttrValue");
        ((LongAttributeValue)oldLongAttrVal).setValue(456L);

        service.save(newInstance);

        ObjectInstance newOneInstance = service.findById(newInstance.getId()).get();
        Assert.assertNotNull(newOneInstance);

        Assert.assertEquals(2, newInstance.getAttrValues().size());
        AttributeValue newOneStrAttrVal = newInstance.getAttrValue(ObjectServiceTestHelper.STR_ATTR_NAME);
        AttributeValue newOneLongAttrVal = newInstance.getAttrValue(ObjectServiceTestHelper.LONG_ATTR_NAME);
        Assert.assertEquals("NewTestAttrValue", ((StringAttributeValue)newOneStrAttrVal).getValue());
        Assert.assertEquals(Long.valueOf(456L), ((LongAttributeValue)newOneLongAttrVal).getValue());

        service.deleteById(newOneInstance.getId());
        ObjectInstance delInstance = service.findById(newInstance.getId()).orElse(null);
        Assert.assertNull(delInstance);

    }
}























