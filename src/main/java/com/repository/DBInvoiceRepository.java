package com.repository;

import com.config.JDBCConfig;
import com.model.Auto;
import com.model.Bus;
import com.model.Invoice;
import com.model.Truck;
import com.util.VehicleFactory;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBInvoiceRepository {

    private static DBInvoiceRepository instance;

    private final Connection connection;

    private DBInvoiceRepository() {
        connection = JDBCConfig.getConnection();
    }

    public static DBInvoiceRepository getInstance() {
        if (instance == null) {
            instance = new DBInvoiceRepository();
        }
        return instance;
    }

    public List<Invoice> getInvoicesExpensiveThan(BigDecimal boundPrice) {
        final List<Invoice> result = new ArrayList<>();
        final String invoiceSql = "SELECT * FROM " +
                "(SELECT V.invoice_id, SUM(V.price) as invoice_price " +
                "FROM (SELECT A.invoice_id, A.price " +
                "FROM public.autos as A " +
                "UNION " +
                "SELECT B.invoice_id, B.price " +
                "FROM public.buses as B " +
                "UNION " +
                "SELECT T.invoice_id, T. price " +
                "FROM public.trucks as T) as V " +
                "WHERE V.invoice_id is not null " +
                "GROUP BY V.Invoice_id " +
                "HAVING SUM(V.price) > ?) as S " +
                "INNER JOIN public.invoices " +
                "ON invoices.invoice_id = S.invoice_id;";
        try (final PreparedStatement preparedStatement = connection.prepareStatement(invoiceSql)) {
            preparedStatement.setBigDecimal(1, boundPrice);
            final ResultSet invoiceResultSet = preparedStatement.executeQuery();
            while (invoiceResultSet.next()) {
                List<Auto> autos =
                        VehicleFactory.findAutoByInvoice(invoiceResultSet.getString("invoice_id")).get();
                List<Bus> buses =
                        VehicleFactory.findBusByInvoice(invoiceResultSet.getString("invoice_id")).get();
                List<Truck> trucks =
                        VehicleFactory.findTruckByInvoice(invoiceResultSet.getString("invoice_id")).get();
                result.add(new Invoice(invoiceResultSet.getString("invoice_id"),
                        invoiceResultSet.getDate("created"),
                        autos,
                        buses,
                        trucks));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @SneakyThrows
    public boolean save(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice must not be null");
        }

        List<Auto> autos = invoice.getAutos();
        List<Bus> buses = invoice.getBuses();
        List<Truck> trucks = invoice.getTrucks();

        final String invoiceSql = "INSERT INTO public.invoices(invoice_id, created)" +
                "VALUES (?, ?);";


        try (final PreparedStatement invoicePreparedStatement = connection.prepareStatement(invoiceSql)) {
            connection.setAutoCommit(false);
            mapInvoiceToRow(invoicePreparedStatement, invoice);
            invoicePreparedStatement.execute();
            String invoiceVehicleSql = null;
            PreparedStatement invoiceVehiclePreparedStatement = null;
            if (!autos.isEmpty()) {
                for (Auto auto : autos) {
                    invoiceVehicleSql = "UPDATE public.autos " +
                            "SET invoice_id = ?" +
                            "WHERE id = ?";
                    invoiceVehiclePreparedStatement = connection.prepareStatement(invoiceVehicleSql);
                    invoiceVehiclePreparedStatement.setString(1, invoice.getId());
                    invoiceVehiclePreparedStatement.setString(2, auto.getId());
                    invoiceVehiclePreparedStatement.executeUpdate();
                }
            }
            if (!buses.isEmpty()) {
                for (Bus bus : buses) {
                    invoiceVehicleSql = "UPDATE public.buses " +
                            "SET invoice_id = ?" +
                            "WHERE bus_id = ?";
                    invoiceVehiclePreparedStatement = connection.prepareStatement(invoiceVehicleSql);
                    invoiceVehiclePreparedStatement.setString(1, invoice.getId());
                    invoiceVehiclePreparedStatement.setString(2, bus.getId());
                    invoiceVehiclePreparedStatement.executeUpdate();
                }
            }
            if (!trucks.isEmpty()) {
                for (Truck truck : trucks) {
                    invoiceVehicleSql = "UPDATE public.trucks " +
                            "SET invoice_id = ?" +
                            "WHERE truck_id = ?";
                    invoiceVehiclePreparedStatement = connection.prepareStatement(invoiceVehicleSql);
                    invoiceVehiclePreparedStatement.setString(1, invoice.getId());
                    invoiceVehiclePreparedStatement.setString(2, truck.getId());
                    invoiceVehiclePreparedStatement.executeUpdate();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        }
        connection.setAutoCommit(true);
        return true;
    }

    @SneakyThrows
    private void mapInvoiceToRow(PreparedStatement preparedStatement, Invoice invoice) {
        preparedStatement.setString(1, invoice.getId());
        preparedStatement.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
    }

    public List<Invoice> getAll() {
        final List<Invoice> result = new ArrayList<>();
        try (final Statement statement = connection.createStatement()) {
            final ResultSet invoiceResultSet = statement.executeQuery("SELECT * " +
                    "FROM public.invoices as I");
            while (invoiceResultSet.next()) {
                List<Auto> autos =
                        VehicleFactory.findAutoByInvoice(invoiceResultSet.getString("invoice_id")).get();
                List<Bus> buses =
                        VehicleFactory.findBusByInvoice(invoiceResultSet.getString("invoice_id")).get();
                List<Truck> trucks =
                        VehicleFactory.findTruckByInvoice(invoiceResultSet.getString("invoice_id")).get();
                result.add(new Invoice(invoiceResultSet.getString("invoice_id"),
                        invoiceResultSet.getDate("created"),
                        autos,
                        buses,
                        trucks));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public boolean deleteInvoiceById(String id) {
        final String invoiceSql = "DELETE " +
                "FROM public.invoices " +
                "WHERE invoice_id = ?";
        final String autoSql = "UPDATE public.autos " +
                "SET invoice_id = null " +
                "WHERE invoice_id = ?";
        final String busSql = "UPDATE public.buses " +
                "SET invoice_id = null " +
                "WHERE invoice_id = ?";
        final String truckSql = "UPDATE public.trucks " +
                "SET invoice_id = null " +
                "WHERE invoice_id = ?";
        try (final PreparedStatement invoicePreparedStatement = connection.prepareStatement(invoiceSql);
             final PreparedStatement autoPreparedStatement = connection.prepareStatement(autoSql);
             final PreparedStatement busPreparedStatement = connection.prepareStatement(busSql);
             final PreparedStatement truckPreparedStatement = connection.prepareStatement(truckSql)) {
            connection.setAutoCommit(false);
            invoicePreparedStatement.execute();
            autoPreparedStatement.executeUpdate();
            busPreparedStatement.executeUpdate();
            truckPreparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void clear() {
        final String invoiceSql = "DELETE " +
                "FROM public.invoices ";
        final String autoSql = "UPDATE public.autos " +
                "SET invoice_id = null ";
        final String busSql = "UPDATE public.buses " +
                "SET invoice_id = null ";
        final String truckSql = "UPDATE public.trucks " +
                "SET invoice_id = null ";
        try (final PreparedStatement invoicePreparedStatement = connection.prepareStatement(invoiceSql);
             final PreparedStatement autoPreparedStatement = connection.prepareStatement(autoSql);
             final PreparedStatement busPreparedStatement = connection.prepareStatement(busSql);
             final PreparedStatement truckPreparedStatement = connection.prepareStatement(truckSql)) {
            connection.setAutoCommit(false);
            autoPreparedStatement.executeUpdate();
            busPreparedStatement.executeUpdate();
            truckPreparedStatement.executeUpdate();
            invoicePreparedStatement.execute();
            connection.commit();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public int getNumberOfInvoices() {
        final Statement statement = connection.createStatement();
        final ResultSet resultSet = statement.executeQuery("SELECT COUNT (public.invoices.invoice_id) " +
                "FROM public.invoices;");
        resultSet.next();
        return resultSet.getInt("count");
    }

    @SneakyThrows
    public boolean changeInvoiceDate(String invoiceId, String date) {
        String sql = "UPDATE public.invoices " +
                "SET created = ? " +
                "WHERE invoice_id = ?";
        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDate(1, Date.valueOf(date));
        preparedStatement.setString(2, invoiceId);
        return preparedStatement.executeUpdate() > 0;
    }

    @SneakyThrows
    public Map<BigDecimal, Integer> getInvoicesGroupedByPrice() {
        Statement statement = connection.createStatement();
        final ResultSet resultSet = statement.executeQuery("SELECT SS.invoice_price, COUNT(SS.invoice_id) FROM\n" +
                "(SELECT S.invoice_id, S.invoice_price FROM\n" +
                "(SELECT V.invoice_id, SUM(V.price) as invoice_price \n" +
                "FROM (SELECT A.invoice_id, A.price\n" +
                "FROM public.autos as A\n" +
                "UNION\n" +
                "SELECT B.invoice_id, B.price\n" +
                "FROM public.buses as B\n" +
                "UNION\n" +
                "SELECT T.invoice_id, T. price\n" +
                "FROM public.trucks as T) as V\n" +
                "WHERE V.invoice_id is not null\n" +
                "GROUP BY V.Invoice_id\n" +
                "HAVING SUM(V.price) > 0) as S\n" +
                "INNER JOIN public.invoices\n" +
                "ON invoices.invoice_id = S.invoice_id) as SS\n" +
                "GROUP BY SS.invoice_price;");
        Map<BigDecimal, Integer> invoices = new HashMap<>();
        while (resultSet.next()) {
            invoices.put(resultSet.getBigDecimal("invoice_price"),
                    resultSet.getInt("count"));
        }
        return invoices;
    }
}
