package com.simple.domain_model.repo;

import com.simple.domain_model.domain.TestEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestEntityRepoTest {

    private static final String TEST_VALUE = "test string value";

    @Autowired
    private TestEntityRepo testRepo;

    @Test
    public void testPersistence() {

        TestEntity testEntity = new TestEntity();
        testEntity.setStrVal(TEST_VALUE);

        testRepo.save(testEntity);

        Assert.assertNotNull(testEntity.getId());

        TestEntity newTestEntity = testRepo.findById(testEntity.getId()).orElse(null);
        Assert.assertEquals(testEntity.getStrVal(), newTestEntity.getStrVal());
    }
}