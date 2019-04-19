package com.simple.domain_model.services;

import com.simple.domain_model.domain.Attribute;
import com.simple.domain_model.domain.ObjectClass;
import com.simple.domain_model.domain.Property;
import com.simple.domain_model.domain.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/*Сервис сделан, чтобы упростить написание тестов*/
@Service
public class ObjectServiceTestHelper {

    private ObjectClassService service;
    private PropertyService prpService;
    private AttributeService atrService;

    public static final String DATE_NAME = "dateName";
    public static final String STR_NAME = "strName";
    public static final String LONG_NAME = "lName";

    public static final String STR_ATTR_NAME = "strAttrName";
    public static final String LONG_ATTR_NAME = "longAttrName";
    public static final String DATE_ATTR_NAME = "dateAttrName";


    public ObjectServiceTestHelper(ObjectClassService service, PropertyService prpService, AttributeService atrService) {
        this.service = service;
        this.prpService = prpService;
        this.atrService = atrService;
    }

    @Transactional
    public void initObjectClass() {
        DebugUtils.transactionRequired("ObjectServiceTestHelper.initObjectClass");
        ObjectClass objectClass = createObjectClass("mainClass", "mainDisplayName", "mainDescription");
        getPropertyValues().forEach(value -> objectClass.properties().addProperty(value));
        getAttributes().forEach(objectClass::addAttribute);
        getChildObjectClasses().forEach(objectClass::addChild);
        service.save(objectClass);
    }

    public List<ObjectClass> getChildObjectClasses() {
        return Arrays.asList(
                createObjectClass("childClass1", "childDisplayName1", "childDescription1"),
                createObjectClass("childClass2", "childDisplayName2", "childDescription2"),
                createObjectClass("childClass3", "childDisplayName3", "childDescription3"),
                createObjectClass("childClass4", "childDisplayName4", "childDescription4")
        );
    }

    public List<Attribute> getAttributes() {
        return Arrays.asList(
                atrService.findByInfoName(STR_ATTR_NAME).get(),
                atrService.findByInfoName(LONG_ATTR_NAME).get(),
                atrService.findByInfoName(DATE_ATTR_NAME).get());
    }
    public List<PropertyValue> getPropertyValues() {
        List<PropertyValue> result = new ArrayList<>();
        Property sProperty = prpService.findByInfoName(STR_NAME).get();
        Property lProperty = prpService.findByInfoName(LONG_NAME).get();
        Property dProperty = prpService.findByInfoName(DATE_NAME).get();
        result.add(service.getValueFactory().createStringValue(sProperty,"value"));
        result.add(service.getValueFactory().createLongValue(lProperty,1L));
        result.add(service.getValueFactory().createDateValue(dProperty,new Date()));
        return result;
    }

    @Transactional
    public void initEnvironmentEntities() {
        DebugUtils.transactionRequired("Transaction in objectServiceTestHelper.initEnvironmentEntities");
        createProperties();
        createAttributes();
    }

    public void createAttributes() {
        atrService.saveAll(
                Arrays.asList(
                        atrService.createStringAttribute(STR_ATTR_NAME,"strDisplayName", "strDescription"),
                        atrService.createLongAttribute(LONG_ATTR_NAME,"longDisplayName", "longDescription"),
                        atrService.createDateAttribute(DATE_ATTR_NAME,"dateDisplayName", "dateDescription")
                ));
    }

    public void createProperties() {
        prpService.saveAll(
                Arrays.asList(
                        prpService.createStringProperty(STR_NAME, "strDisplayName", "strDescription"),
                        prpService.createLongProperty(LONG_NAME, "lDisplayName", "lDescription"),
                        prpService.createDateProperty(DATE_NAME, "dDisplayName", "dDescription")
                ));
    }

    public ObjectClass createObjectClass(String name, String displayName, String description) {
        return service.createObjectClass(name, displayName, description);
    }
}
