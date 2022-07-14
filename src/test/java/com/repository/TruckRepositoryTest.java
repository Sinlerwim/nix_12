package com.repository;

import com.model.Bus;
import com.model.Manufacturer;
import com.model.Truck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class TruckRepositoryTest {
    private TruckRepository target;

    private Truck bus;

    private Truck createSimpleTruck() {
        return new Truck("TestModelTruck", Manufacturer.KIA, BigDecimal.ZERO, 8000);
    }

    @BeforeEach
    void setUp() {
        target = new TruckRepository();
        bus = createSimpleTruck();
        target.save(bus);
    }

    @Test
    void getById_findOne() {
        final Truck actual = target.getById(bus.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(bus.getId(), actual.getId());
    }

    @Test
    void getById_notFound() {
        final Truck actual = target.getById("");
        Assertions.assertNull(actual);
    }

    @Test
    void getById_findOne_manyAutos() {
        final Truck otherAuto = createSimpleTruck();
        target.save(otherAuto);
        final Truck actual = target.getById(bus.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(bus.getId(), actual.getId());
        Assertions.assertNotEquals(otherAuto.getId(),actual.getId());
    }

    @Test
    void getAll() {
        final List<Truck> actual = target.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void save() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void update_notFound() {
        final Truck otherAuto = createSimpleTruck();
        final boolean actual = target.update(otherAuto.getId(), BigDecimal.TEN);
        Assertions.assertFalse(actual);
    }

    @Test
    void update_successful() {
        final boolean actual = target.update(bus.getId(), BigDecimal.TEN);
        final Truck actualAuto = target.getById(bus.getId());
        Assertions.assertTrue(actual);
        Assertions.assertEquals(actualAuto.getId(), bus.getId());
        Assertions.assertEquals(BigDecimal.TEN, actualAuto.getPrice());
    }

    @Test
    void delete_notFound() {
        final boolean actual = target.delete(createSimpleTruck().getId());
        Assertions.assertFalse(actual);
    }

    @Test
    void delete_successful() {
        final boolean actual = target.delete(bus.getId());
        final List<Truck> actualList = target.getAll();
        Assertions.assertTrue(actual);
        Assertions.assertEquals(0, actualList.size());
    }
}