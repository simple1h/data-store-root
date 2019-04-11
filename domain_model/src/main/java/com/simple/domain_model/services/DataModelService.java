package com.simple.domain_model.services;

import org.springframework.stereotype.Service;

@Service
public class DataModelService {

    private PropertyService propertyService;
    private AttributeService attributeService;
    private ChoiceSetService choiceSetService;
    private ObjectClassService objectClassService;

    public DataModelService(PropertyService propertyService, AttributeService attributeService, ChoiceSetService choiceSetService, ObjectClassService objectClassService) {
        this.propertyService = propertyService;
        this.attributeService = attributeService;
        this.choiceSetService = choiceSetService;
        this.objectClassService = objectClassService;
    }

    public PropertyService getPropertyService() {
        return propertyService;
    }

    public AttributeService getAttributeService() {
        return attributeService;
    }

    public ChoiceSetService getChoiceSetService() {
        return choiceSetService;
    }

    public ObjectClassService getObjectClassService() {
        return objectClassService;
    }
}
