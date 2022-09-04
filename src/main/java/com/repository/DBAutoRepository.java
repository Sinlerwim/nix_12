package com.repository;

import com.config.JDBCConfig;
import com.model.Auto;
import com.model.Engine;
import com.model.Manufacturer;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DBAutoRepository implements CrudRepository<Auto> {

    private static DBAutoRepository instance;

    private final Connection connection;

    private DBAutoRepository() {
        connection = JDBCConfig.getConnection();
    }

    public static DBAutoRepository getInstance() {
        if (instance == null) {
            instance = new DBAutoRepository();
        }
        return instance;
    }

    @Override
    public Optional<Auto> findById(String id) {
        final String sql = "SELECT A.*, E.brand, E.volume " +
                "FROM public.autos as A " +
                "INNER JOIN public.engines as E " +
                "ON E.engine_id = A.engine_id";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapRowToAuto(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<List<Auto>> findByInvoice(String id) {
        final String sql = "SELECT A.*, E.brand, E.volume " +
                "FROM public.autos as A " +
                "INNER JOIN public.engines as E " +
                "ON E.engine_id = A.engine_id " +
                "WHERE A.invoice_id = ?";
        List<Auto> result = new ArrayList<>();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(mapRowToAuto(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(result);
    }

    @Override
    public List<Auto> getAll() {
        final List<Auto> result = new ArrayList<>();
        try (final Statement statement = connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT A.*, E.brand, E.volume " +
                    "FROM public.autos as A " +
                    "INNER JOIN public.engines as E " +
                    "ON E.engine_id = A.engine_id");
            while (resultSet.next()) {
                result.add(mapRowToAuto(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean save(Auto auto) {
        if (auto == null) {
            throw new IllegalArgumentException("Auto must not be null");
        }
        if (auto.getPrice().equals(BigDecimal.ZERO)) {
            auto.setPrice(BigDecimal.valueOf(-1));
        }
        final String sql = "INSERT INTO public.autos(id, model, manufacturer, price, " +
                "body_type, count, created, engine_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            auto.setEngine(getRandomEngine());
            mapAutoToRow(preparedStatement, auto);
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
    private void mapAutoToRow(final PreparedStatement preparedStatement, final Auto auto) {
        preparedStatement.setString(1, auto.getId());
        preparedStatement.setString(2, auto.getModel());
        preparedStatement.setString(3, auto.getManufacturer().toString());
        preparedStatement.setBigDecimal(4, auto.getPrice());
        preparedStatement.setString(5, auto.getBodyType());
        preparedStatement.setInt(6, auto.getCount());
        preparedStatement.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
        preparedStatement.setString(8, auto.getEngine().getId());
    }

    @SneakyThrows
    private Auto mapRowToAuto(final ResultSet resultSet) {
        return new Auto(
                resultSet.getString("id"),
                resultSet.getString("model"),
                Manufacturer.valueOf(resultSet.getString("manufacturer")),
                resultSet.getBigDecimal("price"),
                resultSet.getString("body_type"),
                resultSet.getInt("count"),
                resultSet.getDate("created"),
                new Engine(
                        resultSet.getString("engine_id"),
                        resultSet.getInt("volume"),
                        resultSet.getString("brand")
                ));
    }

    public boolean saveAll(List<Auto> auto) {
        if (auto == null) {
            return false;
        }
        auto.forEach(this::save);
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
    public Optional<Auto> findByPrice(BigDecimal price) {
        return Optional.empty();
    }

    public void clear() {
        final String sql = "DELETE FROM public.autos";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}