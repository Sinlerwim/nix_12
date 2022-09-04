package com.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Invoice {
    private String id;
    private Date created;
    private List<Auto> autos = new ArrayList<>();
    private List<Bus> buses = new ArrayList<>();
    private List<Truck> trucks = new ArrayList<>();

    public Invoice(List<Auto> autos, List<Bus> buses, List<Truck> trucks) {
        this.id = UUID.randomUUID().toString();
        this.autos = autos;
        this.buses = buses;
        this.trucks = trucks;
    }

    //
    public Invoice(String id, Date created, List<Auto> autos, List<Bus> buses, List<Truck> trucks) {
        this.id = id;
        this.created = created;
        this.autos = autos;
        this.buses = buses;
        this.trucks = trucks;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", created=" + created +
                ((autos.isEmpty()) ? " null" : ", autos=" + autos) +
                ((buses.isEmpty()) ? " null" : ", buses=" + buses) +
                ((trucks.isEmpty()) ? " null" : ", trucks=" + trucks) +
                '}';
    }
}