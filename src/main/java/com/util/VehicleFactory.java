package com.util;

import com.model.*;
import com.service.AutoService;
import com.service.BusService;
import com.service.EngineService;
import com.service.TruckService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class VehicleFactory {
    private static VehicleFactory instance;

    private static final AutoService AUTO_SERVICE = AutoService.getInstance();
    private static final BusService BUS_SERVICE = BusService.getInstance();
    private static final TruckService TRUCK_SERVICE = TruckService.getInstance();
    private static final EngineService ENGINE_SERVICE = EngineService.getInstance();

    private static final Random RANDOM = new Random();

    private VehicleFactory() {
    }

    public static VehicleFactory getInstance() {
        if (instance == null) {
            instance = new VehicleFactory();
        }
        return instance;
    }

    public static List<Vehicle> getAll() {
        return new ArrayList<>(AUTO_SERVICE.getAll());
    }

//    public static List<Bus> getAllBuses() {
//        return BUS_SERVICE.getAll();
//    }
//
//    public static List<Truck> getAllTrucks() {
//        return TRUCK_SERVICE.getAll();
//    }

    public static Optional<List<Auto>> findAutoByInvoice(String invoiceId) {
        return AUTO_SERVICE.findByInvoice(invoiceId);
    }

//    public static Optional<List<Bus>> findBusByInvoice(String invoiceId) {
//        return BUS_SERVICE.findByInvoice(invoiceId);
//    }
//
//    public static Optional<List<Truck>> findTruckByInvoice(String invoiceId) {
//        return TRUCK_SERVICE.findByInvoice(invoiceId);
//    }

    public static void saveRandomVehicles(int count) {
        for(int i = 0; i < count; i++) {
            switch (RANDOM.nextInt(1, 3)) {
                case 1 -> {
                    Auto auto = AUTO_SERVICE.getRandomAuto();
                    auto.setEngine(ENGINE_SERVICE.getRandomEngineFromDB());
                    AUTO_SERVICE.save(auto);
                }
                case 2 -> {
                    Bus bus = BUS_SERVICE.getRandomBus();
                    bus.setEngine(ENGINE_SERVICE.getRandomEngineFromDB());
                    BUS_SERVICE.save(bus);
                }
                case 3 -> {
                    Truck truck = TRUCK_SERVICE.getRandomTruck();
                    truck.setEngine(ENGINE_SERVICE.getRandomEngineFromDB());
                    TRUCK_SERVICE.save(truck);
                }
            }
        }
    }

    public static Vehicle buildRandomVehicle(VehicleType type) {
        switch (type) {
            case AUTO -> {
                Auto auto = AUTO_SERVICE.getRandomAuto();
                auto.setEngine(ENGINE_SERVICE.getRandomEngineFromDB());
                return auto;
            }
//            case BUS -> BUS_SERVICE.create();
//            case TRUCK -> TRUCK_SERVICE.create();
            case BUS -> {
            }
            case TRUCK -> {
            }
            default -> throw new IllegalArgumentException("Cannot build " + type);
        }
        return null;
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
