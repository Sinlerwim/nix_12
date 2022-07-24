package com.repository;

import com.model.Auto;
import com.model.Truck;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TruckRepository implements CrudRepository<Truck>{

    private static final Logger LOGGER = LoggerFactory.getLogger(TruckRepository.class);
    private final List<Truck> trucks;

    public TruckRepository() {
        trucks = new LinkedList<>();
    }

    @Override
    public Optional<Truck> findById(String id) {
        for (Truck truck : trucks){
            if (truck.getId().equals(id)) {
                return Optional.of(truck);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Truck> getAll() {
        return trucks;
    }

    @Override
    public boolean save(Truck truck) {
        if (truck == null) {
            throw new IllegalArgumentException("Truck must not be null");
        }
        trucks.add(truck);
        LOGGER.debug("Created truck {}", truck.getId());
        return true;
    }

    @Override
    public boolean update(String id, BigDecimal price) {
        for (Truck truck : trucks) {
            if (truck.getId().equals(id)) {
                truck.setPrice(price);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Truck> iterator = trucks.iterator();
        while (iterator.hasNext()) {
            final Truck truck = iterator.next();
            if (truck.getId().equals(id)) {
                iterator.remove();
                LOGGER.debug("Deleted truck {}", truck.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Truck> findByPrice(BigDecimal price) {
        for (Truck truck : trucks) {
            if (price.compareTo(truck.getPrice()) == 0) {
                return Optional.of(truck);
            }
        }
        return Optional.empty();
    }
}
