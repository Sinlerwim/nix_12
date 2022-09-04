package com.command;

//import com.model.Invoice;
//import com.model.VehicleType;
//import com.service.InvoiceService;
//import com.util.UserInputUtil;
//import com.util.VehicleFactory;
//
//import java.math.BigDecimal;
//import java.util.List;

import com.model.Invoice;
import com.service.EngineService;
import com.service.InvoiceService;
import com.util.UserInputUtil;
import com.util.VehicleFactory;

import java.math.BigDecimal;
import java.util.List;

public class DataBase implements Command {
    private static final EngineService ENGINE_SERVICE = new EngineService();

    private static final InvoiceService INVOICE_SERVICE = InvoiceService.getInstance();
    @Override
    public void execute() {
        ENGINE_SERVICE.initEnginesDB(5);
        VehicleFactory.saveRandomVehicles(50);
        INVOICE_SERVICE.saveRandomInvoices(3, 5);
        System.out.println("Created invoices:");
        List<Invoice> Invoices = INVOICE_SERVICE.getAll();
        System.out.println("Amount - " + INVOICE_SERVICE.getNumberOfInvoices());
        System.out.println(Invoices.size());
        Invoices.forEach(System.out::println);
        BigDecimal boundPrice = new BigDecimal(UserInputUtil.getUserInput("Print the bound price"));
        System.out.println("Result:");
        INVOICE_SERVICE.getInvoicesExpensiveThan(boundPrice).forEach(System.out::println);
        String invoiceId = UserInputUtil.getUserInput("Print id of invoice you want to change:");
        String date = UserInputUtil.getUserInput("Print new date:");
        INVOICE_SERVICE.changeInvoiceDate(invoiceId, date);
        System.out.println("Invoices:");
        INVOICE_SERVICE.getAll().forEach(System.out::println);
        System.out.println("Invoices grouped by price:");
        INVOICE_SERVICE.printInvoicesGroupedByPrice();
    }
}
