package com.service;

import com.model.Truck;
import com.model.Manufacturer;
import com.repository.CrudRepository;

import java.math.BigDecimal;

public class TruckService extends VehicleService<Truck> {


    public TruckService(CrudRepository<Truck> repository) {
        super(repository);
    }

    public void create(String model, Manufacturer manufacturer, BigDecimal price, int allowTrailerWeight) {
        repository.save(new Truck(model, manufacturer, price, allowTrailerWeight, 1));
    }

    public void printAll() {
        for (Truck truck : repository.getAll()) {
            System.out.println(truck);
        }
    }

    public boolean changePriceById(String id, BigDecimal price) {
        return repository.update(id, price);
    }

    public boolean delete(String id) {
        return repository.delete(id);
    }
}
