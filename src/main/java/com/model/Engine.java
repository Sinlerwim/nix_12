package com.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Engine {

    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @Id
    private String id;
    private int volume;
    private String brand;


    @Override
    public String toString() {
        return "Engine{" +
                "volume=" + volume +
                ", brand='" + brand + '\'' +
                '}';
    }
}
