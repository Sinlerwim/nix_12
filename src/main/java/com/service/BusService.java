package com.service;

import com.model.Bus;
import com.model.Manufacturer;
import com.repository.BusRepository;
import com.repository.CrudRepository;

import java.math.BigDecimal;

public class BusService extends VehicleService<Bus> {

    private static BusService instance;

    private BusService(CrudRepository<Bus> repository) {
        super(repository);
    }

    public static BusService getInstance() {
        if (instance == null) {
            instance = new BusService(BusRepository.getInstance());
        }
        return instance;
    }

    public void create(String model, Manufacturer manufacturer, BigDecimal price, int numberOfSeats) {
        repository.save(new Bus(model, manufacturer, price, numberOfSeats, 1));
    }

    public Bus create() {
        return new Bus(
                "Model-" + RANDOM.nextInt(1000),
                Manufacturer.KIA,
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                RANDOM.nextInt(6, 24),
                RANDOM.nextInt(1, 20));
    }

    public Bus createAndSave() {
        final Bus bus = create();
        repository.save(bus);
        LOGGER.debug("Created bus {}", bus.getId());
        return bus;
    }

    public void printAll() {
        for (Bus bus : repository.getAll()) {
            System.out.println(bus);
        }
    }

    public boolean changePriceById(String id, BigDecimal price) {
        return repository.update(id, price);
    }

    public boolean delete(String id) {
        return repository.delete(id);
    }
}
