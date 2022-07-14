package com.repository;

import java.math.BigDecimal;
import java.util.List;

public interface CrudRepository<T> {
    T getById(String id);

    List<T> getAll();

    boolean save(T vehicle);

    boolean update(String id, BigDecimal price);

    boolean delete(String id);
}
