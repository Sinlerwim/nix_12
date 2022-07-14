package com.service;

import com.model.Bus;
import com.model.Manufacturer;
import com.repository.BusRepository;

import java.math.BigDecimal;

public class BusService {

    private static final BusRepository BUS_REPOSITORY = new BusRepository();

    public void create(String model, Manufacturer manufacturer, BigDecimal price, int numberOfSeats) {
        BUS_REPOSITORY.save(new Bus(model, manufacturer, price, numberOfSeats));
    }

    public void printAll() {
        for (Bus bus : BUS_REPOSITORY.getAll()) {
            System.out.println(bus);
        }
    }

    public boolean changePriceById(String id, BigDecimal price) {
        return BUS_REPOSITORY.update(id, price);
    }

    public boolean delete(String id) {
        return BUS_REPOSITORY.delete(id);
    }
}
