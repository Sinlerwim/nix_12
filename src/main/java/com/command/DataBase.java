package com.command;

import com.model.Invoice;
import com.model.VehicleType;
import com.service.InvoiceService;
import com.util.UserInputUtil;
import com.util.VehicleFactory;

import java.math.BigDecimal;
import java.util.List;

public class DataBase implements Command {
    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();
    @Override
    public void execute() {
        VehicleFactory.clearAll();
        INVOICE_SERVICE.clearInvoices();
        System.out.println("Creating 30 vehicles...");
        for (int i = 0; i < 30; i++) {
            VehicleFactory.buildAndSave(VehicleType.AUTO);
            VehicleFactory.buildAndSave(VehicleType.BUS);
            VehicleFactory.buildAndSave(VehicleType.TRUCK);
        }
        System.out.println("Creating 5 invoices with 3 cars inside...");
        INVOICE_SERVICE.createAndSave(3, 5);
        System.out.println("Created invoices:");
        List<Invoice> Invoices = INVOICE_SERVICE.getAll();
        System.out.println(Invoices.size());
        Invoices.forEach(System.out::println);
        BigDecimal boundPrice = new BigDecimal(UserInputUtil.getUserInput("Print the bound price"));
        INVOICE_SERVICE.getInvoicesExpensiveThan(boundPrice).forEach(System.out::println);
    }
}
