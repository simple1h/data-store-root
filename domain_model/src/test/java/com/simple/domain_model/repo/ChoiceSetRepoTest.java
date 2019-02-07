package com.simple.domain_model.repo;

import com.simple.domain_model.domain.ChoiceSet;
import com.simple.domain_model.domain.DataModelFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
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
    public void choiceSetPersistTest() {
        String name = "test_choice_set";
        String displayName = "display name";
        String description = "description";

        ChoiceSet set = choiceFactory.createChoiceSet(infoFactory.createEntityInfo(name, displayName, description));
        repo.save(set);
        Assert.assertNotNull(set.getChoiceId());
        ChoiceSet newSet = repo.findById(set.getChoiceId()).orElse(null);
        Assert.assertNotNull(newSet);
    }

    @Test
    public void addChoiceValueTest() {
        String name = "colors2";
        String displayName = "true colors";
        String description = "best true colors";

        ChoiceSet colorSet = choiceFactory.createChoiceSet(infoFactory.createEntityInfo(name, displayName, description))
                .addValue(choiceValFactory.createChoiceValue("red"))
                .addValue(choiceValFactory.createChoiceValue("blue"))
                .addValue(choiceValFactory.createChoiceValue("black"))
                .addValue(choiceValFactory.createChoiceValue("white"))
                .addValue(choiceValFactory.createChoiceValue("yellow"));

        repo.save(colorSet);
        Assert.assertNotNull(colorSet.getChoiceId());
        ChoiceSet newSet = repo.findById(colorSet.getChoiceId()).orElse(null);
        Assert.assertNotNull(newSet);
        Assert.assertEquals(newSet.getValueList().size(), 5);

    }

    @Test
    public void addChoiceValuesStreamStyleTest() {
        String name = "digit words";

        ChoiceSet digits = choiceFactory.createChoiceSet(infoFactory.createEntityInfo(name));
        Stream.of("one", "two", "three", "four", "five", "six").
                forEach(color -> digits.addValue(choiceValFactory.createChoiceValue(color)));

        repo.save(digits);
        Assert.assertNotNull(digits.getChoiceId());
        ChoiceSet newSet = repo.findById(digits.getChoiceId()).orElse(null);
        Assert.assertNotNull(newSet);
        Assert.assertEquals(newSet.getValueList().size(), 6);

    }

    @Test
    public void hirarchiListTest() {
        String name = "colors";
        String displayName = "true colors";
        String description = "best true colors";

        ChoiceSet colorSet = choiceFactory.createChoiceSet(infoFactory.createEntityInfo(name, displayName, description));
        colorSet.addValueAsParent(choiceValFactory.createChoiceValue("red"))
                    .addValue(choiceValFactory.createChoiceValue("sub_red1"))
                    .addValue(choiceValFactory.createChoiceValue("sub_red2"))
                    .addValue(choiceValFactory.createChoiceValue("sub_red4"))
                    .addValueAsParent(choiceValFactory.createChoiceValue("sub_red3"))
                        .addValue(choiceValFactory.createChoiceValue("sub_sub_red3"))
                        .addValue(choiceValFactory.createChoiceValue("sub_sub_red4"))
                        .addValue(choiceValFactory.createChoiceValue("sub_sub_red5"))
                        .addValue(choiceValFactory.createChoiceValue("sub_sub_red6"))
                    .getParentId()
                .getChoiceSet()
                .addValue(choiceValFactory.createChoiceValue("blue"))
                .addValue(choiceValFactory.createChoiceValue("black"))
                .addValue(choiceValFactory.createChoiceValue("white"))
                .addValue(choiceValFactory.createChoiceValue("yellow"));

        repo.save(colorSet);
        Assert.assertNotNull(colorSet.getChoiceId());
        ChoiceSet newSet = repo.findById(colorSet.getChoiceId()).orElse(null);
        Assert.assertNotNull(newSet);
        Assert.assertEquals(colorSet.getValueList().size(), newSet.getValueList().size());
    }

    @Test
    public void readOnlyChoiceSetTest() {
        String name = "colors1";
        String displayName = "true colors";
        String description = "best true colors";

        ChoiceSet colorSet = choiceFactory.createChoiceSet(infoFactory.createEntityInfo(name, displayName, description));
        repo.save(colorSet);
        Assert.assertNotNull(colorSet.getChoiceId());
        ChoiceSet newSet = repo.findById(colorSet.getChoiceId()).orElse(null);
        Assert.assertNotNull(newSet);
        newSet.getInfo().setDescription("new value for description field");
        repo.save(newSet);
    }
}