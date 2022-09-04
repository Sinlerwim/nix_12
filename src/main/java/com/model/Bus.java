package com.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Bus extends Vehicle {

    private int numberOfSeats;

    public Bus(String model, Manufacturer manufacturer, BigDecimal price, int numberOfSeats, int count) {
        super(model, manufacturer, price, count);
        this.numberOfSeats = numberOfSeats;
    }

    public Bus(String id, String model, Manufacturer manufacturer, BigDecimal price, int numberOfSeats, int count, Date date, Engine engine) {
        super(id, model, manufacturer, price, count, date, engine);
        this.numberOfSeats = numberOfSeats;
        this.engine = engine;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "numberOfSeats=" + numberOfSeats +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                ", count=" + count +
                ", created=" + date +
                ", engine=" + engine +
                '}';
    }
}
