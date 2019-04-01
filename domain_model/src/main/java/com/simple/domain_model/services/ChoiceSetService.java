package com.simple.domain_model.services;

import com.simple.domain_model.domain.ChoiceSet;
import com.simple.domain_model.domain.ChoiceValue;
import com.simple.domain_model.domain.DataModelFactory;
import com.simple.domain_model.repo.ChoiceSetRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChoiceSetService {

    private DataModelFactory.ChoiceSetFactory choiceSetFactory;
    private DataModelFactory.EntityInfoFactory infoFactory;
    private ChoiceSetRepo repo;
    private DataModelFactory.ChoiceValueFactory choiceValueFactory;

    public ChoiceSetService(DataModelFactory.ChoiceSetFactory choiceSetFactory, DataModelFactory.EntityInfoFactory infoFactory, ChoiceSetRepo repo, DataModelFactory.ChoiceValueFactory choiceValueFactory) {
        this.choiceSetFactory = choiceSetFactory;
        this.infoFactory = infoFactory;
        this.repo = repo;
        this.choiceValueFactory = choiceValueFactory;
    }

    public ChoiceSet createChoiceSet(String name, String displayName, String description) {
        return choiceSetFactory.createChoiceSet(infoFactory.createEntityInfo(name,displayName,description));
    }

    public ChoiceValue createChoiceValue(String value, Long valueId) {
        return choiceValueFactory.createChoiceValue(value, valueId);
    }

    public void saveChoiceSet(ChoiceSet choiceSet) {
        repo.save(choiceSet);
    }

    public Optional<ChoiceSet> findById(Long id) {
        return repo.findById(id);
    }

    public Optional<ChoiceSet> findByName(String name) {
        return repo.findByInfoName(name);
    }

    public void deleteChoiceSetById(Long id) {
        repo.deleteById(id);
    }
}
