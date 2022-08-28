package com.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class Vehicle {
    protected final String id;
    protected String model;
    protected BigDecimal price;
    protected Manufacturer manufacturer;
    protected int count;
    protected Date date;
    protected List<String> details;
    protected Engine engine;

    protected Vehicle(String model, Manufacturer manufacturer, BigDecimal price, int count) {
        this.id = UUID.randomUUID().toString();
        this.model = model;
        this.manufacturer = manufacturer;
        this.price = price;
        this.count = count;
        this.details = null;
    }

    protected Vehicle(String id, String model, Manufacturer manufacturer, BigDecimal price,
                      int count, Date date, Engine engine) {
        this.id = id;
        this.date = date;
        this.model = model;
        this.manufacturer = manufacturer;
        this.price = price;
        this.count = count;
        this.engine = engine;
    }
}
