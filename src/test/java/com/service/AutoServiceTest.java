package com.service;

import com.model.Auto;
import com.model.Manufacturer;
import com.repository.AutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.ArgumentCaptor;


import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AutoServiceTest {

    private AutoService target;
    private AutoRepository autoRepository;


    private Auto createSimpleAuto() {
        return new Auto("TestModelAuto", Manufacturer.KIA, BigDecimal.ZERO, "TestBodyTypeAuto");
    }

    @BeforeEach
    void setUp() {
        autoRepository = Mockito.mock(AutoRepository.class);

        target = new AutoService(autoRepository);
    }

    @Test
    void createAndSaveAutos_negativeArgument() {
        final List<Auto> actual = target.createAndSaveAutos(-1);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createAndSaveAutos_zeroAsArgument() {
        final List<Auto> actual = target.createAndSaveAutos(0);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createAndSaveAutos() {
        final List<Auto> actual = target.createAndSaveAutos(5);
        Assertions.assertEquals(5, actual.size());
        Mockito.verify(autoRepository, Mockito.times(5)).save(Mockito.any());
    }

    @Test
    void create_withAutoArgument() {
        target.create(Mockito.mock(Auto.class));
        Mockito.verify(autoRepository).save(Mockito.any());
    }


    @Test
    void printAll() {
        List<Auto> autos = List.of(Mockito.mock(Auto.class), Mockito.mock(Auto.class));
        Mockito.when(autoRepository.getAll()).thenReturn(autos);
        target.printAll();
    }



    @Test
    void changePriceById() {
    }

    @Test
    void delete() {
    }
}