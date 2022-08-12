package com.model;

import com.utils.AutoBuilder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Auto extends Vehicle{
    private String bodyType;
    private Date date;
    private Engine engine;
    private String currency;


    public Auto(String model, Manufacturer manufacturer, BigDecimal price, String currency,
                String bodyType, Date date, int count, int engineVolume, String engineBrand) {
        super(model, manufacturer, price, count);
        this.bodyType = bodyType;
        this.date = date;
        this.currency = currency;
        this.engine = new Engine(engineVolume, engineBrand);
    }

    public Auto(String model, Manufacturer manufacturer, BigDecimal price, String bodyType, int count) {
        super(model, manufacturer, price, count);
        this.bodyType = bodyType;
    }

    public Auto(AutoBuilder builder) {
        super(builder.getModel(), builder.getManufacturer(), builder.getPrice(), builder.getCount());
        this.bodyType = builder.getBodyType();
        this.date = builder.getDate();
        this.currency = builder.getCurrency();
        this.engine = builder.getEngine();
    }

    @Override
    public String toString() {
        return "Auto{" +
                "bodyType='" + bodyType + '\'' +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price + currency +
                ", created=" + date +
                ", manufacturer=" + manufacturer +
                ", count=" + count +
                ", " + engine +
                '}';
    }
}
