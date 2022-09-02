package com.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Truck extends Vehicle {

    private int allowTrailerWeight;

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
