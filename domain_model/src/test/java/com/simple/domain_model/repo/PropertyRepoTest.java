package com.simple.domain_model.repo;

import com.simple.domain_model.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertyRepoTest {

    @Autowired
    DataModelFactory.PropertyFactory propFactory;

    @Autowired
    DataModelFactory.EntityInfoFactory infoFactory;

    @Autowired
    private PropertyRepo repo;

    @Test (expected = DataIntegrityViolationException.class)
    public void doubletPropertyNameTest() {
        Property property = propFactory.createStringProperty(infoFactory.createEntityInfo("property_name"));
        Property property1 = propFactory.createStringProperty(infoFactory.createEntityInfo("property_name"));

        repo.save(property);
        repo.save(property1);

    }

    @Test (expected = ConstraintViolationException.class)
    public void nullInfoPropertyTest() {
        propFactory.createStringProperty(null);
    }

    @Test
    public void notEqualsPropertyTest() {
        Property property = propFactory.createStringProperty(infoFactory.createEntityInfo("property_name"));
        Property property1 = propFactory.createStringProperty(infoFactory.createEntityInfo("property_name1"));
        Assert.assertNotEquals(property,property1);
    }

    @Test
    public void longPropertyPersistenceTest(){

        Property property = propFactory.createLongProperty(infoFactory.createEntityInfo("long_property_name_1"));

        repo.save(property);

        Assert.assertNotNull(property.getId());
        Property newProperty = repo.findById(property.getId()).orElse(null);
        Assert.assertNotNull(newProperty);
        Assert.assertEquals(property,newProperty);
    }

    @Test
    public void stringPropertyPersistenceTest(){

        Property property = propFactory.createStringProperty(infoFactory.createEntityInfo("string_property_name_1"));
        repo.save(property);

        Assert.assertNotNull(property.getId());
        Property newProperty = repo.findById(property.getId()).orElse(null);
        Assert.assertNotNull(newProperty);
        Assert.assertEquals(property,newProperty);
    }

    @Test
    public void dateTimePropertyPersistenceTest(){
        Property property = propFactory.createDateProperty(infoFactory.createEntityInfo("date_property_name_1"));
        repo.save(property);

        Assert.assertNotNull(property.getId());
        Property newProperty = repo.findById(property.getId()).orElse(null);
        Assert.assertNotNull(newProperty);
        Assert.assertEquals(property,newProperty);
    }

    @Test
    public void choicePropertyPersistenceTest() {
        Property property = propFactory.createChoiceProperty(infoFactory.createEntityInfo("choice_value_name", "choice display name"));
        repo.save(property);

        Assert.assertNotNull(property.getId());
        Property newProperty = repo.findById(property.getId()).orElse(null);
        Assert.assertNotNull(newProperty);
        Assert.assertEquals(property,newProperty);
    }
}
