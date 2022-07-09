package com.service;

import com.model.Bus;
import com.model.Truck;
import com.model.Manufacturer;
import com.repository.TruckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TruckService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TruckService.class);
    private static final Random RANDOM = new Random();
    private static final TruckRepository TRUCK_REPOSITORY = new TruckRepository();

    public List<Truck> createTrucks(int count) {
        List<Truck> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Truck truck = new Truck(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    RANDOM.nextInt(6000, 10000)
            );

            result.add(truck);
            LOGGER.debug("Created truck {}", truck.getId());
        }
        return result;
    }

    public void create(String model, Manufacturer manufacturer, BigDecimal price, int allowTrailerWeight) {
        TRUCK_REPOSITORY.create(new Truck(model, manufacturer, price, allowTrailerWeight));
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveTrucks(List<Truck> truckes) {
        TRUCK_REPOSITORY.create(truckes);
    }

    public void printAll() {
        for (Truck truck : TRUCK_REPOSITORY.getAll()) {
            System.out.println(truck);
        }
    }

    public boolean changePriceById(String id, BigDecimal price) {
        return TRUCK_REPOSITORY.update(id, price);
    }

    public boolean delete(String id) {
        return TRUCK_REPOSITORY.delete(id);
    }
}
