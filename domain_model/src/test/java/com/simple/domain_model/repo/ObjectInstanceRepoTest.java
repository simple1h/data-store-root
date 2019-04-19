package com.simple.domain_model.repo;

import com.simple.domain_model.domain.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-inttest.properties")
public class ObjectInstanceRepoTest {

    @Autowired
    DataModelFactory.ObjectClassFactory objectClassFactory;
    @Autowired
    DataModelFactory.EntityInfoFactory infoFactory;
    @Autowired
    DataModelFactory.ObjectInstanceFactory objectInstanceFactory;
    @Autowired
    DataModelFactory.AttributeFactory attrFactory;
    @Autowired
    DataModelFactory.AttributeValueFactory valFactory;
    @Autowired
    private AttributeRepo attributeRepo;
    @Autowired
    private ObjectClassRepo classRepo;
    @Autowired
    private ObjectInstanceRepo objectInstanceRepo;

    private Attribute attribute;


    @Before
    public void setUp() throws Exception {
//        attribute = attrFactory.createAttribute(infoFactory.createEntityInfo("testAttributeName"));
//        attributeRepo.save(attribute);

    }
    @Test
    public void persistEmptyObjectInstance() {
//        todo создать ObjectClass
        ObjectClass aClass = objectClassFactory.createObjectClass(infoFactory.createEntityInfo("objectClassName6"));
        classRepo.save(aClass);
//        todo создать на основе ObjectClass ObjectInstance
        EntityInfo info = infoFactory.createEntityInfo("objectInstanceName6");
        ObjectInstance instance = objectInstanceFactory.createObjectInstance(aClass);
//        todo сохранить его, проверить результат
        objectInstanceRepo.save(instance);
        Assert.assertNotNull(instance.getId());
        Assert.assertEquals(aClass, instance.getClassId());
    }

    @Test
    public void persistObjectInstanceAndAttribute() {
//        todo создать ObjectClass
        ObjectClass aClass = objectClassFactory.createObjectClass(infoFactory.createEntityInfo("objectClassName7"));
        classRepo.save(aClass);
//        todo создать на основе ObjectClass ObjectInstance
        ObjectInstance instance = objectInstanceFactory.createObjectInstance(aClass);
//        todo создать аттрибут
        Attribute attribute = attrFactory.createStringAttribute(infoFactory.createEntityInfo("testAttributeName10"));
        attributeRepo.save(attribute);
//        Attribute newAttribute = attributeRepo.findById(attribute.getId()).orElse(null);

//        todo создать значение для атрибута
        objectInstanceRepo.save(instance);
//        StringAttributeValue value = valFactory.createStringAttrVal(attribute).setValue("test_attr_value");
//        instance.setObjectInstanceId(1L);
//        StringAttributeValue value = new StringAttributeValue(instance, attribute, "test_attr_value");
        instance.addAttrValue(valFactory.createStringAttrVal(attribute,"test_attr_value"));
        objectInstanceRepo.save(instance);
        Assert.assertNotNull(instance.getId());
//        ObjectInstance newInstance = objectInstanceRepo.findById(instance.getId()).orElse(null);
        ObjectInstance newInstance = objectInstanceRepo.findObjectInstanceById(instance.getId()).orElse(null);
        Assert.assertNotNull(newInstance);
        Assert.assertEquals(1,newInstance.getAttrValues().size());
//        instance = objectInstanceRepo.save(instance);
//        attrRepo.save(value);
//        instance.addAttrValue(value);
//        objectInstanceRepo.save(instance);
//        todo добавить пару атрибут/значение атрибута в коллекцию ObjectInstance
//        todo сохранить ObjectInstance, проверить результат

    }

    @Test
    public void persistObjectWithLongAttrVal() {
        ObjectClass aClass = objectClassFactory.createObjectClass(infoFactory.createEntityInfo("objectClassName10"));
        classRepo.save(aClass);
        ObjectInstance instance = objectInstanceFactory.createObjectInstance(aClass);
        Attribute lAttribute = attrFactory.createLongAttribute(infoFactory.createEntityInfo("testLongAttributeName14"));
        Attribute strAttribute = attrFactory.createStringAttribute(infoFactory.createEntityInfo("testStringAttributeName14"));
        attributeRepo.save(lAttribute);
        attributeRepo.save(strAttribute);
        objectInstanceRepo.save(instance);
        instance.addAttrValue(valFactory.createLongAttrVal(lAttribute,1L));
        instance.addAttrValue(valFactory.createStringAttrVal(strAttribute,"test_attr_value"));
        objectInstanceRepo.save(instance);
        Assert.assertNotNull(instance.getId());
        ObjectInstance newInstance = objectInstanceRepo.findObjectInstanceById(instance.getId()).orElse(null);
        Assert.assertNotNull(newInstance);
        Assert.assertEquals(2,newInstance.getAttrValues().size());
    }

    @Test
    public void removeAttrValTest() {
        ObjectClass aClass = objectClassFactory.createObjectClass(infoFactory.createEntityInfo("objectClassName8"));
        classRepo.save(aClass);
        ObjectInstance instance = objectInstanceFactory.createObjectInstance(aClass);
        Attribute lAttribute = attrFactory.createLongAttribute(infoFactory.createEntityInfo("testLongAttributeName12"));
        Attribute strAttribute = attrFactory.createStringAttribute(infoFactory.createEntityInfo("testStringAttributeName12"));
        attributeRepo.save(lAttribute);
        attributeRepo.save(strAttribute);
        objectInstanceRepo.save(instance);
        LongAttributeValue lVal = valFactory.createLongAttrVal(lAttribute,1L);
        instance.addAttrValue(lVal);
        StringAttributeValue strVal = valFactory.createStringAttrVal(strAttribute, "test_attr_value");
        instance.addAttrValue(strVal);
        objectInstanceRepo.save(instance);
        Assert.assertNotNull(instance.getId());
        ObjectInstance newInstance = objectInstanceRepo.findObjectInstanceById(instance.getId()).orElse(null);
        Assert.assertNotNull(newInstance);
        Assert.assertEquals(2,newInstance.getAttrValues().size());
        newInstance.removeAttrValue(lVal);
        objectInstanceRepo.save(newInstance);
        ObjectInstance instance1 = objectInstanceRepo.findObjectInstanceById(instance.getId()).orElse(null);
        Assert.assertNotNull(instance1);
        Assert.assertEquals(1,instance1.getAttrValues().size());
    }

    @Test
    public void changeAttrValTest() {
        ObjectClass aClass = objectClassFactory.createObjectClass(infoFactory.createEntityInfo("objectClassName9"));
        classRepo.save(aClass);
        ObjectInstance instance = objectInstanceFactory.createObjectInstance(aClass);
        Attribute lAttribute = attrFactory.createLongAttribute(infoFactory.createEntityInfo("testLongAttributeName13"));
        Attribute strAttribute = attrFactory.createStringAttribute(infoFactory.createEntityInfo("testStringAttributeName13"));
        attributeRepo.save(lAttribute);
        attributeRepo.save(strAttribute);
        objectInstanceRepo.save(instance);
        LongAttributeValue lVal = valFactory.createLongAttrVal(lAttribute,1L);
        instance.addAttrValue(lVal);
        StringAttributeValue strVal = valFactory.createStringAttrVal(strAttribute, "test_attr_value");
        instance.addAttrValue(strVal);
        objectInstanceRepo.save(instance);
        Assert.assertNotNull(instance.getId());
        ObjectInstance newInstance = objectInstanceRepo.findObjectInstanceById(instance.getId()).orElse(null);
        Assert.assertNotNull(newInstance);
        Assert.assertEquals(2,newInstance.getAttrValues().size());
        ((LongAttributeValue)newInstance.getAttrValue(lVal.getAttr().getInfo().getName())).setValue(10L);
        objectInstanceRepo.save(newInstance);
        ObjectInstance instance1 = objectInstanceRepo.findObjectInstanceById(instance.getId()).orElse(null);
        Assert.assertNotNull(instance1);
        Long l1 = ((LongAttributeValue) instance1.getAttrValue(lAttribute.getInfo().getName())).getValue();
        Assert.assertEquals(l1, Long.valueOf(10));

    }
}
