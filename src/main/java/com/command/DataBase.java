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
import com.mongodb.client.MongoDatabase;
import com.repository.MongoAutoRepository;
import com.repository.MongoBusRepository;
import com.repository.MongoInvoiceRepository;
import com.repository.MongoTruckRepository;
import com.service.*;
import com.util.MongoUtil;
import com.util.UserInputUtil;
import com.util.VehicleFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class DataBase implements Command {
    private static final Random RANDOM = new Random();

    private static final MongoDatabase db = MongoUtil.connect("NIX");

    private static final AutoService AUTO_SERVICE = new AutoService(new MongoAutoRepository(db));
    private static final BusService BUS_SERVICE = new BusService(new MongoBusRepository(db));
    private static final TruckService TRUCK_SERVICE = new TruckService(new MongoTruckRepository(db));

    private static final EngineService ENGINE_SERVICE = new EngineService();

    private static final InvoiceService INVOICE_SERVICE = new InvoiceService(new MongoInvoiceRepository(db));

    @Override
    public void execute() {
        db.drop();
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
