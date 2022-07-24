package com.service;

import com.model.Auto;
import com.model.Manufacturer;
import com.repository.AutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.ArgumentCaptor;


import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class AutoServiceTest {

    private AutoService target;
    private AutoRepository autoRepository;


    private Auto createSimpleAuto() {
        return new Auto("TestModelAuto", Manufacturer.KIA, BigDecimal.ONE, "TestBodyTypeAuto");
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
    void create_isCorrectlyCreatedAndSaved () {
        Auto otherAuto = createSimpleAuto();

        target.create(otherAuto);
        Mockito.verify(autoRepository).save(argThat(new ArgumentMatcher<Auto>() {

            @Override
            public boolean matches(Auto auto) {
                return auto.getId().equals(otherAuto.getId()) &&
                        auto.getPrice().equals(otherAuto.getPrice()) &&
                        auto.getManufacturer().equals(otherAuto.getManufacturer()) &&
                        auto.getBodyType().equals(otherAuto.getBodyType()) &&
                        auto.getModel().equals(otherAuto.getModel());
            }
        }));
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
        ArgumentCaptor<String> captorId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<BigDecimal> captorPrice = ArgumentCaptor.forClass(BigDecimal.class);
        Auto auto = createSimpleAuto();
        target.create(auto);
        target.changePriceById(auto.getId(), BigDecimal.TEN);

        Mockito.verify(autoRepository, Mockito.times(1)).update(captorId.capture(), captorPrice.capture());
        Assertions.assertEquals(BigDecimal.TEN, captorPrice.getValue());
        Assertions.assertEquals(auto.getId(), captorId.getValue());
    }

    @Test
    void changePriceById_negativePrice() {
        Auto auto = createSimpleAuto();
        target.create(auto);
        Mockito.when(autoRepository.update(anyString(), any())).thenThrow(
                IllegalArgumentException.class);

        target.changePriceById(auto.getId(), BigDecimal.valueOf(-1));
    }

    @Test
    void getSumAllPrices_emptyRepository() {
        Mockito.when(autoRepository.getAll()).thenCallRealMethod();
        Assertions.assertEquals(BigDecimal.ZERO, target.getSumAllPrices());
//        Assertions.assertThrows(NullPointerException.class, () -> target.getSumAllPrices());
    }

    @Test
    void delete() {
    }
}