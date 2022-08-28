package com.service;

import com.annotation.Autowired;
import com.annotation.Singleton;
import com.model.Auto;
import com.repository.DBAutoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Singleton
public class AutoService extends VehicleService<Auto> {

    private static AutoService instance;

    public static AutoService getInstance() {
        if (instance == null) {
            instance = new AutoService(DBAutoRepository.getInstance());
        }
        return instance;
    }

    public void clear() {
        repository.clear();
    }

    @Autowired
    private AutoService(DBAutoRepository dbAutoRepository) {
        super(dbAutoRepository);
    }

//    public void create(String model, Manufacturer manufacturer, BigDecimal price, String bodyType, int count) {
//        repository.save(new Auto(model, manufacturer, price, bodyType, count));
//    }

    public void create(Auto auto) {
        repository.save(auto);
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

    public Auto create() {
        return new Auto(
                "Model-" + RANDOM.nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                "Model-" + RANDOM.nextInt(1000), RANDOM.nextInt(1, 20)
        );
    }

    public Auto createAndSave() {
        final Auto auto = create();
        repository.save(auto);
        LOGGER.debug("Created auto {}", auto.getId());
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
        System.out.println("Sum of all prices = " + getSumAllPrices());
    }

    public void findAutoByIdAndPrint(String id) {
        Optional<Auto> foundAuto = repository.findById(id);
        foundAuto.ifPresent(System.out::println);
    }

//    public void findAutoByPriceOrPrintNew(BigDecimal price) {
//        Auto foundAuto = repository.findByPrice(price).orElse(new Auto("Model-" +RANDOM.nextInt(1000),
//                getRandomManufacturer(),
//                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
//                "Model-" + RANDOM.nextInt(1000), RANDOM.nextInt(1, 10)));
//        System.out.println(foundAuto);
//    }

    public Auto findAutoByPriceOrCreateNew(BigDecimal price) {
        Optional<Auto> foundAuto = Optional.of(repository.findByPrice(price).orElseGet(this::create));
        return foundAuto.get();
    }

    public String findAutoByPriceAndReturnId(BigDecimal price) {
        return repository.findByPrice(price).map(auto -> auto.getId()).orElse(String.valueOf(0));
    }

//    public Auto findAutoByPriceOrReturnNew(BigDecimal price) {
//        Optional<Auto> foundAuto = repository.findByPrice(price).or(() -> Optional.of(new Auto("Model-" + RANDOM.nextInt(1000),
//                getRandomManufacturer(),
//                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
//                "Model-" + RANDOM.nextInt(1000), RANDOM.nextInt(1, 10))));
//        return foundAuto.get();
//    }

    public Auto findAutoByIdAndFilter(String id, BigDecimal price) throws Exception {
        Optional<Auto> foundAuto;
        foundAuto = Optional.of(repository.findById(id).filter(auto -> auto.getPrice().compareTo(price) == 1)
                .orElseThrow(() -> new Exception("Filter exception")));
        return foundAuto.get();
    }
}
