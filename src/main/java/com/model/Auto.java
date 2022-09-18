package com.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity
public class Auto extends Vehicle{
    private String bodyType;

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
