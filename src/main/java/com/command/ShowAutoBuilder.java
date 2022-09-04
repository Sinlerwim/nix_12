package com.command;

import com.model.Auto;
import com.model.Manufacturer;
import com.util.AutoBuilder;

import java.math.BigDecimal;

public class ShowAutoBuilder implements Command {
    @Override
    public void execute() {
        AutoBuilder builder = new AutoBuilder("TestModel", Manufacturer.KIA, BigDecimal.TEN, "$");
        Auto testAuto = builder.bodyType("qwertyuiop[]asdfghjkl;")
                .count(0)
                .engine(2, "brand-1")
                .build();
        System.out.println("Auto built by builder:");
        System.out.println(testAuto);
    }
}
