package com.service;

import com.model.Manufacturer;
import com.model.Vehicle;
import com.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;


public abstract class VehicleService<T extends Vehicle> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);
    protected CrudRepository<T> repository;
    protected static final Random RANDOM = new Random();

    public VehicleService(CrudRepository<T> repository) {
        this.repository = repository;
    }

    abstract void printAll();

    protected Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public boolean changePriceById(String id, BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) >= 0) {
            return repository.update(id, price);
        }
        else {
            return false;
        }
    }

    abstract protected boolean delete(String id);
}
