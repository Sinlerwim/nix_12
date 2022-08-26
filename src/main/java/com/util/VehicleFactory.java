package com.util;

import com.model.Vehicle;
import com.model.VehicleType;
import com.service.AutoService;
import com.service.BusService;
import com.service.TruckService;

import java.math.BigDecimal;
import java.util.Random;

public class VehicleFactory {
    private static VehicleFactory instance;

    private static final AutoService AUTO_SERVICE = AutoService.getInstance();
    private static final BusService BUS_SERVICE = BusService.getInstance();
    private static final TruckService TRUCK_SERVICE = TruckService.getInstance();

    private static final Random RANDOM = new Random();

    private VehicleFactory() {
    }

    public static VehicleFactory getInstance() {
        if (instance == null) {
            instance = new VehicleFactory();
        }
        return instance;
    }

    public static Vehicle buildAndSave(VehicleType type) {
        return switch (type) {
            case AUTO -> AUTO_SERVICE.createAndSave();
            case BUS -> BUS_SERVICE.createAndSave();
            case TRUCK -> TRUCK_SERVICE.createAndSave();
            default -> throw new IllegalArgumentException("Cannot build " + type);
        };
    }

    public static Vehicle build(VehicleType type) {
        return switch (type) {
            case AUTO -> AUTO_SERVICE.create();
            case BUS -> BUS_SERVICE.create();
            case TRUCK -> TRUCK_SERVICE.create();
            default -> throw new IllegalArgumentException("Cannot build " + type);
        };
    }

    public static boolean changePriceById(String id, BigDecimal price) {
        return AUTO_SERVICE.changePriceById(id, price) |
                BUS_SERVICE.changePriceById(id, price) |
                TRUCK_SERVICE.changePriceById(id, price);
    }

    public static void printAll() {
        AUTO_SERVICE.printAll();
        BUS_SERVICE.printAll();
        TRUCK_SERVICE.printAll();
        System.out.println();
    }

    public static boolean deleteById(String id) {
        return AUTO_SERVICE.delete(id) |
                BUS_SERVICE.delete(id) |
                TRUCK_SERVICE.delete(id);
    }

}
