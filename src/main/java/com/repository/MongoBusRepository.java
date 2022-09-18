package com.repository;

import com.model.Auto;
import com.model.Bus;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MongoBusRepository extends CrudRepository<Bus> {

    private static final String COLLECTION_NAME = "Bus";

    public MongoBusRepository(MongoDatabase db) {
        super(db, COLLECTION_NAME);
    }

    @Override
    public List<Bus> getAll() {
        return collection.find()
                .map(item -> {
                    Bus bus = gson.fromJson(item.toJson(), Bus.class);
                    bus.setId(item.get("_id", ObjectId.class).toString());
                    return bus;
                })
                .into(new ArrayList<>());
    }

    @Override
    public void save(Bus bus) {
        bus.setDate(LocalDateTime.now());
        collection.insertOne(mapFrom(bus));
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
//        Session session = sessionFactory.openSession();
//        session.createQuery("delete from Auto").executeUpdate();
//        session.close();
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