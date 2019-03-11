package com.simple.domain_model.repo;

import com.simple.domain_model.domain.ObjectInstance;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ObjectInstanceRepo extends JpaRepository<ObjectInstance, Long> {
    @EntityGraph(value = "instance_attribute_values")
    Optional<ObjectInstance> findObjectInstanceByObjectInstanceId(Long objectInstanceId);
}
