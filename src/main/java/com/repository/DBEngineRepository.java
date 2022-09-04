package com.repository;

import com.model.Engine;
import com.util.HibernateFactoryUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DBEngineRepository {

    private static final Random RANDOM = new Random();

    private static DBEngineRepository instance;

    private static SessionFactory sessionFactory;

    private DBEngineRepository() {
        sessionFactory = HibernateFactoryUtil.getSessionFactory();
    }

    public static DBEngineRepository getInstance() {
        if (instance == null) {
            instance = new DBEngineRepository();
        }
        return instance;
    }

    public List<Engine> createRandomEngines(int numberOfEngines) {
        List<Engine> engines = new ArrayList<>();
        for(int i = 0; i < numberOfEngines; i++) {
            Engine engine = new Engine();
            engine.setVolume(RANDOM.nextInt(998, 4998));
            engine.setBrand(RandomStringUtils.random(5,true,false));
            engines.add(engine);
        }
        return engines;
    }

    public void save(List<Engine> engines) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        engines.forEach(session::save);
        session.getTransaction().commit();
        session.close();
    }

    public List<Engine> getAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM Engine ", Engine.class).list();
    }
}
