package com.repository;

import com.model.Engine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    Optional<T> findById(String id);

    List<T> getAll();

    void save(T vehicle) throws IllegalArgumentException;

    boolean update(String id, BigDecimal price);

    boolean delete(String id);

    Optional<T> findByPrice(BigDecimal price);

    Engine getRandomEngine();

    void clear();

    Optional<List<T>> findByInvoice(String id);
}
