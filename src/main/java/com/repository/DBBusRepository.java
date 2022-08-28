package com.repository;

import com.config.JDBCConfig;
import com.model.Auto;
import com.model.Bus;
import com.model.Engine;
import com.model.Manufacturer;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DBBusRepository implements CrudRepository<Bus> {

    private static DBBusRepository instance;

    private final Connection connection;

    private DBBusRepository() {
        connection = JDBCConfig.getConnection();
    }

    public static DBBusRepository getInstance() {
        if (instance == null) {
            instance = new DBBusRepository();
        }
        return instance;
    }

    @Override
    public Optional<Bus> findById(String id) {
        final String sql = "SELECT B.*, E.brand, E.volume " +
                "FROM public.buses as B " +
                "INNER JOIN public.engines as E " +
                "ON B.engine_id = E.engine_id ";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapRowToBus(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<List<Bus>> findByInvoice(String id) {
        final String sql = "SELECT B.*, E.brand, E.volume " +
                "FROM public.buses as B " +
                "INNER JOIN public.engines as E " +
                "ON B.engine_id = E.engine_id " +
                "WHERE B.invoice_id = ?";
        List<Bus> result = new ArrayList<>();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(mapRowToBus(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(result);
    }

    @Override
    public List<Bus> getAll() {
        final List<Bus> result = new ArrayList<>();
        try (final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT B.*, E.brand, E.volume " +
                    "FROM public.buses as B " +
                    "INNER JOIN public.engines as E " +
                    "ON B.engine_id = E.engine_id ");
            while (resultSet.next()) {
                result.add(mapRowToBus(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean save(Bus bus) {
        if (bus == null) {
            throw new IllegalArgumentException("Bus must not be null");
        }
        if (bus.getPrice().equals(BigDecimal.ZERO)) {
            bus.setPrice(BigDecimal.valueOf(-1));
        }
        final String sql = "INSERT INTO public.buses(bus_id, model, manufacturer, price, " +
                "number_of_seats, count, created, engine_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            bus.setEngine(getRandomEngine());
            mapBusToRow(preparedStatement, bus);
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
    private void mapBusToRow(final PreparedStatement preparedStatement, final Bus bus) {
        preparedStatement.setString(1, bus.getId());
        preparedStatement.setString(2, bus.getModel());
        preparedStatement.setString(3, bus.getManufacturer().toString());
        preparedStatement.setBigDecimal(4, bus.getPrice());
        preparedStatement.setInt(5, bus.getNumberOfSeats());
        preparedStatement.setInt(6, bus.getCount());
        preparedStatement.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
        preparedStatement.setString(8, bus.getEngine().getId());
    }

    @SneakyThrows
    private Bus mapRowToBus(final ResultSet resultSet) {
        return new Bus(
                resultSet.getString("bus_id"),
                resultSet.getString("model"),
                Manufacturer.valueOf(resultSet.getString("manufacturer")),
                resultSet.getBigDecimal("price"),
                resultSet.getInt("number_of_seats"),
                resultSet.getInt("count"),
                resultSet.getDate("created"),
                new Engine(
                        resultSet.getString("engine_id"),
                        resultSet.getInt("volume"),
                        resultSet.getString("brand")
                ));
    }

    public boolean saveAll(List<Bus> buses) {
        if (buses == null) {
            return false;
        }
        buses.forEach(this::save);
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
    public Optional<Bus> findByPrice(BigDecimal price) {
        return Optional.empty();
    }

    public void clear() {
        final String sql = "DELETE FROM public.buses";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}