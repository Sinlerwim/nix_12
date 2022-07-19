package com.service;

import com.model.Auto;
import com.model.Manufacturer;
import com.model.Vehicle;
import com.repository.AutoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class AutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);
    private final AutoRepository AUTO_REPOSITORY;
    private static final Random RANDOM = new Random();

    public AutoService(AutoRepository autoRepository) {
        AUTO_REPOSITORY = autoRepository;
    }

//    public AutoService() {
//        AUTO_REPOSITORY = new AutoRepository();
//    }

    public void create(String model, Manufacturer manufacturer, BigDecimal price, String bodyType) {
        AUTO_REPOSITORY.save(new Auto(model, manufacturer, price, bodyType));
    }

    public void create(Auto auto) {
        AUTO_REPOSITORY.save(auto);
    }

    public void printAll() {
        for (Auto auto : AUTO_REPOSITORY.getAll()) {
            System.out.println(auto);
        }
    }

    public List<Auto> createAndSaveAutos(int count) {
        List<Auto> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Auto auto = new Auto(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    "Model-" + RANDOM.nextInt(1000)
            );
            result.add(auto);
            AUTO_REPOSITORY.save(auto);
            LOGGER.debug("Created auto {}", auto.getId());
        }
        return result;
    }

    public Auto createAndReturn() {
        return new Auto(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    "Model-" + RANDOM.nextInt(1000)
            );
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public boolean changePriceById(String id, BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) >= 0) {
            return AUTO_REPOSITORY.update(id, price);
        }
        else {
            return false;
        }
    }

    public boolean delete(String id) {
        return AUTO_REPOSITORY.delete(id);
    }

    public BigDecimal getSumAllPrices() {
        List<Auto> autos = AUTO_REPOSITORY.getAll();
        if (autos == null || autos.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal sumPrices = BigDecimal.ZERO;
        for (Auto auto : autos) {
            sumPrices = sumPrices.add(auto.getPrice());
        }
        return sumPrices;
    }

    public void printSumAllPrices() {
        System.out.println("Sum of all prices = " + getSumAllPrices());;
    }

    public void findAutoByIdAndPrint(String id) {
        Optional<Auto> foundAuto = AUTO_REPOSITORY.findById(id);
        foundAuto.ifPresent(System.out::println);
    }

    public void findAutoByPriceOrPrintNew(BigDecimal price) {
        Auto foundAuto = AUTO_REPOSITORY.findByPrice(price).orElse(new Auto("Model-" +RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                "Model-" + RANDOM.nextInt(1000)));
        System.out.println(foundAuto);
    }

    public Auto findAutoByPriceOrCreateNew(BigDecimal price) {
        Optional<Auto> foundAuto = Optional.of(AUTO_REPOSITORY.findByPrice(price).orElseGet(this::createAndReturn));
        return foundAuto.get();
    }

    public String findAutoByPriceAndReturnId(BigDecimal price) {
        return AUTO_REPOSITORY.findByPrice(price).map(auto -> auto.getId()).orElse(String.valueOf(0));
    }

    public Auto findAutoByPriceOrReturnNew(BigDecimal price) {
        Optional<Auto> foundAuto = AUTO_REPOSITORY.findByPrice(price).or(() -> Optional.of(new Auto("Model-" + RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                "Model-" + RANDOM.nextInt(1000))));
        return foundAuto.get();
    }

    public Auto findAutoByIdAndFilter(String id, BigDecimal price) throws Exception {
        Optional<Auto> foundAuto;
        foundAuto = Optional.of(AUTO_REPOSITORY.findById(id).filter(auto -> auto.getPrice().compareTo(price) == 1)
                .orElseThrow(() -> new Exception("Filter exception")));
        return foundAuto.get();
    }
}
