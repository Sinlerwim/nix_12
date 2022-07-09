package com.repository;

import com.model.Auto;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoRepository implements CrudRepository<Auto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoRepository.class);
    private final List<Auto> autos;

    public AutoRepository() {
        autos = new LinkedList<>();
    }

    @Override
    public Auto getById(String id) {
        for (Auto auto : autos) {
            if (auto.getId().equals(id)) {
                return auto;
            }
        }
        return null;
    }

    @Override
    public List<Auto> getAll() {
        return autos;
    }

    @Override
    public boolean create(Auto auto) {
        autos.add(auto);
        return true;
    }

    @Override
    public boolean create(List<Auto> auto) {
        return autos.addAll(auto);
    }

    @Override
    public boolean update(String id, BigDecimal price) {
        final Iterator<Auto> iterator = autos.iterator();
        while (iterator.hasNext()) {
            final Auto auto = iterator.next();
            if (auto.getId().equals(id)) {
                auto.setPrice(price);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Auto> iterator = autos.iterator();
        while (iterator.hasNext()) {
            final Auto auto = iterator.next();
            if (auto.getId().equals(id)) {
                iterator.remove();
                LOGGER.debug("Deleted auto {}", auto.getId());
                return true;
            }
        }
        return false;
    }

    private static void copy(final Auto from, final Auto to) {
        to.setManufacturer(from.getManufacturer());
        to.setModel(from.getModel());
        to.setBodyType(from.getBodyType());
        to.setPrice(from.getPrice());
    }
}
