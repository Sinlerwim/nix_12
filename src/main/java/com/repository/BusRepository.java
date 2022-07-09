package com.repository;

import com.model.Bus;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusRepository implements CrudRepository<Bus> {

    private final List<Bus> buses;
    private static final Logger LOGGER = LoggerFactory.getLogger(BusRepository.class);

    public BusRepository() {
        buses = new LinkedList<>();
    }

    @Override
    public Bus getById(String id) {
        for (Bus bus : buses) {
            if (bus.getId().equals(id)) {
                return bus;
            }
        }
        return null;
    }

    @Override
    public List<Bus> getAll() {
        return buses;
    }

    @Override
    public boolean create(Bus bus) {
        buses.add(bus);
        return true;
    }

    @Override
    public boolean create(List<Bus> bus) {
        return buses.addAll(bus);
    }

    @Override
    public boolean update(String id, BigDecimal price) {
        final Iterator<Bus> iterator = buses.iterator();
        while (iterator.hasNext()) {
            final Bus bus = iterator.next();
            if (bus.getId().equals(id)) {
                bus.setPrice(price);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Bus> iterator = buses.iterator();
        while (iterator.hasNext()) {
            final Bus bus = iterator.next();
            if (bus.getId().equals(id)) {
                iterator.remove();
                LOGGER.debug("Deleted bus {}", bus.getId());
                return true;
            }
        }
        return false;
    }
}
