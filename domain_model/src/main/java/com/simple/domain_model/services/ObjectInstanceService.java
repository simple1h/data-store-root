package com.simple.domain_model.services;

import com.simple.domain_model.domain.*;
import com.simple.domain_model.repo.ObjectInstanceRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObjectInstanceService {
    private DataModelFactory.ObjectInstanceFactory objectInstanceFactory;
    private ObjectInstanceRepo repo;
    private DataModelFactory.AttributeValueFactory attrValFactory;

    public ObjectInstanceService(DataModelFactory.ObjectInstanceFactory objectInstanceFactory, ObjectInstanceRepo repo, DataModelFactory.AttributeValueFactory attrValFactory) {
        this.objectInstanceFactory = objectInstanceFactory;
        this.repo = repo;
        this.attrValFactory = attrValFactory;
    }

    public ObjectInstance createObject(ObjectClass objectClass) {
        return objectInstanceFactory.createObjectInstance(objectClass);
    }

    public void save(ObjectInstance instance) {
        repo.save(instance);
    }

    public Optional<ObjectInstance> findById(Long id) {
        return repo.findObjectInstanceById(id);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public AttributeValue createAttrVal(Attribute attribute, Object value) {
        return attrValFactory.createAttrVal(attribute, value);
    }
}
