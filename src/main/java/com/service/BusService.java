package com.service;

import com.model.Auto;
import com.model.Bus;
import com.model.Manufacturer;
import com.repository.BusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusService.class);
    private static final Random RANDOM = new Random();
    private static final BusRepository BUS_REPOSITORY = new BusRepository();

    public List<Bus> createBuses(int count) {
        List<Bus> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Bus bus = new Bus(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    RANDOM.nextInt(8, 36)
            );

            result.add(bus);
            LOGGER.debug("Created bus {}", bus.getId());
        }
        return result;
    }

    public void create(String model, Manufacturer manufacturer, BigDecimal price, int numberOfSeats) {
        BUS_REPOSITORY.create(new Bus(model, manufacturer, price, numberOfSeats));
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveBuses(List<Bus> buses) {
        BUS_REPOSITORY.create(buses);
    }

    public void printAll() {
        for (Bus bus : BUS_REPOSITORY.getAll()) {
            System.out.println(bus);
        }
    }

    public boolean changePriceById(String id, BigDecimal price) {
        return BUS_REPOSITORY.update(id, price);
    }

    public boolean delete(String id) {
        return BUS_REPOSITORY.delete(id);
    }
}
