package com.repository;

import com.model.Auto;
import com.model.Vehicle;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.*;

public class VehicleContainer<T extends Vehicle> {
    private final List<T> vehicles;

    public VehicleContainer() {
        vehicles = new LinkedList<T>();
    }

    public void addVehicle(T vehicle) {
        vehicles.add(vehicle);
    }

    public boolean deleteVehicleById(String id) {
        final Iterator<T> iterator = vehicles.iterator();
        while (iterator.hasNext()) {
            final T vehicle = iterator.next();
            if (vehicle.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public void printAll() {
        for (T vehicle : vehicles)
            System.out.println(vehicle);
    }

    public List<T> getAll() {
        return vehicles;
    }

    public Optional<T> findById(String id) {
        for (T vehicle : vehicles) {
            if (vehicle.getId().equals(id)) {
                return Optional.of(vehicle);
            }
        }
        return Optional.empty();
    }

    public boolean changePriceRandomly(String id) {

        BigDecimal oldPrice;
        BigDecimal newPrice;
        BigDecimal discount = new BigDecimal(new Random().nextDouble(10, 30) / 100);
        for (T vehicle : vehicles) {
            if (vehicle.getId().equals(id)) {
                oldPrice = vehicle.getPrice();
                newPrice = oldPrice.subtract(oldPrice.multiply(discount));
                vehicle.setPrice(newPrice);
                return true;
            }
        }
        return false;
    }

    public void changeAllPricesRandomly() {
        BigDecimal oldPrice;
        BigDecimal newPrice;
        BigDecimal discount;
        for (T vehicle : vehicles) {
            oldPrice = vehicle.getPrice();
            discount = new BigDecimal(new Random().nextDouble(10, 30) / 100);
            newPrice = oldPrice.subtract(oldPrice.multiply(discount));
            vehicle.setPrice(newPrice);
        }
    }

    public <N extends Number> boolean increasePrice (String id , N x) {
        for (T vehicle : vehicles) {
            if (vehicle.getId().equals(id)) {
                vehicle.setPrice(vehicle.getPrice().add(new BigDecimal(String.valueOf(x))));
                return true;
            }
        }
        return false;
    }

    public <N extends Number> void increaseAllPrices(N x) {
        for (T vehicle : vehicles) {
                vehicle.setPrice(vehicle.getPrice().add(new BigDecimal(String.valueOf(x))));
        }
    }


}
