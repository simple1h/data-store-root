package com.simple.domain_model.repo;

import com.simple.domain_model.domain.ChoiceSet;
import com.simple.domain_model.domain.DataModelFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(locations = "classpath:application-inttest.properties")
public class ChoiceSetRepoTest {

    @Autowired
    ChoiceSetRepo repo;

    @Autowired
    DataModelFactory.ChoiceSetFactory choiceFactory;
    @Autowired
    DataModelFactory.ChoiceValueFactory choiceValFactory;
    @Autowired
    DataModelFactory.EntityInfoFactory infoFactory;


    @Test
    @DirtiesContext
    public void choiceSetPersistTest() {
        String name = "test_choice_set";
        String displayName = "display name";
        String description = "description";

        ChoiceSet set = choiceFactory.createChoiceSet(infoFactory.createEntityInfo(name, displayName, description));
        repo.save(set);
        Assert.assertNotNull(set.getId());
        ChoiceSet newSet = repo.findById(set.getId()).orElse(null);
        Assert.assertNotNull(newSet);
    }

    @Test
    @DirtiesContext
    public void addChoiceValueTest() {
        String name = "colors2";
        String displayName = "true colors";
        String description = "best true colors";

        ChoiceSet colorSet = choiceFactory.createChoiceSet(infoFactory.createEntityInfo(name, displayName, description))
                .addValue(choiceValFactory.createChoiceValue("red", 1L))
                .addValue(choiceValFactory.createChoiceValue("blue",2L))
                .addValue(choiceValFactory.createChoiceValue("black",3L))
                .addValue(choiceValFactory.createChoiceValue("white",4L))
                .addValue(choiceValFactory.createChoiceValue("yellow",5L));

        repo.save(colorSet);
        Assert.assertNotNull(colorSet.getId());
        ChoiceSet newSet = repo.findById(colorSet.getId()).orElse(null);
        Assert.assertNotNull(newSet);
        Assert.assertEquals(5,newSet.getValueList().size());

    }

    @Test
    @DirtiesContext
    public void addChoiceValuesStreamStyleTest() {
        String name = "digit words";

        ChoiceSet digits = choiceFactory.createChoiceSet(infoFactory.createEntityInfo(name));
        Long[] valueId = {1L};
        Stream.of("one", "two", "three", "four", "five", "six").
                forEach(color -> digits.addValue(choiceValFactory.createChoiceValue(color,valueId[0]++)));

        repo.save(digits);
        Assert.assertNotNull(digits.getId());
        ChoiceSet newSet = repo.findById(digits.getId()).orElse(null);
        Assert.assertNotNull(newSet);
        Assert.assertEquals(6,newSet.getValueList().size());

    }

    @Test
    @DirtiesContext
    public void hirarchiListTest() {
        String name = "colors";
        String displayName = "true colors";
        String description = "best true colors";

        ChoiceSet colorSet = choiceFactory.createChoiceSet(infoFactory.createEntityInfo(name, displayName, description));
        colorSet.addValuesNode(choiceValFactory.createChoiceValue("red",1L))
                    .addValue(choiceValFactory.createChoiceValue("sub_red1",2L))
                    .addValue(choiceValFactory.createChoiceValue("sub_red2",3L))
                    .addValue(choiceValFactory.createChoiceValue("sub_red4",4L))
                    .addValuesNode(choiceValFactory.createChoiceValue("sub_red3",5L))
                        .addValue(choiceValFactory.createChoiceValue("sub_sub_red3",6L))
                        .addValue(choiceValFactory.createChoiceValue("sub_sub_red4",7L))
                        .addValue(choiceValFactory.createChoiceValue("sub_sub_red5",8L))
                        .addValue(choiceValFactory.createChoiceValue("sub_sub_red6",9L))
                    .getParent()
                .getChoiceSet()
                .addValue(choiceValFactory.createChoiceValue("blue",10L))
                .addValue(choiceValFactory.createChoiceValue("black",11L))
                .addValue(choiceValFactory.createChoiceValue("white",12L))
                .addValue(choiceValFactory.createChoiceValue("yellow",13L));

        repo.save(colorSet);
        Assert.assertNotNull(colorSet.getId());
        ChoiceSet newSet = repo.findById(colorSet.getId()).orElse(null);
        Assert.assertNotNull(newSet);
        Assert.assertEquals(colorSet.getValueList().size(), newSet.getValueList().size());
    }

    @Test
    @DirtiesContext
    public void readOnlyChoiceSetTest() {
        String name = "colors1";
        String displayName = "true colors";
        String description = "best true colors";

        ChoiceSet colorSet = choiceFactory.createChoiceSet(infoFactory.createEntityInfo(name, displayName, description));
        repo.save(colorSet);
        Assert.assertNotNull(colorSet.getId());
        ChoiceSet newSet = repo.findById(colorSet.getId()).orElse(null);
        Assert.assertNotNull(newSet);
        newSet.getInfo().setDescription("new value for description field");
        repo.save(newSet);
    }
}