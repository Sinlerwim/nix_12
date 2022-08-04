package com.repository;

import com.model.Auto;
import com.model.Manufacturer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GarageTest {

    private static final Random RANDOM = new Random();
    private Garage<Auto> target;

    private Auto auto;

    @BeforeEach
    void setUp() {
        target = new Garage<Auto>();
        auto = Mockito.mock(Auto.class);
    }

    @Test
    void add() {
        target.add(auto, 10);
        Assertions.assertEquals(1, target.getSize());
        Assertions.assertEquals(auto, target.search(10).get());
    }

    @Test
    void replace() {
        for (int i = 0; i<4; i++)
            target.add(Mockito.mock(Auto.class), 10);

        target.add(Mockito.mock(Auto.class), 20);

        Assertions.assertTrue(target.replace(20, auto, 30));
        Assertions.assertEquals(auto, target.search(30).get());
    }

    @Test
    void search() {
        for (int i = 0; i<4; i++)
            target.add(Mockito.mock(Auto.class), 10);
        target.add(auto, 20);
        Assertions.assertEquals(auto, target.search(20).get());
    }

    @Test
    void remove() {
        for (int i = 0; i<4; i++)
            target.add(Mockito.mock(Auto.class), 10);
        target.add(auto, 20);
        Assertions.assertEquals(5,target.getSize());
        Assertions.assertTrue(target.remove(20));
        Assertions.assertEquals(4, target.getSize());
    }

}