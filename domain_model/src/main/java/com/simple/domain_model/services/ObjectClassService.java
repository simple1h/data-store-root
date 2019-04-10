package com.simple.domain_model.services;

import com.simple.domain_model.domain.DataModelFactory;
import com.simple.domain_model.domain.ObjectClass;
import com.simple.domain_model.repo.ObjectClassRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObjectClassService {

    private DataModelFactory.ObjectClassFactory objectClassFactory;
    private DataModelFactory.EntityInfoFactory infoFactory;
    private ObjectClassRepo repo;
    private DataModelFactory.PropertyValueFactory valueFactory;

    public ObjectClassService(DataModelFactory.ObjectClassFactory objectClassFactory, DataModelFactory.EntityInfoFactory infoFactory, ObjectClassRepo repo, DataModelFactory.PropertyValueFactory valueFactory) {
        this.objectClassFactory = objectClassFactory;
        this.infoFactory = infoFactory;
        this.repo = repo;
        this.valueFactory = valueFactory;
    }

    public ObjectClass createObjectClass(String name, String displayName, String description) {
        return objectClassFactory.createObjectClass(infoFactory.createEntityInfo(name,displayName,description));
    }

    public void save(ObjectClass objectClass) {
        repo.save(objectClass);
    }

    public void saveAll(Iterable<ObjectClass> objectClasses) {
        repo.saveAll(objectClasses);
    }

    public Optional<ObjectClass> findById(Long id) {
        return repo.findById(id);
    }

    public Optional<ObjectClass> findByInfoName(String name) {
        return repo.findByInfoName(name);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public void deleteAll(Iterable<ObjectClass> objectClasses) {
        repo.deleteAll(objectClasses);
    }

    public DataModelFactory.PropertyValueFactory getValueFactory() {
        return valueFactory;
    }
}
