package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

import com.model.Manufacturer;

public class Creating {

    public static void start(BufferedReader reader, AutoService AUTO_SERVICE, BusService BUS_SERVICE, TruckService TRUCK_SERVICE) throws IOException {
        do {
            System.out.println("Choose what you want to create:");
            System.out.println("1.Auto");
            System.out.println("2.Bus");
            System.out.println("3.Truck");
            System.out.println("Or input 0 to exit");
        }
        while (navigation(reader, AUTO_SERVICE, BUS_SERVICE, TRUCK_SERVICE));
    }

    private static Manufacturer getManufacturer(BufferedReader reader) throws IOException {
        Manufacturer manufacturer;
        do {
            System.out.println("Print the manufacturer (Try \"KIA\", \"BMW\", \"ZAZ\" or \"OPEL\"):");
            manufacturer = switch (reader.readLine()) {
                case "KIA" -> Manufacturer.KIA;
                case "BMW" -> Manufacturer.BMW;
                case "ZAZ" -> Manufacturer.ZAZ;
                case "OPEL" -> Manufacturer.OPEL;
                default -> null;
            };
        } while (manufacturer == null);
        return manufacturer;
    }

    private static String getModel(BufferedReader reader) throws IOException {
        System.out.println("Print the model:");
        return reader.readLine();
    }

    private static BigDecimal getPrice(BufferedReader reader) throws IOException {
        System.out.println("Print the price:");
        return new BigDecimal(reader.readLine());
    }


    private static void creatingAuto(BufferedReader reader, AutoService AUTO_SERVICE) throws IOException {
        String bodyType;
        System.out.println("Ok. You want to create an auto.");
        System.out.println("Print the body type:");
        bodyType = reader.readLine();
        AUTO_SERVICE.create(getModel(reader), getManufacturer(reader), getPrice(reader), bodyType);
    }

    private static void creatingBus(BufferedReader reader, BusService BUS_SERVICE) throws IOException {
        int numberOfSeats;
        System.out.println("Ok. You want to create a bus.");
        System.out.println("Print the number of seats:");
        numberOfSeats = Integer.parseInt(reader.readLine());
        BUS_SERVICE.create(getModel(reader), getManufacturer(reader), getPrice(reader), numberOfSeats);
    }

    private static void creatingTruck(BufferedReader reader, TruckService TRUCK_SERVICE) throws IOException {
        int  allowTrailerWeight;
        System.out.println("Ok. You want to create a Truck.");
        System.out.println("Print the allow weight of trailer:");
        allowTrailerWeight = Integer.parseInt(reader.readLine());
        TRUCK_SERVICE.create(getModel(reader), getManufacturer(reader), getPrice(reader),  allowTrailerWeight);
    }

    private static boolean navigation(BufferedReader reader, AutoService AUTO_SERVICE, BusService BUS_SERVICE, TruckService TRUCK_SERVICE) throws IOException {
        String input = reader.readLine();
        switch (input) {
            case "1":
                creatingAuto(reader, AUTO_SERVICE);
                break;
            case "2":
                creatingBus(reader, BUS_SERVICE);
                break;
            case "3":
                creatingTruck(reader, TRUCK_SERVICE);
                break;
            case "0":
                return false;
            default:
                System.out.println("Unexpected value: \"" + input + "\". Try 1 (auto), 2 (bus), 3(truck) or 0 to exit");
        }
        return true;
    }


}
