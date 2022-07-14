package com.repository;

import com.model.Bus;
import com.model.Manufacturer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class BusRepositoryTest {
    private BusRepository target;

    private Bus bus;

    private Bus createSimpleBus() {
        return new Bus("TestModelBus", Manufacturer.KIA, BigDecimal.ZERO, 24);
    }

    @BeforeEach
    void setUp() {
        target = new BusRepository();
        bus = createSimpleBus();
        target.save(bus);
    }

    @Test
    void getById_findOne() {
        final Bus actual = target.getById(bus.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(bus.getId(), actual.getId());
    }

    @Test
    void getById_notFound() {
        final Bus actual = target.getById("");
        Assertions.assertNull(actual);
    }

    @Test
    void getById_findOne_manyAutos() {
        final Bus otherAuto = createSimpleBus();
        target.save(otherAuto);
        final Bus actual = target.getById(bus.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(bus.getId(), actual.getId());
        Assertions.assertNotEquals(otherAuto.getId(),actual.getId());
    }

    @Test
    void getAll() {
        final List<Bus> actual = target.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void save() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void update_notFound() {
        final Bus otherAuto = createSimpleBus();
        final boolean actual = target.update(otherAuto.getId(), BigDecimal.TEN);
        Assertions.assertFalse(actual);
    }

    @Test
    void update_successful() {
        final boolean actual = target.update(bus.getId(), BigDecimal.TEN);
        final Bus actualAuto = target.getById(bus.getId());
        Assertions.assertTrue(actual);
        Assertions.assertEquals(actualAuto.getId(), bus.getId());
        Assertions.assertEquals(BigDecimal.TEN, actualAuto.getPrice());
    }

    @Test
    void delete_notFound() {
        final boolean actual = target.delete(createSimpleBus().getId());
        Assertions.assertFalse(actual);
    }

    @Test
    void delete_successful() {
        final boolean actual = target.delete(bus.getId());
        final List<Bus> actualList = target.getAll();
        Assertions.assertTrue(actual);
        Assertions.assertEquals(0, actualList.size());
    }
}