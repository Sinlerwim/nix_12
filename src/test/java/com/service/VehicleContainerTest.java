package com.service;

import com.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class VehicleContainerTest {

    private VehicleContainer<Vehicle> target;

    private Auto auto;

    private Bus bus;

    private Truck truck;

    private List<Vehicle> vehicles;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private Auto createSimpleAuto() {
        return new Auto("TestModelAuto", Manufacturer.KIA, BigDecimal.ONE, "TestBodyType");
    }

    private Bus createSimpleBus() {
        return new Bus("TestModelBus", Manufacturer.BMW, BigDecimal.TEN, 24);
    }

//    private Truck createSimpleTruck() {
//        return new Truck("TestModelTruck", Manufacturer.BMW, BigDecimal.TEN, 2400);
//    }

    @BeforeEach
    void setUp() {
        target = new VehicleContainer<Vehicle>();
        auto = createSimpleAuto();
        bus = createSimpleBus();
//        truck = createSimpleTruck();

        target.addVehicle(auto);
        target.addVehicle(bus);
    }

    @Test
    void findById_findOne() {
        final Optional<Vehicle> actual = target.findById(auto.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(auto.getId(), actual.get().getId());
    }

    @Test
    void findById_notFound() {
        final Optional<Vehicle> actual = target.findById("");
        Assertions.assertEquals(Optional.empty(), actual);
    }

    @Test
    void addVehicle_differentTypes () {
        System.setOut(new PrintStream(outputStreamCaptor));

        target.printAll();

        String expected = auto.toString().trim() + System.lineSeparator()  + bus.toString().trim();
        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());

        System.setOut(System.out);
    }

    @Test
    void deleteVehicleById_successful() {

        Assertions.assertTrue(target.deleteVehicleById(auto.getId()));
        final List<Vehicle> actual = target.getAll();
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void deleteVehicleById_failed() {

        Assertions.assertFalse(target.deleteVehicleById(""));
        final List<Vehicle> actual = target.getAll();
        Assertions.assertEquals(2, actual.size());
    }

    @Test
    void printAll() {
        System.setOut(new PrintStream(outputStreamCaptor));

        target.printAll();
        String expected = auto.toString().trim() + System.lineSeparator()  + bus.toString().trim();
        Assertions.assertEquals(expected, outputStreamCaptor.toString().trim());

        System.setOut(System.out);
    }

    @Test
    void getAll_successful() {
        target.addVehicle(truck);
        final List<Vehicle> actual = target.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(3, actual.size());
    }

    @Test
    void changePriceRandomly_successful() {

        Assertions.assertTrue(target.changePriceRandomly(auto.getId()));
        BigDecimal actual = target.findById(auto.getId()).get().getPrice();
        Assertions.assertEquals(1, BigDecimal.ONE.compareTo(actual));
    }

    @Test
    void changeAllPricesRandomly() {
        target.changeAllPricesRandomly();

        BigDecimal actualAutoPrice = target.findById(auto.getId()).get().getPrice();
        BigDecimal actualBusPrice = target.findById(bus.getId()).get().getPrice();

        Assertions.assertEquals(1, BigDecimal.ONE.compareTo(actualAutoPrice));
        Assertions.assertEquals(1, BigDecimal.TEN.compareTo(actualBusPrice));
    }

    @Test
    void increasePrice() {
        Assertions.assertTrue(target.increasePrice(auto.getId(), 1));
        BigDecimal actualAutoPrice = target.findById(auto.getId()).get().getPrice();
        Assertions.assertEquals(-1, BigDecimal.ONE.compareTo(actualAutoPrice));
    }

    @Test
    void increaseAllPrices() {
        target.increaseAllPrices(1);

        BigDecimal actualAutoPrice = target.findById(auto.getId()).get().getPrice();
        BigDecimal actualBusPrice = target.findById(bus.getId()).get().getPrice();

        Assertions.assertEquals(-1, BigDecimal.ONE.compareTo(actualAutoPrice));
        Assertions.assertEquals(-1, BigDecimal.TEN.compareTo(actualBusPrice));
    }
}