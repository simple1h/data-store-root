package com.simple.domain_model.services;

import com.simple.domain_model.domain.ChoiceSet;
import com.simple.domain_model.domain.ChoiceValue;
import com.simple.domain_model.domain.DataModelFactory;
import com.simple.domain_model.domain.EntityInfo;
import com.simple.domain_model.repo.ChoiceSetRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ChoiceSetServiceTest {

    private ChoiceSetService service;
    @Mock
    private DataModelFactory.ChoiceSetFactory choiceSetFactory;
    @Mock
    private DataModelFactory.EntityInfoFactory infoFactory;
    @Mock
    private DataModelFactory.ChoiceValueFactory choiceValueFactory;
    @Mock
    private ChoiceSetRepo repo;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new ChoiceSetService(choiceSetFactory, infoFactory, repo, choiceValueFactory);
    }

    @Test
    public void createChoiceSetTest() {
        when(infoFactory.createEntityInfo(anyString(),anyString(),anyString())).thenReturn(new EntityInfo());
        when(choiceSetFactory.createChoiceSet(any(EntityInfo.class))).thenReturn(new ChoiceSet());

        ChoiceSet choiceSet = service.createChoiceSet("name", "displayName", "description");
        Assert.assertNotNull(choiceSet);

        verify(infoFactory, times(1)).createEntityInfo(anyString(),anyString(),anyString());
        verify(choiceSetFactory, times(1)).createChoiceSet(any(EntityInfo.class));
    }

    @Test
    public void createChoiceValueTest() {
        when(choiceValueFactory.createChoiceValue(anyString(),anyLong())).thenReturn(new ChoiceValue());

        ChoiceValue value = service.createChoiceValue("value", 1L);
        Assert.assertNotNull(value);
        verify(choiceValueFactory,times(1)).createChoiceValue(anyString(),anyLong());
    }

    @Test
    public void saveChoiceSetTest() {
        when(infoFactory.createEntityInfo(anyString(),anyString(),anyString())).thenReturn(new EntityInfo());
        when(choiceSetFactory.createChoiceSet(any(EntityInfo.class))).thenReturn(new ChoiceSet());

        ChoiceSet choiceSet = service.createChoiceSet("name", "displayName", "description");
        service.save(choiceSet);

        Assert.assertNotNull(choiceSet);
        verify(infoFactory, times(1)).createEntityInfo(anyString(),anyString(),anyString());
        verify(choiceSetFactory, times(1)).createChoiceSet(any(EntityInfo.class));
        verify(repo, times(1)).save(any(ChoiceSet.class));
    }

    @Test
    public void findByIdTest() {

        when(repo.findById(anyLong())).thenReturn(Optional.of(new ChoiceSet()));

        ChoiceSet choiceSet = service.findById(1L).get();
        Assert.assertNotNull(choiceSet);

        verify(repo, times(1)).findById(anyLong());
    }

    @Test
    public void findByNameTest() {

        when(repo.findByInfoName(anyString())).thenReturn(Optional.of(new ChoiceSet()));

        ChoiceSet choiceSet = service.findByInfoName("name").get();
        Assert.assertNotNull(choiceSet);

        verify(repo, times(1)).findByInfoName(anyString());
    }

    @Test
    public void deleteChoiceSetByIdTest() {
        service.deleteById(1L);
        verify(repo, times(1)).deleteById(anyLong());
    }
}
