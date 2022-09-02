package com.service;

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

    public void save(Truck truck) {
        repository.save(truck);
    }

    public void printAll() {
        for (Truck truck : repository.getAll()) {
            System.out.println(truck);
        }
    }

    public Optional<List<Truck>> findByInvoice(String id) {
        return repository.findByInvoice(id);
    }

    public List<Truck> getAll() {
        return repository.getAll();
    }

    public Truck getRandomTruck() {
        Truck truck = new Truck();
        truck.setModel("Model-" + RANDOM.nextInt(1000));
        truck.setManufacturer(getRandomManufacturer());
        truck.setPrice(BigDecimal.valueOf(RANDOM.nextDouble(1000.0)));
        truck.setAllowTrailerWeight(RANDOM.nextInt(6000, 10000));
        truck.setCount(RANDOM.nextInt(1, 20));
        return truck;
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
