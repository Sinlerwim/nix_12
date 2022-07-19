package com.repository;

import com.model.Auto;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.Option;

public class AutoRepository implements CrudRepository<Auto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoRepository.class);
    private final List<Auto> autos;

    public AutoRepository() {
        autos = new LinkedList<>();
    }

    @Override
    public Optional<Auto> findById(String id) {
        for (Auto auto : autos) {
            if (auto.getId().equals(id)) {
                return Optional.of(auto);
            }
        }
        return Optional.empty();
    }

    public Optional<Auto> findByPrice(BigDecimal price) {
        for (Auto auto : autos) {
            if (price.compareTo(auto.getPrice()) == 0) {
                return Optional.of(auto);
            }
        }
        return Optional.empty();
    }
    
//    public List<Auto> getByPrice(BigDecimal price) {
//        List<Auto> foundAutos = new ArrayList<>();
//        for (Auto auto : autos) {
//            if (auto.getPrice().equals(price)) {
//                foundAutos.add(auto);
//
//            }
//        }
//        return foundAutos;
//    }

    @Override
    public List<Auto> getAll() {
        return autos;
    }

    @Override
    public boolean save(Auto auto) throws IllegalArgumentException {
        if (auto == null) {
            throw new IllegalArgumentException("Auto must not be null");
        }
        autos.add(auto);
        LOGGER.debug("Created auto {}", auto.getId());
        return true;
    }

    @Override
    public boolean update(String id, BigDecimal price) {
        for (Auto auto : autos) {
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


}
