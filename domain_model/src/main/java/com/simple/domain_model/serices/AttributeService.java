package com.simple.domain_model.serices;

import com.simple.domain_model.domain.Attribute;
import com.simple.domain_model.domain.DataModelFactory;
import com.simple.domain_model.repo.AttributeRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttributeService {

    private DataModelFactory.AttributeFactory attributeFactory;
    private DataModelFactory.EntityInfoFactory infoFactory;
    private DataModelFactory.PropertyValueFactory valueFactory;
    private AttributeRepo repo;

    public AttributeService(DataModelFactory.AttributeFactory attributeFactory, DataModelFactory.EntityInfoFactory infoFactory, AttributeRepo repo, DataModelFactory.PropertyValueFactory valueFactory) {
        this.attributeFactory = attributeFactory;
        this.infoFactory = infoFactory;
        this.repo = repo;
        this.valueFactory = valueFactory;
    }

    public DataModelFactory.PropertyValueFactory getValueFactory() {
        return valueFactory;
    }

    public Attribute createStringAttribute(String name, String displayName, String description) {
        return attributeFactory.createStringAttribute(infoFactory.createEntityInfo(name,displayName,description));
    }

    public Attribute createLongAttribute(String name, String displayName, String description) {
        return attributeFactory.createLongAttribute(infoFactory.createEntityInfo(name,displayName,description));
    }

    public Attribute createDateAttribute(String name, String displayName, String description) {
        return attributeFactory.createDateAttribute(infoFactory.createEntityInfo(name,displayName,description));
    }

    public Attribute createChoiceAttribute(String name, String displayName, String description) {
        return attributeFactory.createChoiceAttribute(infoFactory.createEntityInfo(name,displayName,description));
    }

    public void save(Attribute attribute) {
        repo.save(attribute);
    }

    public void saveAll(Iterable<Attribute> attributes) {
        repo.saveAll(attributes);
    }

    public Optional<Attribute> findById(Long id) {
        return repo.findById(id);
    }

    public Optional<Attribute> findByInfoName(String name) {
        return repo.findByInfoName(name);
    }

    public void deleteAttributeById(Long id) {
        repo.deleteById(id);
    }

    public void deleteAllAttributes(Iterable<Attribute> attributes) {
        repo.deleteAll(attributes);
    }
}
