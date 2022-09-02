package com.repository;

import com.model.Invoice;
import com.model.Vehicle;
import com.util.HibernateFactoryUtil;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public class DBInvoiceRepository {

    private static DBInvoiceRepository instance;

    private static SessionFactory sessionFactory;

    private DBInvoiceRepository() {
        sessionFactory = HibernateFactoryUtil.getSessionFactory();
    }

    public static DBInvoiceRepository getInstance() {
        if (instance == null) {
            instance = new DBInvoiceRepository();
        }
        return instance;
    }

    public List<Invoice> getInvoicesExpensiveThan(BigDecimal boundPrice) {
        Session session = sessionFactory.openSession();
        Query q = session.createNamedQuery("selectInvoiceExpensiveThat");
        q.setParameter("boundPrice", boundPrice);
        return q.getResultList();
    }

    @SneakyThrows
    public void save(Invoice invoice, List<Vehicle> vehicles) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice must not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        invoice.setCreated(LocalDateTime.now());
        session.save(invoice);
        vehicles.forEach(vehicle -> {
            vehicle.setInvoice(invoice);
            session.update(vehicle);
        });
        session.getTransaction().commit();
        session.close();
    }

    public List<Invoice> getAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM Invoice ", Invoice.class).list();
    }

    public boolean deleteInvoiceById(String id) {
//        final String invoiceSql = "DELETE " +
//                "FROM public.invoices " +
//                "WHERE invoice_id = ?";
//        final String autoSql = "UPDATE public.autos " +
//                "SET invoice_id = null " +
//                "WHERE invoice_id = ?";
//        final String busSql = "UPDATE public.buses " +
//                "SET invoice_id = null " +
//                "WHERE invoice_id = ?";
//        final String truckSql = "UPDATE public.trucks " +
//                "SET invoice_id = null " +
//                "WHERE invoice_id = ?";
//        try (final PreparedStatement invoicePreparedStatement = connection.prepareStatement(invoiceSql);
//             final PreparedStatement autoPreparedStatement = connection.prepareStatement(autoSql);
//             final PreparedStatement busPreparedStatement = connection.prepareStatement(busSql);
//             final PreparedStatement truckPreparedStatement = connection.prepareStatement(truckSql)) {
//            connection.setAutoCommit(false);
//            invoicePreparedStatement.execute();
//            autoPreparedStatement.executeUpdate();
//            busPreparedStatement.executeUpdate();
//            truckPreparedStatement.executeUpdate();
//            connection.commit();
//            connection.setAutoCommit(false);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return true;
    }

    public void clear() {
//        final String invoiceSql = "DELETE " +
//                "FROM public.invoices ";
//        final String autoSql = "UPDATE public.autos " +
//                "SET invoice_id = null ";
//        final String busSql = "UPDATE public.buses " +
//                "SET invoice_id = null ";
//        final String truckSql = "UPDATE public.trucks " +
//                "SET invoice_id = null ";
//        try (final PreparedStatement invoicePreparedStatement = connection.prepareStatement(invoiceSql);
//             final PreparedStatement autoPreparedStatement = connection.prepareStatement(autoSql);
//             final PreparedStatement busPreparedStatement = connection.prepareStatement(busSql);
//             final PreparedStatement truckPreparedStatement = connection.prepareStatement(truckSql)) {
//            connection.setAutoCommit(false);
//            autoPreparedStatement.executeUpdate();
//            busPreparedStatement.executeUpdate();
//            truckPreparedStatement.executeUpdate();
//            invoicePreparedStatement.execute();
//            connection.commit();
//            connection.setAutoCommit(false);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @SneakyThrows
    public int getNumberOfInvoices() {
        Session session = sessionFactory.openSession();
        return ((Long) session.createQuery("select count(*) from Invoice").getSingleResult()).intValue();
    }

    @SneakyThrows
    public void changeInvoiceDate(String invoiceId, String date) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Invoice invoice = session.get(Invoice.class, invoiceId);
        invoice.setCreated(LocalDateTime.parse(date));
        session.update(invoice);
        session.getTransaction().commit();
        session.close();
    }

    @SneakyThrows
    public List<Invoice> getInvoicesGroupedByPrice() {
        Session session = sessionFactory.openSession();
        Query q = session.createNamedQuery("invoicesGroupedBySum");
        return q.getResultList();
    }
}
