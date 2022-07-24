package com.service;

import com.model.Auto;
import com.model.Manufacturer;
import com.repository.AutoRepository;

import com.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class AutoService extends VehicleService<Auto> {

    public AutoService(CrudRepository<Auto> autoRepository) {
        super(autoRepository);
    }

//    public AutoService() {
//        AUTO_REPOSITORY = new AutoRepository();
//    }

    public void create(String model, Manufacturer manufacturer, BigDecimal price, String bodyType) {
        repository.save(new Auto(model, manufacturer, price, bodyType));
    }

    public void create(Auto auto) {
        repository.save(auto);
    }

    public void printAll() {
        for (Auto auto : repository.getAll()) {
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
            repository.save(auto);
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

    public boolean changePriceById(String id, BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) >= 0) {
            return repository.update(id, price);
        }
        else {
            return false;
        }
    }

    public boolean delete(String id) {
        return repository.delete(id);
    }

    public BigDecimal getSumAllPrices() {
        List<Auto> autos = repository.getAll();
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
        Optional<Auto> foundAuto = repository.findById(id);
        foundAuto.ifPresent(System.out::println);
    }

    public void findAutoByPriceOrPrintNew(BigDecimal price) {
        Auto foundAuto = repository.findByPrice(price).orElse(new Auto("Model-" +RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                "Model-" + RANDOM.nextInt(1000)));
        System.out.println(foundAuto);
    }

    public Auto findAutoByPriceOrCreateNew(BigDecimal price) {
        Optional<Auto> foundAuto = Optional.of(repository.findByPrice(price).orElseGet(this::createAndReturn));
        return foundAuto.get();
    }

    public String findAutoByPriceAndReturnId(BigDecimal price) {
        return repository.findByPrice(price).map(auto -> auto.getId()).orElse(String.valueOf(0));
    }

    public Auto findAutoByPriceOrReturnNew(BigDecimal price) {
        Optional<Auto> foundAuto = repository.findByPrice(price).or(() -> Optional.of(new Auto("Model-" + RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                "Model-" + RANDOM.nextInt(1000))));
        return foundAuto.get();
    }

    public Auto findAutoByIdAndFilter(String id, BigDecimal price) throws Exception {
        Optional<Auto> foundAuto;
        foundAuto = Optional.of(repository.findById(id).filter(auto -> auto.getPrice().compareTo(price) == 1)
                .orElseThrow(() -> new Exception("Filter exception")));
        return foundAuto.get();
    }
}
