package com.service;

import com.model.Invoice;
import com.model.Vehicle;
import com.repository.MongoInvoiceRepository;
import com.util.VehicleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class InvoiceService {

    private static final Random RANDOM = new Random();
    private static InvoiceService instance;
    private final MongoInvoiceRepository repository;
    protected static final Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);

    public InvoiceService(MongoInvoiceRepository repository) {
        this.repository = repository;
    }

//    public static InvoiceService getInstance() {
//        if (instance == null) {
//            instance = new InvoiceService(DBInvoiceRepository.getInstance());
//        }
//        return instance;
//    }

    public Invoice saveRandomInvoice(List<Vehicle> vehicles) {
        final Invoice invoice = new Invoice();
        invoice.setVehiclesId(vehicles.stream().map(Vehicle::getId).toList());
        invoice.setPrice(vehicles.stream().map(Vehicle::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        repository.save(invoice);
        LOGGER.debug("Created invoice {}", invoice.getId());
        return invoice;
    }

    public void saveRandomInvoices(int numberOfVehiclesInInvoice, int numberOfInvoices) {
        List<Vehicle> allVehicles = VehicleFactory.getAll();
        List<Vehicle> invoiceVehicles = new LinkedList<>();
        for (int j = 0; j < numberOfInvoices; j++) {
            for (int i = 0; i < numberOfVehiclesInInvoice; i++) {
                int choseAuto = RANDOM.nextInt(0, allVehicles.size() - 1);
                invoiceVehicles.add(allVehicles.get(choseAuto));
                allVehicles.remove(choseAuto);
            }
            saveRandomInvoice(invoiceVehicles);
            invoiceVehicles.clear();
        }
    }


    public List<Invoice> getAll() {
        return repository.getAll();
    }

//            public boolean deleteInvoiceById (String invoiceId){
//                return repository.deleteInvoiceById(invoiceId);
//            }

    public void clearInvoices() {
        repository.clear();
    }

    public List<Invoice> getInvoicesExpensiveThan(BigDecimal boundPrice) {
        List<Invoice> result = new ArrayList<>();
        result.addAll(repository.getInvoicesExpensiveThan(boundPrice));
        return result;
    }

    public int getNumberOfInvoices() {
        return repository.getNumberOfInvoices();
    }

    public void changeInvoiceDate(String invoiceId, String date) {
        repository.changeInvoiceDate(invoiceId, date);
    }

    public void printInvoicesGroupedByPrice() {
        repository.getInvoicesGroupedByPrice().forEach(System.out::println);
    }
}
