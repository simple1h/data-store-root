package com.simple.domain_model.services;

import com.simple.domain_model.domain.DataModelFactory;
import com.simple.domain_model.domain.ObjectClass;
import com.simple.domain_model.domain.ObjectInstance;
import com.simple.domain_model.repo.ObjectInstanceRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ObjectInstanceServiceTest {

    private ObjectInstanceService service;
    @Mock
    private ObjectClassService objectClassService;
    @Mock
    private DataModelFactory.ObjectInstanceFactory objectInstanceFactory;
    @Mock
    private ObjectInstanceRepo repo;
    @Mock
    private DataModelFactory.AttributeValueFactory attrValFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new ObjectInstanceService(objectInstanceFactory, repo, attrValFactory);
    }

    @Test
    public void createObjectInstanceTest() {
        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(objectClassService.createObjectClass(anyString(),anyString(), anyString())).thenReturn(new ObjectClass());
        when(objectInstanceFactory.createObjectInstance(any(ObjectClass.class))).thenReturn(new ObjectInstance());

        ObjectInstance instance = service.createObject(objectClassService.createObjectClass(name,displayName,description));

        verify(objectInstanceFactory, times(1)).createObjectInstance(any(ObjectClass.class));
        Assert.assertNotNull(instance);
    }

    @Test
    public void saveObjectInstanceTest() {
        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(objectClassService.createObjectClass(anyString(),anyString(), anyString())).thenReturn(new ObjectClass());
        when(objectInstanceFactory.createObjectInstance(any(ObjectClass.class))).thenReturn(new ObjectInstance());

        ObjectInstance instance = service.createObject(objectClassService.createObjectClass(name,displayName,description));
        service.save(instance);

        Assert.assertNotNull(instance);
        verify(objectInstanceFactory, times(1)).createObjectInstance(any(ObjectClass.class));
        verify(repo, times(1)).save(any(ObjectInstance.class));
    }

    @Test
    public void findByIdTest() {

        when(repo.findObjectInstanceById(anyLong())).thenReturn(Optional.of(new ObjectInstance()));

        ObjectInstance instance = service.findById(1L).get();

        Assert.assertNotNull(instance);
        verify(repo,times(1)).findObjectInstanceById(anyLong());
    }

    @Test
    public void deleteByIdTest() {

        service.deleteById(1L);
        verify(repo, times(1)).deleteById(anyLong());

    }
}
