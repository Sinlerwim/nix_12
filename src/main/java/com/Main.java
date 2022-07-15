package com;

import com.repository.AutoRepository;
import com.service.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    private static final AutoService AUTO_SERVICE = new AutoService(new AutoRepository());
    private static final BusService BUS_SERVICE = new BusService();
    private static final TruckService TRUCK_SERVICE = new TruckService();

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

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            do {
                System.out.println("Choose what you want to do:");
                System.out.println("1. To CREATE the new vehicle");
                System.out.println("2. To UPDATE the price by id");
                System.out.println("3. To DELETE one by id ");
                System.out.println("4. To PRINT list of existing vehicles");
                System.out.println("Choose the action you need (1-4) or 0 to exit:");
            } while(navigation(reader));
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}