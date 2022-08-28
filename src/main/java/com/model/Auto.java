package com.model;

import com.util.AutoBuilder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Auto extends Vehicle{
    private String bodyType;


    public Auto(String model, Manufacturer manufacturer, BigDecimal price, String currency,
                String bodyType, Date date, int count, int engineVolume, String engineBrand) {
        super(model, manufacturer, price, count);
        this.bodyType = bodyType;
        this.engine = new Engine(engineVolume, engineBrand);
    }

    public Auto(String model, Manufacturer manufacturer, BigDecimal price, String bodyType, int count) {
        super(model, manufacturer, price, count);
        this.bodyType = bodyType;
    }

    public Auto(AutoBuilder builder) {
        super(builder.getModel(), builder.getManufacturer(), builder.getPrice(), builder.getCount());
        this.bodyType = builder.getBodyType();
        this.engine = builder.getEngine();
    }

    public Auto(String id, String model, Manufacturer manufacturer, BigDecimal price, String bodyType, int count, Date date, Engine engine) {
        super(id, model, manufacturer, price, count, date, engine);
        this.bodyType = bodyType;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "bodyType='" + bodyType + '\'' +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                ", count=" + count +
                ", created=" + date +
                ", " + engine +
                '}';
    }
}
