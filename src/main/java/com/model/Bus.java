package com.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity
public class Bus extends Vehicle {

    private int numberOfSeats;

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
