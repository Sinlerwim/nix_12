package com.service;

import com.model.Bus;
import com.model.Manufacturer;
import com.repository.CrudRepository;
import com.repository.DBBusRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class BusService extends VehicleService<Bus> {

    private static BusService instance;

    private BusService(CrudRepository<Bus> repository) {
        super(repository);
    }

    public static BusService getInstance() {
        if (instance == null) {
            instance = new BusService(DBBusRepository.getInstance());
        }
        return instance;
    }

    public Optional<List<Bus>> findByInvoice(String id) {
        return repository.findByInvoice(id);
    }

    public List<Bus> getAll() {
        return repository.getAll();
    }

    public void create(String model, Manufacturer manufacturer, BigDecimal price, int numberOfSeats) {
        repository.save(new Bus(model, manufacturer, price, numberOfSeats, 1));
    }

    public Bus create() {
        return new Bus(
                "Model-" + RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                RANDOM.nextInt(9, 36),
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

    public void clear() {
        repository.clear();
    }

    public boolean changePriceById(String id, BigDecimal price) {
        return repository.update(id, price);
    }

    public boolean delete(String id) {
        return repository.delete(id);
    }
}
