package com.service;

import java.io.BufferedReader;
import java.io.IOException;

public class Deleting {
    public static void start(BufferedReader reader, AutoService AUTO_SERVICE, BusService BUS_SERVICE, TruckService TRUCK_SERVICE) throws IOException {
        String id = getId(reader);
        if (
                AUTO_SERVICE.delete(id) ||
                        BUS_SERVICE.delete(id) ||
                        TRUCK_SERVICE.delete(id)
        ) {
            System.out.println("Successful");
        } else System.out.println("Oops... Seems like vehicle with this ID doesn't exist");
        System.out.println("\nInput any symbol to return to main menu");
        reader.readLine();
    }

    private static String getId(BufferedReader reader) throws IOException {
        System.out.println("Ok. Print ID of vehicle you need to delete:");
        return reader.readLine();
    }
}
