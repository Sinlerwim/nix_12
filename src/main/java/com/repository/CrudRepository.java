package com.repository;

import com.model.Auto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(String id);

    List<T> getAll();

    boolean save(T vehicle) throws IllegalArgumentException;

    boolean update(String id, BigDecimal price);

    boolean delete(String id);

    Optional<T> findByPrice(BigDecimal price);
}
