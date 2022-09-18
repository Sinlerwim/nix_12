package com.service;

import com.model.Engine;
import com.repository.DBEngineRepository;

import java.util.List;
import java.util.Random;

public class EngineService {

    private static EngineService instance;

    private static final DBEngineRepository REPOSITORY = new DBEngineRepository();

    private static final Random RANDOM = new Random();

    public void initEnginesDB(int numberOfEngines) {
        List<Engine> engines = REPOSITORY.createRandomEngines(numberOfEngines);
        REPOSITORY.save(engines);
    }

    public Engine getRandomEngineFromDB() {
        List<Engine> engines = REPOSITORY.getAll();
        return engines.get(RANDOM.nextInt(0, engines.size() - 1));
    }

    public Engine getOneRandomEngine() {
        return REPOSITORY.createOneRandomEngine();
    }
}
