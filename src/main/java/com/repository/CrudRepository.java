package com.repository;

import com.model.Auto;

import java.math.BigDecimal;
import java.util.List;

public interface CrudRepository<T> {
    T getById(String id);

    List<T> getAll();

    boolean create(T vehicle);

    boolean create(List<T> auto);

    boolean update(String id, BigDecimal price);

    boolean delete(String id);
}
