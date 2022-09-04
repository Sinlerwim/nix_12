package com.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity
public class Engine {

    //    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
//    @Id
//    private String id;
    private int volume;
    private String brand;

//    public Engine() {
//        this.id = UUID.randomUUID().toString();
//    }


    @Override
    public String toString() {
        return "Engine{" +
                "volume=" + volume +
                ", brand='" + brand + '\'' +
                '}';
    }
}
