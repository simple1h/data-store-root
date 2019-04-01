package com.simple.domain_model.services;

import com.simple.domain_model.domain.ChoiceSet;
import com.simple.domain_model.domain.ChoiceValue;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-inttest.properties")
public class ChoiceSetServiceITTest {

    @Autowired
    private ChoiceSetService service;

    @Test
    public void simpleChoiceSetServiceTest() {

        ChoiceSet choiceSet = service.createChoiceSet("name", "displayName", "description");
        choiceSet.addValue(service.createChoiceValue("value1",1L));
        choiceSet.addValue(service.createChoiceValue("value2",2L));
        choiceSet.addValue(service.createChoiceValue("value3",3L));
        choiceSet.addValue(service.createChoiceValue("value4",4L));
        choiceSet.addValue(service.createChoiceValue("value5",5L));

        service.saveChoiceSet(choiceSet);
        Assert.assertNotNull(choiceSet.getId());

        ChoiceSet newChoiceSet = service.findById(choiceSet.getId()).get();
        ChoiceValue value = newChoiceSet.getValueList().stream().filter(fValue -> fValue.getValue().equalsIgnoreCase("value3")).findAny().orElse(null);
        newChoiceSet.removeValue(value);
        service.saveChoiceSet(newChoiceSet);
        Assert.assertEquals(4,service.findById(newChoiceSet.getId()).get().getValueList().size());
        ChoiceSet newChoiceSet1 = service.findByName("name").get();
        service.deleteChoiceSetById(newChoiceSet1.getId());
        Assert.assertNull(service.findById(newChoiceSet1.getId()).orElse(null));
    }

    @Test
    public void hierarchicalChoiceSetServiceTest() {
        ChoiceSet choiceSet = service.createChoiceSet("name", "displayName", "description");
        choiceSet.addValuesNode(service.createChoiceValue("value1",1L))
                    .addValue(service.createChoiceValue("sub1_value1",2L))
                    .addValue(service.createChoiceValue("sub2_value1",3L))
                    .addValue(service.createChoiceValue("sub3_value1",4L))
                    .addValuesNode(service.createChoiceValue("sub4_value1",5L))
                        .addValue(service.createChoiceValue("sub1_sub4_value1",6L))
                        .addValue(service.createChoiceValue("sub2_sub4_value1",7L))
                        .addValue(service.createChoiceValue("sub3_sub4_value1",8L))
                        .addValue(service.createChoiceValue("sub4_sub4_value1",9L))
                        .getParent()
                    .addValue(service.createChoiceValue("sub5_value1",10L))
                    .addValue(service.createChoiceValue("sub6_value1",11L))
                    .addValue(service.createChoiceValue("sub7_value1",12L))
                    .getChoiceSet()
                .addValue(service.createChoiceValue("value2", 13L))
                .addValue(service.createChoiceValue("value3", 14L))
                .addValue(service.createChoiceValue("value4", 15L));

        service.saveChoiceSet(choiceSet);
        Assert.assertNotNull(choiceSet.getId());
        ChoiceSet newChoiceSet = service.findById(choiceSet.getId()).get();
        Assert.assertEquals(4,newChoiceSet.getValueList().size());
        Assert.assertEquals(7,newChoiceSet.getValueList()
                .stream().filter(value -> value.getValueId() == 1L).findAny().get().getChildValues().size());
        Assert.assertEquals(4,newChoiceSet.getValueList()
                .stream().filter(value -> value.getValueId() == 1L).findAny().get().getChildValues()
                .stream().filter(value -> value.getValueId() == 5L).findAny().get().getChildValues().size());
        ChoiceValue valForDel = newChoiceSet.getValueList()
                .stream().filter(value -> value.getValueId() == 1L).findAny().get().getChildValues()
                .stream().filter(value -> value.getValueId() == 5L).findAny().get().getChildValues()
                .stream().filter(value -> value.getValueId() == 8L).findAny().get();
        newChoiceSet.getValueList()
                .stream().filter(value -> value.getValueId() == 1L).findAny().get().getChildValues()
                .stream().filter(value -> value.getValueId() == 5L).findAny().get().removeValue(valForDel);
        service.saveChoiceSet(newChoiceSet);
        ChoiceSet newChoiceSet1 = service.findByName("name").get();
        Assert.assertEquals(3,newChoiceSet1.getValueList()
                .stream().filter(value -> value.getValueId() == 1L).findAny().get().getChildValues()
                .stream().filter(value -> value.getValueId() == 5L).findAny().get().getChildValues().size());
        service.deleteChoiceSetById(newChoiceSet1.getId());
        Assert.assertNull(service.findById(newChoiceSet1.getId()).orElse(null));
    }
}
