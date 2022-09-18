package com.repository;

import com.google.gson.*;
import com.model.Vehicle;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public abstract class CrudRepository<T extends Vehicle> {

    protected static Gson gson;

    protected final MongoCollection<Document> collection;

    protected CrudRepository(MongoDatabase db, String collectionName) {
        this.collection = db.getCollection(collectionName);
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                        (localDateTime, type, jsonSerializationContext) ->
                                new JsonPrimitive(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
                        (json, type, jsonSerializationContext) -> LocalDateTime.parse(json.getAsString() + " 00:00",
                                DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm").withLocale(Locale.ENGLISH)))
                .create();
    }

    protected static <T> Document mapFrom(T item) {
        return Document.parse(gson.toJson(item));
    }

    abstract Optional<T> findById(String id);

    public void save(T vehicle) throws IllegalArgumentException {
        vehicle.setDate(LocalDateTime.now());
        collection.insertOne(mapFrom(vehicle));
    }

    public abstract List<T> getAll();

    public void clear() {
//        collection.;
    }

    public abstract boolean update(String id, BigDecimal price);

    public abstract boolean delete(String id);

    abstract Optional<T> findByPrice(BigDecimal price);

    public abstract Optional<List<T>> findByInvoice(String id);


}
