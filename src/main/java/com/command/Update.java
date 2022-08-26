package com.command;

import com.util.UserInputUtil;
import com.util.VehicleFactory;

import java.math.BigDecimal;

public class Update implements Command {
    @Override
    public void execute() {
        final String id = UserInputUtil.getUserInput("Print ID:");
        final String price = UserInputUtil.getUserInput("Print new price:");
        if (VehicleFactory.changePriceById(id, new BigDecimal(price))) {
            System.out.println("Successful");
        } else System.out.println("Oops... Seems like vehicle with this ID doesn't exist");
    }
}
