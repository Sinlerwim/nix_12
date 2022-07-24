package com.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    T getById(String id);

    List<T> getAll();

    boolean save(T vehicle) throws IllegalArgumentException;

    boolean update(String id, BigDecimal price);

    boolean delete(String id);
}
