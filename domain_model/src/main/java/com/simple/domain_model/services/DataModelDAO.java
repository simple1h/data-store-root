package com.simple.domain_model.services;

import com.simple.domain_model.domain.Attribute;

import javax.naming.OperationNotSupportedException;
import java.util.Optional;

public interface DataModelDAO<T> {

    default void save(T dataModelObject) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    default void saveAll(Iterable<T> dataModelObjects) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    default Optional<T> findById(Long id) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    default Optional<T> findByInfoName(String name) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    default void deleteById(Long id) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }

    default void deleteAll(Iterable<T> dataModelObjects) throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }
}
