package com.simple.domain_model.service;

import com.simple.domain_model.domain.Attribute;
import com.simple.domain_model.domain.DataModelFactory;
import com.simple.domain_model.domain.EntityInfo;
import com.simple.domain_model.repo.AttributeRepo;
import com.simple.domain_model.serices.AttributeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AttributeServiceTest {

    private AttributeService service;
    @Mock
    private DataModelFactory.AttributeFactory attributeFactory;
    @Mock
    private DataModelFactory.EntityInfoFactory infoFactory;
    @Mock
    private AttributeRepo attributeRepo;
    @Mock
    private DataModelFactory.PropertyValueFactory valueFactory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new AttributeService(attributeFactory, infoFactory, attributeRepo, valueFactory);
    }

    @Test
    public void createStringAttributeTest() {

        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(infoFactory.createEntityInfo(anyString(), anyString(), anyString())).thenReturn(new EntityInfo());
        when(attributeFactory.createStringAttribute(any(EntityInfo.class))).thenReturn(new Attribute());

        Attribute attribute = service.createStringAttribute(name, displayName, description);

        Assert.assertNotNull(attribute);
        verify(attributeFactory, times(1)).createStringAttribute(any(EntityInfo.class));
        verify(infoFactory, times(1)).createEntityInfo(anyString(), anyString(), anyString());
    }

    @Test
    public void createLongAttributeTest() {

        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(infoFactory.createEntityInfo(anyString(), anyString(), anyString())).thenReturn(new EntityInfo());
        when(attributeFactory.createLongAttribute(any(EntityInfo.class))).thenReturn(new Attribute());

        Attribute attribute = service.createLongAttribute(name, displayName, description);

        Assert.assertNotNull(attribute);
        verify(attributeFactory, times(1)).createLongAttribute(any(EntityInfo.class));
        verify(infoFactory, times(1)).createEntityInfo(anyString(), anyString(), anyString());
    }

    @Test
    public void createDateAttributeTest() {

        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(infoFactory.createEntityInfo(anyString(), anyString(), anyString())).thenReturn(new EntityInfo());
        when(attributeFactory.createDateAttribute(any(EntityInfo.class))).thenReturn(new Attribute());

        Attribute attribute = service.createDateAttribute(name, displayName, description);

        Assert.assertNotNull(attribute);
        verify(attributeFactory, times(1)).createDateAttribute(any(EntityInfo.class));
        verify(infoFactory, times(1)).createEntityInfo(anyString(), anyString(), anyString());
    }

    @Test
    public void createChoiceAttributeTest() {

        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(infoFactory.createEntityInfo(anyString(), anyString(), anyString())).thenReturn(new EntityInfo());
        when(attributeFactory.createChoiceAttribute(any(EntityInfo.class))).thenReturn(new Attribute());

        Attribute attribute = service.createChoiceAttribute(name, displayName, description);

        Assert.assertNotNull(attribute);
        verify(attributeFactory, times(1)).createChoiceAttribute(any(EntityInfo.class));
        verify(infoFactory, times(1)).createEntityInfo(anyString(), anyString(), anyString());
    }

    @Test
    public void saveAttributeTest() {
        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(infoFactory.createEntityInfo(anyString(), anyString(), anyString())).thenReturn(new EntityInfo());
        when(attributeFactory.createStringAttribute(any(EntityInfo.class))).thenReturn(new Attribute());

        Attribute attribute = service.createStringAttribute(name, displayName, description);
        service.save(attribute);

        Assert.assertNotNull(attribute);
        verify(attributeFactory, times(1)).createStringAttribute(any(EntityInfo.class));
        verify(infoFactory, times(1)).createEntityInfo(anyString(), anyString(), anyString());
        verify(attributeRepo, times(1)).save(any(Attribute.class));

    }

    @Test
    public void saveAllAttributeTest() {
        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(infoFactory.createEntityInfo(anyString(), anyString(), anyString())).thenReturn(new EntityInfo());
        when(attributeFactory.createStringAttribute(any(EntityInfo.class))).thenReturn(new Attribute());

        List<Attribute> attributes = new ArrayList<>();
        attributes.add(service.createStringAttribute(name, displayName, description));
        attributes.add(service.createStringAttribute(name, displayName, description));
        attributes.add(service.createStringAttribute(name, displayName, description));
        attributes.add(service.createStringAttribute(name, displayName, description));
        attributes.add(service.createStringAttribute(name, displayName, description));
        service.saveAll(attributes);

        verify(attributeFactory, times(5)).createStringAttribute(any(EntityInfo.class));
        verify(infoFactory, times(5)).createEntityInfo(anyString(), anyString(), anyString());
        verify(attributeRepo, times(1)).saveAll(anyIterable());

    }

    @Test
    public void findByIdTest() {

        when(attributeRepo.findById(anyLong())).thenReturn(Optional.of(new Attribute()));

        Attribute attribute = service.findById(1L).orElse(null);

        Assert.assertNotNull(attribute);
        verify(attributeRepo, times(1)).findById(anyLong());
    }

    @Test
    public void findByNameTest() {

        when(attributeRepo.findByInfoName(anyString())).thenReturn(Optional.of(new Attribute()));

        Attribute attribute = service.findByInfoName("name").orElse(null);

        Assert.assertNotNull(attribute);
        verify(attributeRepo, times(1)).findByInfoName(anyString());
    }

    @Test
    public void deleteAttributeByIdTest() {
        service.deleteAttributeById(1L);
        verify(attributeRepo, times(1)).deleteById(anyLong());
    }

    @Test
    public void deleteAllAttributeTest() {
        String name = "name";
        String displayName = "displayName";
        String description = "description";

        when(infoFactory.createEntityInfo(anyString(), anyString(), anyString())).thenReturn(new EntityInfo());
        when(attributeFactory.createStringAttribute(any(EntityInfo.class))).thenReturn(new Attribute());

        List<Attribute> attributes = new ArrayList<>();
        attributes.add(service.createStringAttribute(name, displayName, description));
        attributes.add(service.createStringAttribute(name, displayName, description));
        attributes.add(service.createStringAttribute(name, displayName, description));
        attributes.add(service.createStringAttribute(name, displayName, description));
        attributes.add(service.createStringAttribute(name, displayName, description));

        service.deleteAllAttributes(attributes);

        verify(attributeFactory, times(5)).createStringAttribute(any(EntityInfo.class));
        verify(infoFactory, times(5)).createEntityInfo(anyString(), anyString(), anyString());
        verify(attributeRepo, times(1)).deleteAll(anyIterable());
    }
}
