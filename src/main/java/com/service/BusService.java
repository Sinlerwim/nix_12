package com.service;

import com.model.Bus;
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

    public void save(Bus bus) {
        repository.save(bus);
    }

    public void printAll() {
        for (Bus bus : repository.getAll()) {
            System.out.println(bus);
        }
    }

    public Optional<List<Bus>> findByInvoice(String id) {
        return repository.findByInvoice(id);
    }

    public List<Bus> getAll() {
        return repository.getAll();
    }

    public Bus getRandomBus() {
        Bus bus = new Bus();
        bus.setModel("Model-" + RANDOM.nextInt(1000));
        bus.setManufacturer(getRandomManufacturer());
        bus.setPrice(BigDecimal.valueOf(RANDOM.nextDouble(1000.0)));
        bus.setNumberOfSeats(RANDOM.nextInt(9, 36));
        bus.setCount(RANDOM.nextInt(1, 20));
        return bus;
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
