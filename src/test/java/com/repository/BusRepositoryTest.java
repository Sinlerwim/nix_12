package com.repository;

import com.model.Bus;
import com.model.Manufacturer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    void findById_findOne() {
        final Optional<Bus> actual = target.findById(bus.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(bus.getId(), actual.get().getId());
    }

    @Test
    void findById_notFound() {
        final Optional<Bus> actual = target.findById("");
        Assertions.assertEquals(Optional.empty(), actual);
    }

    @Test
    void findById_findOne_manyAutos() {
        final Bus otherAuto = createSimpleBus();
        target.save(otherAuto);
        final Optional<Bus> actual = target.findById(bus.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(bus.getId(), actual.get().getId());
        Assertions.assertNotEquals(otherAuto.getId(),actual.get().getId());
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
        final Optional<Bus> actualAuto = target.findById(bus.getId());
        Assertions.assertTrue(actual);
        Assertions.assertEquals(actualAuto.get().getId(), bus.getId());
        Assertions.assertEquals(BigDecimal.TEN, actualAuto.get().getPrice());
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