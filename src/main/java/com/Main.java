package com;

import com.model.Auto;
import com.model.Manufacturer;
import com.repository.AutoRepository;
import com.repository.BusRepository;
import com.repository.Garage;
import com.repository.TruckRepository;
import com.service.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final AutoService AUTO_SERVICE = new AutoService(new AutoRepository());
    private static final BusService BUS_SERVICE = new BusService(new BusRepository());
    private static final TruckService TRUCK_SERVICE = new TruckService(new TruckRepository());
    protected static final Random RANDOM = new Random();

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
            case "4":
                comparatorExample();
                System.out.println("Input any symbol");
                reader.readLine();
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

    private static void comparatorExample() {
        System.out.println("There is example of comparator's work");
        final List<Auto> autos = new LinkedList<>();
        System.out.println("Not sorted list:");
        for (int i = 0; i < 6; i++) {
            final Auto auto = new Auto(
                    "Model-" + RANDOM.nextInt(1000),
                    Manufacturer.KIA,
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    "Model-" + RANDOM.nextInt(1000), RANDOM.nextInt(1, 20));
            System.out.println(auto);
            autos.add(auto);
        }
        final Comparator<Auto> comparator = Comparator.comparing(Auto::getPrice).reversed()
                .thenComparing(auto -> auto.getClass().getSimpleName())
                .thenComparingInt(Auto::getCount);
        autos.sort(comparator);
        System.out.println("Sorted list:");
        for (Auto auto : autos) {
            System.out.println(auto);
        }

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
                System.out.println("4. To check comparator's work");
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