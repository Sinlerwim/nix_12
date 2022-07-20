package com;

import com.repository.AutoRepository;
import com.repository.BusRepository;
import com.repository.TruckRepository;
import com.service.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.math.BigDecimal;

public class Main {
    private static final AutoService AUTO_SERVICE = new AutoService(new AutoRepository());
    private static final BusService BUS_SERVICE = new BusService(new BusRepository());
    private static final TruckService TRUCK_SERVICE = new TruckService(new TruckRepository());

    private static void printAllServices() {
        System.out.println("\nList of all vehicles:");
        AUTO_SERVICE.printAll();
        BUS_SERVICE.printAll();
        TRUCK_SERVICE.printAll();
        System.out.println();
    }

    private static boolean navigation(BufferedReader reader) throws IOException {
        String input = reader.readLine();
        switch (input) {
            case "1":
                Creating.start(reader, AUTO_SERVICE, BUS_SERVICE, TRUCK_SERVICE);
                break;
            case "2":
                Updating.start(reader, AUTO_SERVICE, BUS_SERVICE, TRUCK_SERVICE);
                break;
            case "3":
                Deleting.start(reader, AUTO_SERVICE, BUS_SERVICE, TRUCK_SERVICE);
                break;
//            case "4":
//                Reading.start(reader, AUTO_SERVICE, BUS_SERVICE, TRUCK_SERVICE);
//                break;
            case "5":
                printAllServices();
                break;
            case "0":
                return false;
            default:
                System.out.println("Unexpected value: \"" + input + "\". Try 1-3 for levels or 0 to exit");
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("\n\n\nWelcome to homework #10!\n\nHere will be the list of your vehicles.\n");
        AUTO_SERVICE.createAndSaveAutos(5);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            do {
                System.out.println("Choose what you want to do:");
                System.out.println("1. To CREATE the new vehicle");
                System.out.println("2. To UPDATE the price by id");
                System.out.println("3. To DELETE one by id ");
//                System.out.println("4. To FIND vehicle by parameter");
                System.out.println("5. To PRINT list of existing vehicles");
                System.out.println("Choose the action you need (1-4) or 0 to exit:");
            } while(navigation(reader));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        AUTO_SERVICE.createAndSaveAutos(5);
//        AUTO_SERVICE.printAll();

//        System.out.println("findAutoBy !PRICE! OrPrintNew (Existing auto):");
//        AUTO_SERVICE.findAutoByPriceOrPrintNew(BigDecimal.valueOf(Double.parseDouble(reader.readLine())));
//        System.out.println("^ Found");
//
//        AUTO_SERVICE.printAll();
//
//        System.out.println("findAutoByPriceOrPrintNew (Not existing auto):");
//        AUTO_SERVICE.findAutoByPriceOrPrintNew(BigDecimal.valueOf(Double.parseDouble(reader.readLine())));
//        System.out.println("^ Printed");
//
//        AUTO_SERVICE.printAll();
//
//        System.out.println("findAutoByPriceOrCreateNew (Existing auto):");
//        System.out.println(AUTO_SERVICE.findAutoByPriceOrCreateNew(BigDecimal.valueOf(Double.parseDouble(reader.readLine()))));
//        System.out.println("^Found");
//
//        AUTO_SERVICE.printAll();
//
//        System.out.println("findAutoByPriceOrCreateNew (Not existing auto):");
//        AUTO_SERVICE.create(AUTO_SERVICE.findAutoByPriceOrCreateNew(BigDecimal.valueOf(Double.parseDouble(reader.readLine()))));
//        System.out.println("Created & Saved");
//
//        AUTO_SERVICE.printAll();
//
//        System.out.println("findAutoByPriceAndReturnId (Existing)");
//        System.out.println(AUTO_SERVICE.findAutoByPriceAndReturnId(BigDecimal.valueOf(Double.parseDouble(reader.readLine()))));
//        reader.readLine();
//
//        System.out.println("findAutoByPriceAndReturnId (Not existing)");
//        System.out.println(AUTO_SERVICE.findAutoByPriceAndReturnId(BigDecimal.valueOf(Double.parseDouble(reader.readLine()))));
//        reader.readLine();
//
//        System.out.println("findAutoByIdOrReturnNew (Existing auto):");
//        System.out.println(AUTO_SERVICE.findAutoByPriceOrReturnNew(BigDecimal.valueOf(Double.parseDouble(reader.readLine()))));
//        System.out.println("^ Found");
//
//        System.out.println("findAutoByIdOrReturnNew (Not Existing auto):");
//        System.out.println(AUTO_SERVICE.findAutoByPriceOrReturnNew(BigDecimal.valueOf(Double.parseDouble(reader.readLine()))));
//        System.out.println("^ Created");
//
//        AUTO_SERVICE.printAll();
//
//        try {
//            System.out.println("findAutoByIdAndFilter (Existing auto):");
//            System.out.println(AUTO_SERVICE.findAutoByIdAndFilter(reader.readLine(), BigDecimal.valueOf(Double.parseDouble(reader.readLine()))));
//            System.out.println("^ Found");
//
//            System.out.println("findAutoByIdOrReturnNew (Not Existing auto):");
//            System.out.println(AUTO_SERVICE.findAutoByIdAndFilter(reader.readLine(), BigDecimal.valueOf(Double.parseDouble(reader.readLine()))));
//            System.out.println("^ Created");
//        } catch (Exception e) {
//            System.out.println("Error: " + e);
//        } finally {
//            AUTO_SERVICE.printAll();
//        }
//
//        reader.readLine();
    }
}