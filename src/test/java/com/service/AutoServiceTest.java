package com.service;

import com.model.Auto;
import com.model.Manufacturer;
import com.repository.AutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.ArgumentCaptor;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

class AutoServiceTest {

    private AutoService target;
    private AutoRepository autoRepository;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private Auto auto = createSimpleAuto();
 //   private final String targetAuto =


    private Auto createSimpleAuto() {
        return new Auto("TestModelAuto", Manufacturer.KIA, BigDecimal.ONE, "TestBodyTypeAuto");
    }

    @BeforeEach
    void setUp() {
        autoRepository = Mockito.mock(AutoRepository.class);
        System.setOut(new PrintStream(outputStreamCaptor));

        target = new AutoService(autoRepository);
//        Mockito.when(auto.getId()).thenReturn("123");
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
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
    }

    @Test
    void delete() {
    }

    @Test
    void findAutoByIdAndPrint_existingAuto() {
        Mockito.when(autoRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(auto));

        target.findAutoByIdAndPrint(auto.getId());
        Assertions.assertEquals(auto.toString(), outputStreamCaptor.toString().trim());
    }

    @Test
    void findAutoByIdAndPrint_notExistingAuto() {
        target.findAutoByIdAndPrint("");
        Assertions.assertNotEquals(auto.toString(), outputStreamCaptor.toString().trim());
    }

    @Test
    void findAutoByPriceOrPrintNew_existingAuto() {
        Mockito.when(autoRepository.findByPrice(Mockito.any())).thenReturn(java.util.Optional.of(auto));
        target.findAutoByPriceOrPrintNew(auto.getPrice());

        Assertions.assertEquals(auto.toString(), outputStreamCaptor.toString().trim());
    }

    @Test
    void findAutoByPriceOrPrintNew_notExistingAuto() {
        target.findAutoByPriceOrPrintNew(BigDecimal.ZERO);
        Assertions.assertNotEquals(auto.toString(), outputStreamCaptor.toString().trim());
    }

    @Test
    void findAutoByPriceOrCreateNew_existingAuto() {
        Mockito.when(autoRepository.findByPrice(Mockito.any())).thenReturn(java.util.Optional.of(auto));

        Assertions.assertEquals(target.findAutoByPriceOrCreateNew(auto.getPrice()), auto);
    }

    @Test
    void findAutoByPriceAndReturnId_existingAuto() {
        Mockito.when(autoRepository.findByPrice(Mockito.any())).thenReturn(java.util.Optional.of(auto));

        Assertions.assertEquals(auto.getId(), target.findAutoByPriceAndReturnId(auto.getPrice()));
    }

    @Test
    void findAutoByPriceAndReturnId_notExistingAuto() {
        Assertions.assertEquals(String.valueOf(0), target.findAutoByPriceAndReturnId(auto.getPrice()));
    }


    @Test
    void findAutoByPriceOrReturnNew_existingAuto() {
        Mockito.when(autoRepository.findByPrice(Mockito.any())).thenReturn(java.util.Optional.of(auto));

        Assertions.assertEquals(target.findAutoByPriceOrReturnNew(auto.getPrice()), auto);
    }

    @Test
    void findAutoByPriceOrReturnNew_notExistingAuto() {
        Assertions.assertNotEquals(target.findAutoByPriceOrReturnNew(auto.getPrice()), auto);
    }

    @Test
    void findAutoByIdAndFilter_existingAutoAndPassFilter() throws Exception {
        Mockito.when(autoRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(auto));

        Assertions.assertEquals(auto, target.findAutoByIdAndFilter(auto.getId(),BigDecimal.ZERO));
    }

    @Test
    void findAutoByIdAndFilter_existingAutoAndDoesntPassFilter() throws Exception {
        Mockito.when(autoRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(auto));

        Exception exception = Assertions.assertThrows(Exception.class,() -> target.findAutoByIdAndFilter(auto.getId(),BigDecimal.TEN));
        Assertions.assertEquals("Filter exception", exception.getMessage());
    }

    @Test
    void findAutoByIdAndFilter_notExistingAutoAndPassFilter() throws Exception {
        Exception exception = Assertions.assertThrows(Exception.class,() -> target.findAutoByIdAndFilter(auto.getId(),BigDecimal.ZERO));
        Assertions.assertEquals("Filter exception", exception.getMessage());
    }

    @Test
    void findAutoByIdAndFilter_notExistingAutoAndDoesntPassFilter() throws Exception {
        Exception exception = Assertions.assertThrows(Exception.class,() -> target.findAutoByIdAndFilter(auto.getId(),BigDecimal.TEN));
        Assertions.assertEquals("Filter exception", exception.getMessage());
    }
}