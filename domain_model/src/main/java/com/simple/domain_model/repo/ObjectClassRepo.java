package com.simple.domain_model.repo;

import com.simple.domain_model.domain.ObjectClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ObjectClassRepo extends JpaRepository<ObjectClass, Long> {

    Optional<ObjectClass> findByInfoName(String name);

}
