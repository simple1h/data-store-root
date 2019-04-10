package com.simple.domain_model.services;

import com.simple.domain_model.domain.DataModelFactory;
import com.simple.domain_model.domain.EntityInfo;
import com.simple.domain_model.domain.ObjectClass;
import com.simple.domain_model.repo.ObjectClassRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ObjectClassServiceTest {

    private ObjectClassService service;
    @Mock
    private DataModelFactory.ObjectClassFactory objectClassFactory;
    @Mock
    private DataModelFactory.EntityInfoFactory infoFactory;
    @Mock
    private DataModelFactory.PropertyValueFactory valueFactory;
    @Mock
    private ObjectClassRepo repo;

    private static final String NAME = "name";
    private static final String DISPLAY_NAME = "displayName";
    private static final String DESCRIPTION = "description";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.service = new ObjectClassService(objectClassFactory, infoFactory, repo, valueFactory);
    }

    @Test
    public void createObjectClassTest() {

        when(infoFactory.createEntityInfo(anyString(),anyString(),anyString())).thenReturn(new EntityInfo());
        when(objectClassFactory.createObjectClass(any(EntityInfo.class))).thenReturn(new ObjectClass());

        ObjectClass objectClass = service.createObjectClass(NAME, DISPLAY_NAME, DESCRIPTION);

        Assert.assertNotNull(objectClass);
        verify(objectClassFactory, times(1)).createObjectClass(any(EntityInfo.class));
    }

    @Test
    public void saveObjectClassTest() {
        when(infoFactory.createEntityInfo(anyString(),anyString(),anyString())).thenReturn(new EntityInfo());
        when(objectClassFactory.createObjectClass(any(EntityInfo.class))).thenReturn(new ObjectClass());

        ObjectClass objectClass = service.createObjectClass(NAME, DISPLAY_NAME, DESCRIPTION);
        service.save(objectClass);

        Assert.assertNotNull(objectClass);
        verify(objectClassFactory, times(1)).createObjectClass(any(EntityInfo.class));
        verify(repo, times(1)).save(any(ObjectClass.class));
    }

    @Test
    public void saveAllObjectClassTest() {
        when(infoFactory.createEntityInfo(anyString(),anyString(),anyString())).thenReturn(new EntityInfo());
        when(objectClassFactory.createObjectClass(any(EntityInfo.class))).thenReturn(new ObjectClass());

        service.saveAll(Arrays.asList(
                service.createObjectClass(NAME, DISPLAY_NAME, DESCRIPTION),
                service.createObjectClass(NAME, DISPLAY_NAME, DESCRIPTION),
                service.createObjectClass(NAME, DISPLAY_NAME, DESCRIPTION),
                service.createObjectClass(NAME, DISPLAY_NAME, DESCRIPTION),
                service.createObjectClass(NAME, DISPLAY_NAME, DESCRIPTION)
        ));

        verify(objectClassFactory, times(5)).createObjectClass(any(EntityInfo.class));
        verify(repo, times(1)).saveAll(anyIterable());
    }

    @Test
    public void findByIdTest() {
        when(repo.findById(anyLong())).thenReturn(Optional.of(new ObjectClass()));

        ObjectClass objectClass = service.findById(1L).get();

        Assert.assertNotNull(objectClass);
        verify(repo, times(1)).findById(anyLong());
    }

    @Test
    public void findByInfoNameTest() {
        when(repo.findByInfoName(anyString())).thenReturn(Optional.of(new ObjectClass()));

        ObjectClass objectClass = service.findByInfoName(NAME).get();

        Assert.assertNotNull(objectClass);
        verify(repo, times(1)).findByInfoName(anyString());
    }

    @Test
    public void deleteByIdTest() {
        service.deleteById(1L);
        verify(repo, times(1)).deleteById(anyLong());
    }

    @Test
    public void deleteAllTest() {
        when(infoFactory.createEntityInfo(anyString(),anyString(),anyString())).thenReturn(new EntityInfo());
        when(objectClassFactory.createObjectClass(any(EntityInfo.class))).thenReturn(new ObjectClass());

        service.deleteAll(Arrays.asList(
                service.createObjectClass(NAME, DISPLAY_NAME, DESCRIPTION),
                service.createObjectClass(NAME, DISPLAY_NAME, DESCRIPTION),
                service.createObjectClass(NAME, DISPLAY_NAME, DESCRIPTION),
                service.createObjectClass(NAME, DISPLAY_NAME, DESCRIPTION),
                service.createObjectClass(NAME, DISPLAY_NAME, DESCRIPTION)
        ));

        verify(repo, times(1)).deleteAll(anyIterable());

    }
}
