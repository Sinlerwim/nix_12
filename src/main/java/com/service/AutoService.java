package com.service;

import com.model.Auto;
import com.model.Manufacturer;
import com.repository.AutoRepository;

import java.math.BigDecimal;

public class AutoService {

    private static final AutoRepository AUTO_REPOSITORY = new AutoRepository();

    public void create(String model, Manufacturer manufacturer, BigDecimal price, String bodyType) {
        AUTO_REPOSITORY.save(new Auto(model, manufacturer, price, bodyType));
    }

    public void printAll() {
        for (Auto auto : AUTO_REPOSITORY.getAll()) {
            System.out.println(auto);
        }
    }

    public boolean changePriceById(String id, BigDecimal price) {
        return AUTO_REPOSITORY.update(id, price);
    }

    public boolean delete(String id) {
        return AUTO_REPOSITORY.delete(id);
    }
}
