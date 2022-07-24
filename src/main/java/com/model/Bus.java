package com.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Bus extends Vehicle{

    private int numberOfSeats;

    public Bus(String model, Manufacturer manufacturer, BigDecimal price, int numberOfSeats, int count) {
        super(model, manufacturer, price, count);
        this.numberOfSeats=numberOfSeats;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "numberOfSeats='" + numberOfSeats + '\'' +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }
}
