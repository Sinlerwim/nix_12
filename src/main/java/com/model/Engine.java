package com.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Engine {
    private int volume;
    private String brand;
    private String id;

    public Engine(String id, int volume, String brand) {
        this.id = id;
        this.volume = volume;
        this.brand = brand;
    }

    public Engine(int volume, String brand) {
        this.volume = volume;
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "volume=" + volume +
                ", brand='" + brand + '\'' +
                '}';
    }
}
