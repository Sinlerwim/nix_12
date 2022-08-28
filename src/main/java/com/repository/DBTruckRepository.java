package com.repository;

import com.config.JDBCConfig;
import com.model.Auto;
import com.model.Engine;
import com.model.Manufacturer;
import com.model.Truck;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DBTruckRepository implements CrudRepository<Truck> {

    private static DBTruckRepository instance;

    private final Connection connection;

    private DBTruckRepository() {
        connection = JDBCConfig.getConnection();
    }

    public static DBTruckRepository getInstance() {
        if (instance == null) {
            instance = new DBTruckRepository();
        }
        return instance;
    }

    @Override
    public Optional<Truck> findById(String id) {
        final String sql = "SELECT T.*, E.brand, E.volume " +
                "FROM public.trucks as T " +
                "INNER JOIN public.engines as E " +
                "ON E.engine_id = T.engine_id " +
                "WHERE T.invoice_id = ?";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapRowToTruck(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<List<Truck>> findByInvoice(String id) {
        final String sql = "SELECT T.*, E.brand, E.volume " +
                "FROM public.trucks as T " +
                "INNER JOIN public.engines as E " +
                "ON E.engine_id = T.engine_id " +
                "WHERE T.invoice_id = ?";
        List<Truck> result = new ArrayList<>();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(mapRowToTruck(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(result);
    }

    @Override
    public List<Truck> getAll() {
        final List<Truck> result = new ArrayList<>();
        try (final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT T.*, E.brand, E.volume " +
                    "FROM public.trucks as T " +
                    "INNER JOIN public.engines as E " +
                    "ON E.engine_id = T.engine_id");
            while (resultSet.next()) {
                result.add(mapRowToTruck(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean save(Truck truck) {
        if (truck == null) {
            throw new IllegalArgumentException("Truck must not be null");
        }
        if (truck.getPrice().equals(BigDecimal.ZERO)) {
            truck.setPrice(BigDecimal.valueOf(-1));
        }
        final String sql = "INSERT INTO public.trucks(truck_id, model, manufacturer, price, " +
                "allow_trailer_weight, count, created, engine_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            truck.setEngine(getRandomEngine());
            mapTruckToRow(preparedStatement, truck);
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Engine getRandomEngine() {
        List<Engine> engines = new ArrayList<>();
        try (final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM public.engines");
            while (resultSet.next()) {
                engines.add(mapRowToEngine(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return engines.get(new Random().nextInt(0, engines.size() - 1));
    }

    @SneakyThrows
    private Engine mapRowToEngine(ResultSet resultSet) {
        return new Engine(
                resultSet.getString("engine_id"),
                resultSet.getInt("volume"),
                resultSet.getString("brand")
        );
    }

    @Override
    public boolean update(String id, BigDecimal price) {
        return false;
    }

    @SneakyThrows
    private void mapTruckToRow(final PreparedStatement preparedStatement, final Truck Truck) {
        preparedStatement.setString(1, Truck.getId());
        preparedStatement.setString(2, Truck.getModel());
        preparedStatement.setString(3, Truck.getManufacturer().toString());
        preparedStatement.setBigDecimal(4, Truck.getPrice());
        preparedStatement.setInt(5, Truck.getAllowTrailerWeight());
        preparedStatement.setInt(6, Truck.getCount());
        preparedStatement.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
        preparedStatement.setString(8, Truck.getEngine().getId());
    }

    @SneakyThrows
    private Truck mapRowToTruck(final ResultSet resultSet) {
        return new Truck(
                resultSet.getString("truck_id"),
                resultSet.getString("model"),
                Manufacturer.valueOf(resultSet.getString("manufacturer")),
                resultSet.getBigDecimal("price"),
                resultSet.getInt("allow_trailer_weight"),
                resultSet.getInt("count"),
                resultSet.getDate("created"),
                new Engine(
                        resultSet.getString("engine_id"),
                        resultSet.getInt("volume"),
                        resultSet.getString("brand")
                ));
    }

    public boolean saveAll(List<Truck> trucks) {
        if (trucks == null) {
            return false;
        }
        trucks.forEach(this::save);
        return true;
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

    @Override
    public Optional<Truck> findByPrice(BigDecimal price) {
        return Optional.empty();
    }

    public void clear() {
        final String sql = "DELETE FROM public.trucks";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}