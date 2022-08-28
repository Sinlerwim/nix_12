package com.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Truck extends Vehicle {

    private int allowTrailerWeight;

    public Truck(String model, Manufacturer manufacturer, BigDecimal price, int allowTrailerWeight, int count) {
        super(model, manufacturer, price, count);
        this.allowTrailerWeight = allowTrailerWeight;
    }

    public Truck(String id, String model, Manufacturer manufacturer, BigDecimal price, int allowTrailerWeight, int count,
                 Date date, Engine engine) {
        super(id, model, manufacturer, price, count, date, engine);
        this.allowTrailerWeight = allowTrailerWeight;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "allowTrailerWeight=" + allowTrailerWeight +
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
