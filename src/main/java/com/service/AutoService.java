package com.service;

import com.annotation.Autowired;
import com.annotation.Singleton;
import com.model.Auto;
import com.repository.MongoAutoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Singleton
public class AutoService extends VehicleService<Auto> {

    private static AutoService instance;

    public void clear() {
        repository.clear();
    }

    @Autowired
    public AutoService(MongoAutoRepository mongoAutoRepository) {
        super(mongoAutoRepository);
    }

    public void save(Auto auto) {
        repository.save(auto);
    }

    public void saveRandomAuto() {
        repository.save(getRandomAuto());
    }

    public void printAll() {
        for (Auto auto : repository.getAll()) {
            System.out.println(auto);
        }
    }

    public Optional<List<Auto>> findByInvoice(String id) {
        return repository.findByInvoice(id);
    }

    public List<Auto> getAll() {
        return repository.getAll();
    }

    public Auto getRandomAuto() {
        Auto auto = new Auto();
        auto.setModel("Model-" + RANDOM.nextInt(1000));
        auto.setManufacturer(getRandomManufacturer());
        auto.setPrice(BigDecimal.valueOf(RANDOM.nextDouble(1000.0)));
        auto.setBodyType("BT-" + RANDOM.nextInt(1000));
        auto.setCount(RANDOM.nextInt(1, 20));
        auto.setEngine(ENGINE_SERVICE.getOneRandomEngine());
        return auto;
    }

    public boolean changePriceById(String id, BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) >= 0) {
            return repository.update(id, price);
        } else {
            return false;
        }
    }

    public boolean delete(String id) {
        return repository.delete(id);
    }
}
