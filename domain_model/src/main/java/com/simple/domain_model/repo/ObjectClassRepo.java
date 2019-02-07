package com.simple.domain_model.repo;

import com.simple.domain_model.domain.ObjectClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjectClassRepo extends JpaRepository<ObjectClass, Long> {
}
