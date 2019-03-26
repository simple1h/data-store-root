package com.simple.domain_model.service;

import com.simple.domain_model.domain.DataModelFactory;
import com.simple.domain_model.domain.EntityInfo;
import com.simple.domain_model.domain.Property;
import com.simple.domain_model.repo.PropertyRepo;
import com.simple.domain_model.serices.PropertyService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PropertyServiceTest {

    private PropertyService service;

    @Mock
    private DataModelFactory.EntityInfoFactory infoFactory;

    @Mock
    private DataModelFactory.PropertyFactory propertyFactory;

    @Mock
    private PropertyRepo propertyRepo;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new PropertyService(propertyFactory, infoFactory, propertyRepo);
    }

    @Test
    public void createStringPropertyTest() {

        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(infoFactory.createEntityInfo(anyString(),anyString(),anyString())).thenReturn(new EntityInfo());
        when(propertyFactory.createStringProperty(any(EntityInfo.class))).thenReturn(new Property());

        Property property =  service.createStringProperty(name, displayName, description);

        Assert.assertNotNull(property);
        verify(propertyFactory, times(1)).createStringProperty(any(EntityInfo.class));
        verify(infoFactory, times(1)).createEntityInfo(anyString(),anyString(),anyString());

    }
    @Test
    public void createLongPropertyTest() {

        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(infoFactory.createEntityInfo(anyString(),anyString(),anyString())).thenReturn(new EntityInfo());
        when(propertyFactory.createLongProperty(any(EntityInfo.class))).thenReturn(new Property());

        Property property =  service.createLongProperty(name, displayName, description);

        Assert.assertNotNull(property);
        verify(propertyFactory, times(1)).createLongProperty(any(EntityInfo.class));
        verify(infoFactory, times(1)).createEntityInfo(anyString(),anyString(),anyString());

    }
    @Test
    public void createDatePropertyTest() {

        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(infoFactory.createEntityInfo(anyString(),anyString(),anyString())).thenReturn(new EntityInfo());
        when(propertyFactory.createDateProperty(any(EntityInfo.class))).thenReturn(new Property());

        Property property =  service.createDateProperty(name, displayName, description);

        Assert.assertNotNull(property);
        verify(propertyFactory, times(1)).createDateProperty(any(EntityInfo.class));
        verify(infoFactory, times(1)).createEntityInfo(anyString(),anyString(),anyString());

    }
    @Test
    public void createChoicePropertyTest() {

        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(infoFactory.createEntityInfo(anyString(),anyString(),anyString())).thenReturn(new EntityInfo());
        when(propertyFactory.createChoiceProperty(any(EntityInfo.class))).thenReturn(new Property());

        Property property =  service.createChoiceProperty(name, displayName, description);

        Assert.assertNotNull(property);
        verify(propertyFactory, times(1)).createChoiceProperty(any(EntityInfo.class));
        verify(infoFactory, times(1)).createEntityInfo(anyString(),anyString(),anyString());

    }

    @Test
    public void savePropertyTest() {

        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(infoFactory.createEntityInfo(anyString(),anyString(),anyString())).thenReturn(new EntityInfo());
        when(propertyFactory.createStringProperty(any(EntityInfo.class))).thenReturn(new Property());

        Property property =  service.createStringProperty(name, displayName, description);
        service.save(property);

        Assert.assertNotNull(property);
        verify(propertyFactory, times(1)).createStringProperty(any(EntityInfo.class));
        verify(infoFactory, times(1)).createEntityInfo(anyString(),anyString(),anyString());
        verify(propertyRepo, times(1)).save(any(Property.class));
    }

    @Test
    public void saveAllPropertyTest() {
        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(infoFactory.createEntityInfo(anyString(),anyString(),anyString())).thenReturn(new EntityInfo());
        when(propertyFactory.createStringProperty(any(EntityInfo.class))).thenReturn(new Property());

        List<Property> properties = new ArrayList<>();
        properties.add(service.createStringProperty(name, displayName, description));
        properties.add(service.createStringProperty(name, displayName, description));
        properties.add(service.createStringProperty(name, displayName, description));
        properties.add(service.createStringProperty(name, displayName, description));
        properties.add(service.createStringProperty(name, displayName, description));
        service.saveAll(properties);

        verify(propertyFactory, times(5)).createStringProperty(any(EntityInfo.class));
        verify(infoFactory, times(5)).createEntityInfo(anyString(),anyString(),anyString());
        verify(propertyRepo, times(1)).saveAll(anyIterable());
    }

    @Test
    public void findByIdTest() {

        when(propertyRepo.findById(anyLong())).thenReturn(Optional.of(new Property()));

        Property property = service.findById(1L).orElse(null);

        Assert.assertNotNull(property);
        verify(propertyRepo, times(1)).findById(anyLong());

    }

    @Test
    public void findByInfoNameTest() {
        when(propertyRepo.findByInfoName(anyString())).thenReturn(Optional.of(new Property()));

        Property property = service.findByInfoName("name").orElse(null);

        Assert.assertNotNull(property);
        verify(propertyRepo, times(1)).findByInfoName(anyString());

    }

    @Test
    public void deletePropertyByIdTest() {
        service.deletePropertyById(1L);
        verify(propertyRepo, times(1)).deleteById(anyLong());
    }

    @Test
    public void deleteAllPropertiesTest() {
        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(infoFactory.createEntityInfo(anyString(),anyString(),anyString())).thenReturn(new EntityInfo());
        when(propertyFactory.createStringProperty(any(EntityInfo.class))).thenReturn(new Property());

        List<Property> properties = new ArrayList<>();
        properties.add(service.createStringProperty(name, displayName, description));
        properties.add(service.createStringProperty(name, displayName, description));
        properties.add(service.createStringProperty(name, displayName, description));
        properties.add(service.createStringProperty(name, displayName, description));
        properties.add(service.createStringProperty(name, displayName, description));

        service.deleteAllProperties(properties);

        verify(propertyRepo, times(1)).deleteAll(anyIterable());
    }
}
