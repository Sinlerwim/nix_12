package com.repository;

import com.model.Auto;
import com.model.Manufacturer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;

class AutoRepositoryTest {

    private AutoRepository target;

    private Auto auto;

    private Auto createSimpleAuto() {
        return new Auto("TestModelAuto", Manufacturer.KIA, BigDecimal.ZERO, "TestBodyTypeAuto");
    }

    @BeforeEach
    void setUp() {
        target = new AutoRepository();
        auto = Mockito.mock(Auto.class);
        Mockito.when(auto.getId()).thenReturn("123");
        target.save(auto);

    }

    @Test
    void getById_findOne() {
        Mockito.when(auto.getId()).thenReturn("123");
        final Auto actual = target.getById(auto.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(auto.getId(), actual.getId());
    }

    @Test
    void getById_notFound() {
        final Auto actual = target.getById("");
        Assertions.assertNull(actual);
    }

    @Test
    void getById_findOne_manyAutos() {
        final Auto otherAuto = createSimpleAuto();
        target.save(otherAuto);
        final Auto actual = target.getById(auto.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(auto.getId(), actual.getId());
        Assertions.assertNotEquals(otherAuto.getId(),actual.getId());
    }

    @Test
    void getAll() {
        final List<Auto> actual = target.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void save_nullArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void save_isCorrectlySaved() {
        target = Mockito.mock(AutoRepository.class);
        Auto otherAuto = createSimpleAuto();
        target.save(otherAuto);

//        Assertions.assertTrue(new ArgumentMatcher<Auto>() {
//            @Override
//            public boolean matches(Auto auto) {
//                return auto.getId().equals(otherAuto.getId()) &&
//                        auto.getPrice().equals(otherAuto.getPrice()) &&
//                        auto.getManufacturer().equals(otherAuto.getManufacturer()) &&
//                        auto.getBodyType().equals(otherAuto.getBodyType()) &&
//                        auto.getModel().equals(otherAuto.getModel());
//            }
//        }.matches(otherAuto));
        Mockito.verify(target).save(argThat(new ArgumentMatcher<Auto>() {

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
    void update_notFound() {
        final boolean actual = target.update(Mockito.mock(Auto.class).getId(), BigDecimal.TEN);
        Assertions.assertFalse(actual);
    }

    @Test
    void update_successful() {
        auto = createSimpleAuto();
        target.save(auto);
        final boolean actual = target.update(auto.getId(), BigDecimal.TEN);
        final Auto actualAuto = target.getById(auto.getId());
        Assertions.assertTrue(actual);
        Assertions.assertEquals(actualAuto.getId(), auto.getId());
        Assertions.assertEquals(BigDecimal.TEN, actualAuto.getPrice());
    }

    @Test
    void delete_notFound() {
        final boolean actual = target.delete(createSimpleAuto().getId());
        Assertions.assertFalse(actual);
    }

    @Test
    void delete_successful() {
        final boolean actual = target.delete(auto.getId());
        final List<Auto> actualList = target.getAll();
        Assertions.assertTrue(actual);
        Assertions.assertEquals(0, actualList.size());
    }
}