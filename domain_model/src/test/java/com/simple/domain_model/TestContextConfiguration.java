package com.simple.domain_model;

import com.simple.domain_model.services.AttributeService;
import com.simple.domain_model.services.ObjectClassService;
import com.simple.domain_model.services.ObjectServiceTestHelper;
import com.simple.domain_model.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/*Конифигурация сделана, чтобы обеспечить загрузку в контекс сервиса ObjectServiceTestHelper*/
@Configuration
@ComponentScan
public class TestContextConfiguration {

    @Autowired
    private ObjectClassService service;
    @Autowired
    private PropertyService prpService;
    @Autowired
    private AttributeService atrService;

    @Bean
    public ObjectServiceTestHelper objectServiceTestHelper(){
        return new ObjectServiceTestHelper(service, prpService, atrService);
    }
}
