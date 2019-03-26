package com.simple.domain_model.repo;

import com.simple.domain_model.domain.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttributeRepo extends JpaRepository<Attribute, Long> {
    Optional<Attribute> findByInfoName (String anyString);
}
