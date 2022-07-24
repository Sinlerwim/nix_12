package com.service;

import com.model.Bus;
import com.model.Manufacturer;
import com.repository.BusRepository;
import com.repository.CrudRepository;

import java.math.BigDecimal;

public class BusService extends VehicleService<Bus> {

    public BusService(CrudRepository<Bus> repository) {
        super(repository);
    }

    public void create(String model, Manufacturer manufacturer, BigDecimal price, int numberOfSeats) {
        repository.save(new Bus(model, manufacturer, price, numberOfSeats, 1));
    }

    public void printAll() {
        for (Bus bus : repository.getAll()) {
            System.out.println(bus);
        }
    }

    public boolean changePriceById(String id, BigDecimal price) {
        return repository.update(id, price);
    }

    public boolean delete(String id) {
        return repository.delete(id);
    }
}
