//package com.repository;
//
//import com.model.Auto;
//import com.model.Manufacturer;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//
//class AutoRepositoryTest {
//
//    private AutoRepository target;
//
//    private Auto auto;
//
//    private Auto createSimpleAuto() {
//        return new Auto("TestModelAuto", Manufacturer.KIA, BigDecimal.ZERO, "TestBodyTypeAuto", 1);
//    }
//
//    @BeforeEach
//    void setUp() {
//        target = new AutoRepository();
//        auto = createSimpleAuto();
////        Mockito.when(auto.getId()).thenReturn("123");
//        target.save(auto);
//
//    }
//
//    @Test
//    void findById_findOne() {
////        Mockito.when(auto.getId()).thenReturn("123");
//        final Optional<Auto> actual = target.findById(auto.getId());
//        Assertions.assertNotNull(actual);
//        Assertions.assertEquals(auto.getId(), actual.get().getId());
//    }
//
//    @Test
//    void findById_notFound() {
//        final Optional<Auto> actual = target.findById("");
//        Assertions.assertEquals(Optional.empty(), actual);
//    }
//
//    @Test
//    void findById_findOne_manyAutos() {
//        final Auto otherAuto = createSimpleAuto();
//        target.save(otherAuto);
//        final Optional<Auto> actual = target.findById(auto.getId());
//        Assertions.assertNotNull(actual);
//        Assertions.assertEquals(auto.getId(), actual.get().getId());
//        Assertions.assertNotEquals(otherAuto.getId(),actual.get().getId());
//    }
//
////    @Test
////    void getByPrice() {
////        Auto mockedAuto = Mockito.mock(Auto.class);
////        target.save(auto);
////        target.getByPrice(auto.getPrice());
////        ArgumentCaptor<Auto> captor = ArgumentCaptor.forClass(Auto.class);
////        Mockito.verify(mockedAuto, Mockito.times(2)).equals(captor.capture());
////        System.out.println(captor.getValue());
//////        Assertions.assertEquals(auto.getPrice(), captor.getValue());
////    }
//
//    @Test
//    void getAll() {
//        final List<Auto> actual = target.getAll();
//        Assertions.assertNotNull(actual);
//        Assertions.assertEquals(1, actual.size());
//    }
//
//    @Test
//    void save_nullArgument() {
//        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
//    }
//
//    @Test
//    void update_notFound() {
//        final boolean actual = target.update(Mockito.mock(Auto.class).getId(), BigDecimal.TEN);
//        Assertions.assertFalse(actual);
//    }
//
//    @Test
//    void update_successful() {
//        auto = createSimpleAuto();
//        target.save(auto);
//        final boolean actual = target.update(auto.getId(), BigDecimal.TEN);
//        final Optional<Auto> actualAuto = target.findById(auto.getId());
//        Assertions.assertTrue(actual);
//        Assertions.assertEquals(actualAuto.get().getId(), auto.getId());
//        Assertions.assertEquals(BigDecimal.TEN, actualAuto.get().getPrice());
//    }
//
//    @Test
//    void delete_notFound() {
//        final boolean actual = target.delete(createSimpleAuto().getId());
//        Assertions.assertFalse(actual);
//    }
//
//    @Test
//    void delete_successful() {
//        final boolean actual = target.delete(auto.getId());
//        final List<Auto> actualList = target.getAll();
//        Assertions.assertTrue(actual);
//        Assertions.assertEquals(0, actualList.size());
//    }
//}