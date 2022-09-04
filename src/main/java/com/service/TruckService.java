package com.service;

import com.model.Manufacturer;
import com.model.Truck;
import com.repository.CrudRepository;
import com.repository.DBTruckRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class TruckService extends VehicleService<Truck> {


    private static TruckService instance;

    private TruckService(CrudRepository<Truck> repository) {
        super(repository);
    }

    public static TruckService getInstance() {
        if (instance == null) {
            instance = new TruckService(DBTruckRepository.getInstance());
        }
        return instance;
    }

    public Optional<List<Truck>> findByInvoice(String id) {
        return repository.findByInvoice(id);
    }

    public List<Truck> getAll() {
        return repository.getAll();
    }

    public void clear() {
        repository.clear();
    }

    public Truck create() {
        return new Truck(
                "Model-" + RANDOM.nextInt(1000),
                Manufacturer.KIA,
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                RANDOM.nextInt(5000, 12000),
                RANDOM.nextInt(1, 20));
    }

    public Truck createAndSave() {
        final Truck truck = create();
        repository.save(truck);
        LOGGER.debug("Created truck {}", truck.getId());
        return truck;
    }

    public void create(String model, Manufacturer manufacturer, BigDecimal price, int allowTrailerWeight) {
        repository.save(new Truck(model, manufacturer, price, allowTrailerWeight, 1));
    }

    public void printAll() {
        for (Truck truck : repository.getAll()) {
            System.out.println(truck);
        }
    }

    public boolean changePriceById(String id, BigDecimal price) {
        return repository.update(id, price);
    }

    public boolean delete(String id) {
        return repository.delete(id);
    }


}
