package com.repository;

import com.model.Auto;
import com.model.Truck;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MongoTruckRepository extends CrudRepository<Truck> {

    private static final String COLLECTION_NAME = "Truck";

//    private static SessionFactory sessionFactory;

    public MongoTruckRepository(MongoDatabase db) {
        super(db, COLLECTION_NAME);
    }

    @Override
    public List<Truck> getAll() {
        return collection.find()
                .map(item -> {
                    Truck truck = gson.fromJson(item.toJson(), Truck.class);
                    truck.setId(item.get("_id", ObjectId.class).toString());
                    return truck;
                })
                .into(new ArrayList<>());
    }

    @Override
    public void save(Truck truck) {
        truck.setDate(LocalDateTime.now());
        collection.insertOne(mapFrom(truck));
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
//        Session session = sessionFactory.openSession();
//        session.createQuery("delete from Truck ").executeUpdate();
//        session.close();
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