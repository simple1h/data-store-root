package com.simple.domain_model.services;

import com.simple.domain_model.domain.DataModelFactory;
import com.simple.domain_model.domain.Property;
import com.simple.domain_model.repo.PropertyRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyService {


    private DataModelFactory.PropertyFactory propFactory;

    private DataModelFactory.EntityInfoFactory infoFactory;

    private PropertyRepo repo;

    public PropertyService(DataModelFactory.PropertyFactory propFactory, DataModelFactory.EntityInfoFactory infoFactory, PropertyRepo repo) {
        this.propFactory = propFactory;
        this.infoFactory = infoFactory;
        this.repo = repo;
    }

    public Property createStringProperty(String name, String displayName, String description) {
        return propFactory.createStringProperty(infoFactory.createEntityInfo(name,displayName,description));
    }

    public Property createLongProperty(String name, String displayName, String description) {
        return propFactory.createLongProperty(infoFactory.createEntityInfo(name,displayName,description));
    }

    public Property createDateProperty(String name, String displayName, String description) {
        return propFactory.createDateProperty(infoFactory.createEntityInfo(name,displayName,description));
    }

    public Property createChoiceProperty(String name, String displayName, String description) {
        return propFactory.createChoiceProperty(infoFactory.createEntityInfo(name,displayName,description));
    }

    public void save(Property property) {
        repo.save(property);
    }

    public void saveAll(Iterable<Property> properties) {
        repo.saveAll(properties);
    }

    public Optional<Property> findById(Long id) {
        return repo.findById(id);
    }

    public Optional<Property> findByInfoName(String name) {
        return repo.findByInfoName(name);
    }

    public void deletePropertyById(Long id) {
        repo.deleteById(id);
    }

    public void deleteAllProperties(Iterable<Property> properties) {
        repo.deleteAll(properties);
    }
}
