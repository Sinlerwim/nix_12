package com.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
//@MappedSuperclass
public abstract class Vehicle {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    protected String id;

    protected String model;
    protected BigDecimal price;
    protected Manufacturer manufacturer;
    protected int count;
    protected LocalDateTime date;

    //    @ManyToOne
//    @JoinColumn(name = "engine_id")
    protected Engine engine;

    //    @OneToOne
//    @JoinColumn(name = "invoice_id")
    protected Invoice invoice;
}
