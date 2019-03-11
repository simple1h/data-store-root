package com.simple.domain_model.repo;

import com.simple.domain_model.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepo extends JpaRepository<Property,Long>{

    Property findByInfoName (String name);
}
