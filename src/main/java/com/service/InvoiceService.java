package com.service;

import com.model.Auto;
import com.model.Bus;
import com.model.Invoice;
import com.model.Truck;
import com.repository.DBInvoiceRepository;
import com.util.VehicleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InvoiceService {

    private static final Random RANDOM = new Random();
    private static InvoiceService instance;
    private final DBInvoiceRepository repository;
    protected static final Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);

    private InvoiceService(DBInvoiceRepository repository) {
        this.repository = repository;
    }

    public static InvoiceService getInstance() {
        if (instance == null) {
            instance = new InvoiceService(DBInvoiceRepository.getInstance());
        }
        return instance;
    }

    public Invoice createAndSave(List<Auto> autos, List<Bus> buses, List<Truck> trucks) {
        final Invoice invoice = new Invoice(autos, buses, trucks);
        repository.save(invoice);
        LOGGER.debug("Created invoice {}", invoice.getId());
        return invoice;
    }

    public void createAndSave(int numberOfVehiclesInInvoice, int numberOfInvoices) {
        final List<Auto> allAutos = VehicleFactory.getAllAutos();
        final List<Bus> allBuses = VehicleFactory.getAllBuses();
        final List<Truck> allTrucks = VehicleFactory.getAllTrucks();
        List<Auto> autos = new ArrayList<>();
        List<Bus> buses = new ArrayList<>();
        List<Truck> trucks = new ArrayList<>();
        for (int j = 0; j < numberOfInvoices; j++) {
            for (int i = 0; i < numberOfVehiclesInInvoice; i++) {
                switch (RANDOM.nextInt(1, 3)) {
                    case 1 -> {
                        int choseAuto = RANDOM.nextInt(0, allAutos.size() - 1);
                        autos.add(allAutos.get(choseAuto));
                        allAutos.remove(choseAuto);
                    }
                    case 2 -> {
                        int choseBus = RANDOM.nextInt(0, allBuses.size() - 1);
                        buses.add(allBuses.get(choseBus));
                        allBuses.remove(choseBus);
                    }
                    case 3 -> {
                        int choseTruck = RANDOM.nextInt(0, allTrucks.size() - 1);
                        trucks.add(allTrucks.get(choseTruck));
                        allTrucks.remove(choseTruck);
                    }
                }
            }
            createAndSave(autos, buses, trucks);
            autos.clear();
            buses.clear();
            trucks.clear();
        }
    }


    public List<Invoice> getAll() {
        return repository.getAll();
    }

    public boolean deleteInvoiceById(String invoiceId) {
        return repository.deleteInvoiceById(invoiceId);
    }

    public void clearInvoices() {
        repository.clear();
    }

    public List<Invoice> getInvoicesExpensiveThan(BigDecimal boundPrice) {
        List<Invoice> result = new ArrayList<>();
        result.addAll(repository.getInvoicesExpensiveThan(boundPrice));
        return result;
    }
}
