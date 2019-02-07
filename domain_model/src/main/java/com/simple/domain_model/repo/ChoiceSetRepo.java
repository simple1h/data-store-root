package com.simple.domain_model.repo;

import com.simple.domain_model.domain.ChoiceSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceSetRepo extends JpaRepository<ChoiceSet, Long> {

    ChoiceSet findByInfoName (String name);
}
