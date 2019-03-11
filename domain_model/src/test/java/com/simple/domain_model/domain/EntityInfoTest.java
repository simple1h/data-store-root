package com.simple.domain_model.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntityInfoTest {

    @Autowired
    DataModelFactory.EntityInfoFactory infoFactory;

    @Test
    public void createEntityInfoTest() {
        EntityInfo info = infoFactory.createEntityInfo("test_name", "display_name", "description");
        Assert.assertEquals("test_name",info.getName());
        Assert.assertEquals("description",info.getDescription());
        Assert.assertEquals("display_name",info.getDisplayName());
    }

    @Test
    public void partialInfoCreateTest() {
        EntityInfo info = infoFactory.createEntityInfo("test_name", null, null);
        Assert.assertEquals("test_name",info.getName());
        Assert.assertNull(info.getDescription());
        Assert.assertNull(info.getDisplayName());
    }

    @Test
    public void partialCreate_2Args() {
        EntityInfo info = infoFactory.createEntityInfo("test_name", "display_name");
        Assert.assertEquals("test_name",info.getName());
        Assert.assertEquals("display_name",info.getDisplayName());
        Assert.assertNull(info.getDescription());

    }

    @Test
    public void partialCreate_1Args() {
        EntityInfo info = infoFactory.createEntityInfo("test_name");
        Assert.assertEquals("test_name",info.getName());
        Assert.assertNull(info.getDescription());
        Assert.assertNull(info.getDisplayName());

    }



    @Test (expected = ConstraintViolationException.class)
    public void faultNullCreateTest() {
        EntityInfo info = infoFactory.createEntityInfo(null, "display_name", "description");
    }

    @Test (expected = ConstraintViolationException.class)
    public void faultEmptyCreateTest() {
        EntityInfo info = infoFactory.createEntityInfo("", "display_name", "description");
    }

    @Test (expected = ConstraintViolationException.class)
    public void faultNullCreate_2Args_Test() {
        EntityInfo info = infoFactory.createEntityInfo(null, "display_name");
    }

    @Test (expected = ConstraintViolationException.class)
    public void faultEmptyCreate_2Args1_Test() {
        EntityInfo info = infoFactory.createEntityInfo("", "display_name");
    }

    @Test (expected = ConstraintViolationException.class)
    public void faultEmptyCreate_1Args1_Test() {
        EntityInfo info = infoFactory.createEntityInfo("");
    }

    @Test
    public void equals() {
        EntityInfo info = infoFactory.createEntityInfo("test_name");
        EntityInfo info1 = infoFactory.createEntityInfo("test_name");
        Assert.assertEquals(info, info1);
    }
}