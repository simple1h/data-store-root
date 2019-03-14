package com.simple.domain_model.repo;

import com.simple.domain_model.domain.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-inttest.properties")
public class AttributeRepoTest {


    private  Property pString;
    private  Property pString1;
    private  Property pLong;
    private  Property pDate;

    private  PropertyValue stringVal;
    private  PropertyValue longVal;
    private  PropertyValue dateVal;
    
    private static boolean isSaved;

    @Autowired
    private AttributeRepo attributeRepo;

    @Autowired
    private PropertyRepo propertyRepo;

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


    //    TODO вынести инициализацию свойств в создание класса
    @Before
    public void setUp() throws Exception {

        if (!isSaved) {
            pString = propFactory.createStringProperty(infoFactory.createEntityInfo("string property","display string property","best string property an ether"));
            pString1 = propFactory.createStringProperty(infoFactory.createEntityInfo("string property1","display string property","best string property an ether"));
            pLong = propFactory.createLongProperty(infoFactory.createEntityInfo("LONG property","display LONG property","best LONG property an ether"));
            pDate = propFactory.createDateProperty(infoFactory.createEntityInfo("Date property","display Date property","best string Date an ether"));

            propertyRepo.save(pLong);
            propertyRepo.save(pString);
            propertyRepo.save(pString1);
            propertyRepo.save(pDate);

            isSaved = true;
        } else {
            pLong = propertyRepo.findByInfoName("LONG property");
            pString = propertyRepo.findByInfoName("string property");
            pString1 = propertyRepo.findByInfoName("string property1");
            pDate = propertyRepo.findByInfoName("Date property");
        }

        stringVal = propValFactory.createStringValue(pString,"test_value");
        longVal = propValFactory.createLongValue(pLong, 1L);
        dateVal = propValFactory.createDateValue(pDate, new Date());

    }

    @Test
    public void attributePersistenceTest() {

        Attribute attribute = attrFactory.createStringAttribute(infoFactory.createEntityInfo("testAttributeName"));
        attribute.getProperties().addProperty(stringVal);


        attributeRepo.save(attribute);
        Assert.assertNotNull(attribute.getId());
        Attribute newAttribute = attributeRepo.findById(attribute.getId()).orElse(null);
        Assert.assertNotNull(newAttribute);
    }

    @Test
    public void attributeEmptyPropertiesTest() {
        Attribute attribute = attrFactory.createStringAttribute(infoFactory.createEntityInfo("testAttributeName1"));
        attributeRepo.save(attribute);
        Assert.assertNotNull(attribute.getId());
        Attribute newAttribute = attributeRepo.findById(attribute.getId()).orElse(null);
        Assert.assertNotNull(newAttribute);
    }

    @Test
    public void delegateMethodsTest() {
        Attribute attribute = attrFactory.createStringAttribute(infoFactory.createEntityInfo("testAttributeName2"));
        attribute.getProperties()
                    .addProperty(stringVal)
                    .addProperty(longVal)
                    .addProperty(dateVal);

        attributeRepo.save(attribute);

        Attribute newAttribute = attributeRepo.findById(attribute.getId()).orElse(null);
        Assert.assertNotNull(newAttribute);
        Assert.assertEquals(attribute, newAttribute);

        newAttribute.getInfo().setDisplayName("attribute2 display name");
        newAttribute.getInfo().setDescription("attribute2 description");
        newAttribute.getProperties().removeProperty(dateVal);
        newAttribute.getProperties().overwriteValue(propValFactory.createLongValue(pLong,2L));
        newAttribute.getProperties().addProperty(propValFactory.createStringValue(pString1,"test2"));

        attributeRepo.save(newAttribute);

        Attribute newAttribute1 = attributeRepo.findById(newAttribute.getId()).orElse(null);
        Assert.assertNotNull(newAttribute1);
        Assert.assertEquals(newAttribute, newAttribute1);

    }
}