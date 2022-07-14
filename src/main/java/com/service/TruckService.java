package com.service;

import com.model.Truck;
import com.model.Manufacturer;
import com.repository.TruckRepository;

import java.math.BigDecimal;

public class TruckService {

    private static final TruckRepository TRUCK_REPOSITORY = new TruckRepository();

    public void create(String model, Manufacturer manufacturer, BigDecimal price, int allowTrailerWeight) {
        TRUCK_REPOSITORY.save(new Truck(model, manufacturer, price, allowTrailerWeight));
    }

    public void printAll() {
        for (Truck truck : TRUCK_REPOSITORY.getAll()) {
            System.out.println(truck);
        }
    }

    public boolean changePriceById(String id, BigDecimal price) {
        return TRUCK_REPOSITORY.update(id, price);
    }

    public boolean delete(String id) {
        return TRUCK_REPOSITORY.delete(id);
    }
}
