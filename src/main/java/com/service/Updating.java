package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;

public class Updating {
    public static void start(BufferedReader reader, AutoService AUTO_SERVICE, BusService BUS_SERVICE, TruckService TRUCK_SERVICE) throws IOException {
        String id = getId(reader);
        String price = getPrice(reader);
        if (
                AUTO_SERVICE.changePriceById(id, new BigDecimal(price)) ||
                        BUS_SERVICE.changePriceById(id, new BigDecimal(price)) ||
                        TRUCK_SERVICE.changePriceById(id, new BigDecimal(price))
        ) {
            System.out.println("Successful");
        } else System.out.println("Oops... Seems like vehicle with this ID doesn't exist");
        System.out.println("\nInput any symbol to return to main menu");
        reader.readLine();
    }

    private static String getId(BufferedReader reader) throws IOException {
        System.out.println("Ok. Print ID of vehicle you need to update:");
        return reader.readLine();
    }

    private static String getPrice(BufferedReader reader) throws IOException {
        System.out.println("Ok. Print the new price:");
        return reader.readLine();
    }
}
