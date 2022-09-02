package com.repository;

import com.model.Auto;
import com.model.Engine;
import com.util.HibernateFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DBAutoRepository implements CrudRepository<Auto> {

    private static DBAutoRepository instance;

    private static SessionFactory sessionFactory;

    private DBAutoRepository() {
        sessionFactory = HibernateFactoryUtil.getSessionFactory();
    }

    public static DBAutoRepository getInstance() {
        if (instance == null) {
            instance = new DBAutoRepository();
        }
        return instance;
    }

    @Override
    public List<Auto> getAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM Auto", Auto.class).list();
    }

    @Override
    public void save(Auto auto) {
        auto.setDate(new java.sql.Date(new java.util.Date().getTime()));
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(auto);
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

    public void saveAll(List<Auto> autos) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        autos.forEach(session::save);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Optional<Auto> findByPrice(BigDecimal price) {
        return Optional.empty();
    }

    public void clear() {
        Session session = sessionFactory.openSession();
        session.createQuery("delete from Auto").executeUpdate();
        session.close();
    }

    @Override
    public Optional<Auto> findById(String id) {
//        final String sql = "SELECT A.*, E.brand, E.volume " +
//                "FROM public.autos as A " +
//                "INNER JOIN public.engines as E " +
//                "ON E.engine_id = A.engine_id";
//        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, id);
//            final ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                return Optional.of(mapRowToAuto(resultSet));
//            } else {
        return Optional.empty();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        session.createQuery("from")
//        session.getTransaction().commit();
//        session.close();
    }

    public Optional<List<Auto>> findByInvoice(String id) {
//        final String sql = "SELECT A.*, E.brand, E.volume " +
//                "FROM public.autos as A " +
//                "INNER JOIN public.engines as E " +
//                "ON E.engine_id = A.engine_id " +
//                "WHERE A.invoice_id = ?";
//        List<Auto> result = new ArrayList<>();
//        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setString(1, id);
//            final ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                result.add(mapRowToAuto(resultSet));
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