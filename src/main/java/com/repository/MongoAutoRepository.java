package com.repository;

import com.model.Auto;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MongoAutoRepository extends CrudRepository<Auto> {

    private static final String COLLECTION_NAME = "Auto";

    private static SessionFactory sessionFactory;

    public MongoAutoRepository(MongoDatabase db) {
        super(db, COLLECTION_NAME);
    }


    @Override
    public List<Auto> getAll() {
        return collection.find()
                .map(item -> {
                    Auto auto = gson.fromJson(item.toJson(), Auto.class);
                    auto.setId(item.get("_id", ObjectId.class).toString());
                    return auto;
                })
                .into(new ArrayList<>());
    }

    @Override
    public void save(Auto auto) {
        auto.setDate(LocalDateTime.now());
        collection.insertOne(mapFrom(auto));

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
//        Session session = sessionFactory.openSession();
//        session.createQuery("delete from Auto").executeUpdate();
//        session.close();
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