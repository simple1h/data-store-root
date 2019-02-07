package com.simple.domain_model.repo;

import com.simple.domain_model.domain.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestEntityRepo extends CrudRepository<TestEntity, Long>{
}
