package com;

import com.service.AutoService;
import com.service.BusService;
import com.service.TruckService;
import com.model.Manufacturer;
import java.io.BufferedReader;
import java.io.InputStreamReader;


import java.math.BigDecimal;
import java.util.*;

public class Main {
    private static final AutoService AUTO_SERVICE = new AutoService();
    private static final BusService BUS_SERVICE = new BusService();
    private static final TruckService TRUCK_SERVICE = new TruckService();

    private static void printAllServices() {
        AUTO_SERVICE.printAll();
        BUS_SERVICE.printAll();
        TRUCK_SERVICE.printAll();
    }

    public static void main(String[] args) {
        System.out.println("\n\n\nWelcome to homework #10!\n\nHere will be the list of your vehicles.\n");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String navigation = "";
            String line;
            String price;
            String manufacturer;
            String model;
            String bodyType;
            String numberOfSeats;
            String allowTrailerWeight;
            Map<String, Manufacturer> manufactures = new HashMap<String, Manufacturer>();
            manufactures.put("KIA",Manufacturer.KIA);
            manufactures.put("BMW",Manufacturer.BMW);
            manufactures.put("ZAZ",Manufacturer.ZAZ);
            manufactures.put("OPEL", Manufacturer.OPEL);
            while (!navigation.equals("0")) {
                printAllServices();
                System.out.println("Choose what you want to do:");
                System.out.println("1. To CREATE the new vehicle");
                System.out.println("2. To UPDATE the price by id");
                System.out.println("3. To DELETE one by id ");
                System.out.println("Choose the action you need (1-3) or 0 to exit:");
                navigation = reader.readLine();
                switch (navigation) {
                    case "1":
                        System.out.println("Ok. Choose what you want to create:");
                        System.out.println("1.Auto");
                        System.out.println("2.Bus");
                        System.out.println("3.Truck");
                        line = reader.readLine();
                        switch (line) {
                            case "1":
                                System.out.println("Ok. You want to create an auto.");
                                System.out.println("Print the model:");
                                model = reader.readLine();
                                System.out.println("Print the manufacturer (KIA, BMW, ZAZ, OPEL):");
                                manufacturer = reader.readLine();
                                if (manufactures.get(manufacturer) == null) {
                                    throw new Exception("Invalid manufacturer's name");
                                }
                                System.out.println("Print the price:");
                                price = reader.readLine();
                                System.out.println("Print the body type:");
                                bodyType  = reader.readLine();
                                AUTO_SERVICE.create(model, manufactures.get(manufacturer), new BigDecimal(price), bodyType);
                                break;
                            case "2":
                                System.out.println("Ok. You want to create a bus.");
                                System.out.println("Print the model:");
                                model = reader.readLine();
                                System.out.println("Print the manufacturer (KIA, BMW, ZAZ, OPEL):");
                                manufacturer = reader.readLine();
                                if (manufactures.get(manufacturer) == null) {
                                    throw new Exception("Invalid manufacturer's name");
                                }
                                System.out.println("Print the price:");
                                price = reader.readLine();
                                System.out.println("Print the number of seats");
                                numberOfSeats  = reader.readLine();
                                BUS_SERVICE.create(model, manufactures.get(manufacturer), new BigDecimal(price), Integer.parseInt(numberOfSeats));
                                break;
                            case "3":
                                System.out.println("Ok. You want to create a truck.");
                                System.out.println("Print the model:");
                                model = reader.readLine();
                                System.out.println("Print the manufacturer (KIA, BMW, ZAZ, OPEL):");
                                manufacturer = reader.readLine();
                                if (manufactures.get(manufacturer) == null) {
                                    throw new Exception("Invalid manufacturer's name");
                                }
                                System.out.println("Print the price:");
                                price = reader.readLine();
                                System.out.println("Print the allowed trailer weight");
                                allowTrailerWeight  = reader.readLine();
                                TRUCK_SERVICE.create(model, manufactures.get(manufacturer), new BigDecimal(price), Integer.parseInt(allowTrailerWeight));
                                break;
                        }
                        break;
                    case "2":
                        System.out.println("Ok. Print ID of vehicle you need to update:");
                        line = reader.readLine();
                        System.out.println("Ok. Print the new price:");
                        price = reader.readLine();
                        if(
                        AUTO_SERVICE.changePriceById(line, new BigDecimal(price)) ||
                        BUS_SERVICE.changePriceById(line, new BigDecimal(price)) ||
                        TRUCK_SERVICE.changePriceById(line, new BigDecimal(price))
                        ) {
                            System.out.println("Successful");
                        } else System.out.println("Oops... Seems like vehicle with this ID doesn't exist");

                        System.out.println("\nInput any symbol to return to main menu");
                        line = reader.readLine();
                        break;
                    case "3":
                        System.out.println("Ok. Print ID of vehicle you need to delete:");
                        line = reader.readLine();
                        if(
                        AUTO_SERVICE.delete(line) ||
                        BUS_SERVICE.delete(line) ||
                        TRUCK_SERVICE.delete(line)
                        ) {
                            System.out.println("Successful");
                        } else System.out.println("Oops... Seems like vehicle with this ID doesn't exist");
                        System.out.println("\nInput any symbol to return to main menu");
                        line = reader.readLine();
                        break;
                    case "0": break;
                    default:
                        System.out.println("Unexpected value: \"" + navigation +"\". Try 1-3 for tasks or 0 to exit");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }
}