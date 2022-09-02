package com.repository;

import com.model.Auto;
import com.model.Engine;
import com.model.Truck;
import com.util.HibernateFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DBTruckRepository implements CrudRepository<Truck> {

    private static DBTruckRepository instance;

    private static SessionFactory sessionFactory;

    private DBTruckRepository() {
        sessionFactory = HibernateFactoryUtil.getSessionFactory();
    }

    public static DBTruckRepository getInstance() {
        if (instance == null) {
            instance = new DBTruckRepository();
        }
        return instance;
    }

    @Override
    public List<Truck> getAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM Truck ", Truck.class).list();
    }

    @Override
    public void save(Truck truck) {
        truck.setDate(new java.sql.Date(new java.util.Date().getTime()));
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(truck);
        session.getTransaction().commit();
        session.close();
    }

    public Engine getRandomEngine() {
        Session session = sessionFactory.openSession();
        List <Engine> engines = session.createQuery("FROM Engine", Engine.class).list();
        return engines.get(new Random().nextInt(0, engines.size() - 1));
    }

    @Override
    public boolean update(String id, BigDecimal price) {
        return false;
    }

    @Override
    public Optional<Truck> findByPrice(BigDecimal price) {
        return Optional.empty();
    }

    public void clear() {
        Session session = sessionFactory.openSession();
        session.createQuery("delete from Truck ").executeUpdate();
        session.close();
    }

    @Override
    public Optional<Truck> findById(String id) {
//        final String sql = "SELECT T.*, E.brand, E.volume " +
//                "FROM public.trucks as T " +
//                "INNER JOIN public.engines as E " +
//                "ON E.engine_id = T.engine_id " +
//                "WHERE T.invoice_id = ?";
//        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, id);
//            final ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                return Optional.of(mapRowToTruck(resultSet));
//            } else {
        return Optional.empty();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public Optional<List<Truck>> findByInvoice(String id) {
//        final String sql = "SELECT T.*, E.brand, E.volume " +
//                "FROM public.trucks as T " +
//                "INNER JOIN public.engines as E " +
//                "ON E.engine_id = T.engine_id " +
//                "WHERE T.invoice_id = ?";
//        List<Truck> result = new ArrayList<>();
//        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, id);
//            final ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                result.add(mapRowToTruck(resultSet));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.of(result);
        return Optional.empty();
    }

    public boolean update(Auto auto) {
        /*final Optional<Auto> optionalAuto = findById(auto.getId());
        if (optionalAuto.isPresent()) {
            optionalAuto.ifPresent(founded -> AutoCopy.copy(auto, founded));
            return true;
        }*/
        return false;
    }

    @Override
    public boolean delete(String id) {
       /* final Iterator<Auto> iterator = autos.iterator();
        while (iterator.hasNext()) {
            final Auto auto = iterator.next();
            if (auto.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }*/
        return false;
    }
}