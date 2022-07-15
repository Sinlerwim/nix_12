package com.service;

import com.model.Auto;
import com.model.Manufacturer;
import com.repository.AutoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutoService.class);
    private final AutoRepository AUTO_REPOSITORY;
    private static final Random RANDOM = new Random();

    public AutoService(AutoRepository autoRepository) {
        AUTO_REPOSITORY = autoRepository;
    }

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

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public boolean changePriceById(String id, BigDecimal price) {
        return AUTO_REPOSITORY.update(id, price);
    }

    public boolean delete(String id) {
        return AUTO_REPOSITORY.delete(id);
    }
}
