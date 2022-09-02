package com.repository;

import com.model.Auto;
import com.model.Bus;
import com.model.Engine;
import com.util.HibernateFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DBBusRepository implements CrudRepository<Bus> {

    private static DBBusRepository instance;

    private static SessionFactory sessionFactory;

    private DBBusRepository() {
        sessionFactory = HibernateFactoryUtil.getSessionFactory();
    }

    public static DBBusRepository getInstance() {
        if (instance == null) {
            instance = new DBBusRepository();
        }
        return instance;
    }

    @Override
    public List<Bus> getAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM Bus ", Bus.class).list();
    }

    @Override
    public void save(Bus bus) {
        bus.setDate(new java.sql.Date(new java.util.Date().getTime()));
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(bus);
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
    public Optional<Bus> findByPrice(BigDecimal price) {
        return Optional.empty();
    }

    public void clear() {
        Session session = sessionFactory.openSession();
        session.createQuery("delete from Auto").executeUpdate();
        session.close();
    }

    @Override
    public Optional<Bus> findById(String id) {
//        final String sql = "SELECT B.*, E.brand, E.volume " +
//                "FROM public.buses as B " +
//                "INNER JOIN public.engines as E " +
//                "ON B.engine_id = E.engine_id ";
//        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, id);
//            final ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                return Optional.of(mapRowToBus(resultSet));
//            } else {
//                return Optional.empty();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return Optional.empty();
    }

    public Optional<List<Bus>> findByInvoice(String id) {
//        final String sql = "SELECT B.*, E.brand, E.volume " +
//                "FROM public.buses as B " +
//                "INNER JOIN public.engines as E " +
//                "ON B.engine_id = E.engine_id " +
//                "WHERE B.invoice_id = ?";
//        List<Bus> result = new ArrayList<>();
//        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, id);
//            final ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                result.add(mapRowToBus(resultSet));
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