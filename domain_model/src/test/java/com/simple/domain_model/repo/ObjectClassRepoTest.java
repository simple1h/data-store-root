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

import java.util.Date;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-inttest.properties")
public class ObjectClassRepoTest {

    @Autowired
    private ObjectClassRepo classRepo;

    @Autowired
    private PropertyRepo propertyRepo;

    private Property pString;
    private  Property pString1;
    private  Property pLong;
    private  Property pDate;

    private PropertyValue stringVal;
    private  PropertyValue longVal;
    private  PropertyValue dateVal;

    private static boolean isSaved;

    @Autowired
    DataModelFactory.PropertyValueFactory propValFactory;
    @Autowired
    DataModelFactory.PropertyFactory propFactory;
    @Autowired
    DataModelFactory.EntityInfoFactory infoFactory;
    @Autowired
    DataModelFactory.AttributeFactory attrFactory;
    @Autowired
    DataModelFactory.ObjectClassFactory objectClassFactory;

    @Before
    public void setUp() throws Exception {

        if (!isSaved) {
            pString = propFactory.createStringProperty(infoFactory.createEntityInfo("object_string property","display string property","best string property an ether"));
            pString1 = propFactory.createStringProperty(infoFactory.createEntityInfo("object_string property1","display string property","best string property an ether"));
            pLong = propFactory.createLongProperty(infoFactory.createEntityInfo("object_long property","display LONG property","best LONG property an ether"));
            pDate = propFactory.createDateProperty(infoFactory.createEntityInfo("object_date property","display Date property","best string Date an ether"));

            propertyRepo.save(pLong);
            propertyRepo.save(pString);
            propertyRepo.save(pString1);
            propertyRepo.save(pDate);

            isSaved = true;
        } else {
            pLong = propertyRepo.findByInfoName("object_long property");
            pString = propertyRepo.findByInfoName("object_string property");
            pString1 = propertyRepo.findByInfoName("object_string property1");
            pDate = propertyRepo.findByInfoName("object_date property");
        }

        stringVal = propValFactory.createStringValue(pString,"test_value");
        longVal = propValFactory.createLongValue(pLong, 1L);
        dateVal = propValFactory.createDateValue(pDate, new Date());

    }

    @Test
    public void persistEmptyObjectClassTest() {
        ObjectClass aClass = objectClassFactory.createObjectClass(infoFactory.createEntityInfo("objectClassName"));
        classRepo.save(aClass);
        Assert.assertNotNull(aClass.getId());
        ObjectClass newClass = classRepo.findById(aClass.getId()).orElse(null);
        Assert.assertNotNull(newClass);
    }

    @Test
    public void persistObjectClassTest() {
        ObjectClass aClass = objectClassFactory.createObjectClass(infoFactory.createEntityInfo("objectClassName1"));
        Stream.of(stringVal, longVal, dateVal).forEach(propVal -> aClass.properties().addProperty(propVal));
        classRepo.save(aClass);
        Assert.assertNotNull(aClass.getId());
    }

    @Test
    public void hierarchicalObjectClassTest() {
        ObjectClass aClass = objectClassFactory.createObjectClass(infoFactory.createEntityInfo("objectClassName2"));
        ObjectClass aClassTemp = objectClassFactory.createObjectClass(infoFactory.createEntityInfo("objectClassName2"));
        aClass
                .addChild(objectClassFactory.createObjectClass(infoFactory.createEntityInfo("sub1_objectClassName2")))
                .addChild(objectClassFactory.createObjectClass(infoFactory.createEntityInfo("sub2_objectClassName2")))
                .addChildNode(objectClassFactory.createObjectClass(infoFactory.createEntityInfo("sub3_objectClassName2")))
                    .addChild(objectClassFactory.createObjectClass(infoFactory.createEntityInfo("sub1_sub3_objectClassName2")))
                    .addChild(objectClassFactory.createObjectClass(infoFactory.createEntityInfo("sub2_sub3_objectClassName2")))
                    .addChild(objectClassFactory.createObjectClass(infoFactory.createEntityInfo("sub3_sub3_objectClassName2")))
                    .addChild(objectClassFactory.createObjectClass(infoFactory.createEntityInfo("sub4_sub3_objectClassName2")))
                    .addChild(aClassTemp)
                    .removeChild(aClassTemp)
                    .parent()
                .addChild(objectClassFactory.createObjectClass(infoFactory.createEntityInfo("sub4_objectClassName1")));
        classRepo.save(aClass);
        Assert.assertNotNull(aClass.getId());
        ObjectClass aClassNew = classRepo.findById(aClass.getId()).orElse(null);
        Assert.assertNotNull(aClassNew);
        Assert.assertNotNull(aClassNew.childs());
        Assert.assertEquals(4,aClassNew.childs().size());
    }

    @Test
    public void removeChildObjectClassTest() {
        ObjectClass aClass = objectClassFactory.createObjectClass(infoFactory.createEntityInfo("objectClassName3"));
        ObjectClass aClassSub1 = objectClassFactory.createObjectClass(infoFactory.createEntityInfo("sub1_objectClassName3"));
        ObjectClass aClassSub2 = objectClassFactory.createObjectClass(infoFactory.createEntityInfo("sub2_objectClassName3"));
        aClass.addChild(aClassSub1).addChild(aClassSub2);
        classRepo.save(aClass);
        Assert.assertNotNull(aClass.getId());
        ObjectClass aClassNew = classRepo.findById(aClass.getId()).orElse(null);
        Assert.assertEquals(2,aClassNew.childs().size());
        aClassNew.removeChild(aClassSub1);
        classRepo.save(aClassNew);
        Long id = aClassNew.getId();
        Assert.assertNotNull(id);
        ObjectClass aClassNew1 = classRepo.findById(id).orElse(null);
        Assert.assertNotNull(aClassNew1);
        Assert.assertEquals(1,aClassNew1.childs().size());
    }

    @Test
    public void attributeObjectClassAssociateTest() {
        Attribute attribute = attrFactory.createStringAttribute(infoFactory.createEntityInfo("attributeObjectClassAssociate_name"));
//        attributeRepo.save(attribute);
        ObjectClass aClass = objectClassFactory.createObjectClass(infoFactory.createEntityInfo("objectClassName4"));
        aClass.addAttribute(attribute);
        classRepo.save(aClass);
        Assert.assertNotNull(aClass.getId());
        ObjectClass aClassNew = classRepo.findById(aClass.getId()).orElse(null);
        Assert.assertNotNull(aClassNew);
        Assert.assertNotNull(aClassNew.attributes());
        Assert.assertEquals(1,aClassNew.attributes().size());
    }

    @Test
    public void attributeObjectClassAssociateRemoveTest() {
        Attribute attribute = attrFactory.createStringAttribute(infoFactory.createEntityInfo("attributeObjectClassAssociate_name1"));
        ObjectClass aClass = objectClassFactory.createObjectClass(infoFactory.createEntityInfo("objectClassName5"));
        aClass.addAttribute(attribute);
        classRepo.save(aClass);
        Assert.assertNotNull(aClass.getId());
        ObjectClass aClassNew = classRepo.findById(aClass.getId()).orElse(null);
        aClassNew.removeAttribute(attribute);
        classRepo.save(aClassNew);
        ObjectClass aClassNew1 = classRepo.findById(aClass.getId()).orElse(null);
        Assert.assertNotNull(aClassNew1);
        Assert.assertNotNull(aClassNew1.attributes());
        Assert.assertEquals(0,aClassNew1.attributes().size());
    }
}
