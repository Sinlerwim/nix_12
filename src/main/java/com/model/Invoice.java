package com.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class Invoice {

    private String id;
    private LocalDateTime date;
    private BigDecimal price;
    private List<String> vehiclesId;

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", created=" + date +
                ", vehicles=" + vehiclesId +
                ", price=" + price +
                '}';
    }
}