package com.util;

import lombok.Getter;

@Getter
public class AutoBuilder {
//    private String bodyType;
//    private Date date;
//    private Engine engine;
//    private final String currency;
//    private final String model;
//    private final BigDecimal price;
//    private final Manufacturer manufacturer;
//    private int count;
//    private List<String> details;
//
//    public AutoBuilder(String model, Manufacturer manufacturer, BigDecimal price, String currency) {
//        this.model = model;
//        this.manufacturer = manufacturer;
//        this.price = price;
//        this.currency = currency;
//    }
//
//    public AutoBuilder bodyType(String bodyType) {
//        if (bodyType.length() > 20)
//            bodyType = bodyType.substring(0, 20);
//        this.bodyType = bodyType;
//        return this;
//    }
//
//    public AutoBuilder date(Date date) {
//        this.date = date;
//        return this;
//    }
//
//    public AutoBuilder engine(int volume, String brand) {
//        engine = new Engine(volume, brand);
//        return this;
//    }
//
//    public AutoBuilder count(int count) {
//        if (count <= 0)
//            count = 1;
//        this.count = count;
//        return this;
//    }
//
//    public AutoBuilder details(List<String> details) {
//        this.details = details;
//        return this;
//    }
//
//    public Auto build() {
//        return new Auto(this);
//    }
}
