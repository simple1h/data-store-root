package com.simple.domain_model.repo;

import com.simple.domain_model.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyRepo extends JpaRepository<Property,Long>{

    Optional<Property> findByInfoName (String name);
}
