package com.simple.domain_model.services;

import com.simple.domain_model.domain.DataModelFactory;
import com.simple.domain_model.domain.ObjectClass;
import com.simple.domain_model.repo.ObjectClassRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ObjectClassService implements DataModelDAO<ObjectClass>{

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

    public DataModelFactory.PropertyValueFactory getValueFactory() {
        return valueFactory;
    }

    public ObjectClass createObjectClass(String name, String displayName, String description) {
        return objectClassFactory.createObjectClass(infoFactory.createEntityInfo(name,displayName,description));
    }

    @Override
    public void save(ObjectClass objectClass) {
        repo.save(objectClass);
    }

    @Override
    public void saveAll(Iterable<ObjectClass> objectClasses) {
        repo.saveAll(objectClasses);
    }

    @Override
    public Optional<ObjectClass> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Optional<ObjectClass> findByInfoName(String name) {
        return repo.findByInfoName(name);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void deleteAll(Iterable<ObjectClass> objectClasses) {
        repo.deleteAll(objectClasses);
    }
}
