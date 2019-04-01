package com.simple.domain_model.repo;

import com.simple.domain_model.domain.ChoiceSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChoiceSetRepo extends JpaRepository<ChoiceSet, Long> {

    Optional<ChoiceSet> findByInfoName (String name);
}
