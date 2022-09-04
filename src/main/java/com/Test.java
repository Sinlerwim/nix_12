package com;

import com.config.JDBCConfig;
import com.service.InvoiceService;
import com.util.VehicleFactory;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Random;

public class Test {
    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();
    private static final Random RANDOM = new Random();


    @SneakyThrows
    public static void main(String[] args) {
        final Connection connection = JDBCConfig.getConnection();
        final Statement statement = connection.createStatement();
        final boolean select = statement.execute("SELECT * FROM public.autos");
        System.out.println("Result " + select);

//        final String sql = "SELECT * FROM public.\"Auto\"";
//        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("Before deleting:");
        VehicleFactory.printAll();
        System.out.println(INVOICE_SERVICE.getAll());

//        VehicleFactory.clearAll();
        System.out.println("After deleting:");
//        INVOICE_SERVICE.clearInvoices();
        System.out.println(INVOICE_SERVICE.getAll());
//        VehicleFactory.printAll();
//        VehicleFactory.buildAndSave(VehicleType.AUTO);
//        VehicleFactory.buildAndSave(VehicleType.AUTO);
//        VehicleFactory.buildAndSave(VehicleType.AUTO);
//        VehicleFactory.buildAndSave(VehicleType.BUS);
//        VehicleFactory.buildAndSave(VehicleType.BUS);
//        VehicleFactory.buildAndSave(VehicleType.BUS);
//        VehicleFactory.buildAndSave(VehicleType.TRUCK);
//        VehicleFactory.buildAndSave(VehicleType.TRUCK);
//        VehicleFactory.buildAndSave(VehicleType.TRUCK);
        System.out.println("After creating: ");
        VehicleFactory.printAll();
        System.out.println("Invoices:");
        System.out.println(INVOICE_SERVICE.getAll());
    }
}
