package com.repository;

import com.google.gson.*;
import com.model.Invoice;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Filters;
import lombok.SneakyThrows;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.mongodb.client.model.Aggregates.group;


public class MongoInvoiceRepository {

    private static Gson gson;

    private final MongoDatabase db;

    private final MongoCollection<Document> collection;

    private final String COLLECTION_NAME = "Invoice";

    public MongoInvoiceRepository(MongoDatabase db) {
        this.db = db;
        this.collection = db.getCollection(COLLECTION_NAME);
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                        (localDateTime, type, jsonSerializationContext) ->
                                new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                        (json, type, jsonSerializationContext) -> LocalDateTime.parse(json.getAsString() + " 00:00",
                                DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm").withLocale(Locale.ENGLISH)))
                .create();
    }

    protected static Document mapFrom(Invoice invoice) {
        return Document.parse(gson.toJson(invoice));
    }

    public List<Invoice> getInvoicesExpensiveThan(BigDecimal boundPrice) {
//        Document filter = new Document();
//        filter.append("price", "$gt"+boundPrice);
        Bson filter = Filters.gt("price", boundPrice);
        return collection.find(filter)
                .map(item -> {
                    Invoice invoice = gson.fromJson(item.toJson(), Invoice.class);
                    invoice.setId(item.get("_id", ObjectId.class).toString());
                    return invoice;
                })
                .into(new ArrayList<>());
    }

    @SneakyThrows
    public void save(Invoice invoice) {
        invoice.setDate(LocalDateTime.now());
        collection.insertOne(mapFrom(invoice));
    }

    public List<Invoice> getAll() {
        return collection.find()
                .map(item -> {
                    Invoice invoice = gson.fromJson(item.toJson(), Invoice.class);
                    invoice.setId(item.get("_id", ObjectId.class).toString());
                    return invoice;
                })
                .into(new ArrayList<>());
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
        return (int) collection.countDocuments();
    }

    @SneakyThrows
    public void changeInvoiceDate(String invoiceId, String date) {
        collection.updateOne(
                new BasicDBObject("_id", new ObjectId(invoiceId)),
                new BasicDBObject("$set", new BasicDBObject("date", LocalDateTime.parse(date)))
        );
    }

    @SneakyThrows
    public Map<BigDecimal, Integer> getInvoicesGroupedByPrice() {
        Map<BigDecimal, Integer> groupedPrices = new HashMap<>();
        AggregateIterable<Document> aggregateIterable = collection.aggregate(Arrays.asList(
                group("$price", Accumulators.sum("numberOfInvoices", 1))));
        MongoCursor<Document> iterator = aggregateIterable.iterator();
        while (iterator.hasNext()) {
            Document line = iterator.next();
            groupedPrices.put(BigDecimal.valueOf(line.get(("_id"), Double.class)), line.get("numberOfInvoices", Integer.class));
        }
        return groupedPrices;
    }
}
