package com.command;

import com.util.UserInputUtil;
import com.util.VehicleFactory;

public class Delete implements Command {
    private static final VehicleFactory VEHICLE_FACTORY = VehicleFactory.getInstance();

    @Override
    public void execute() {
        final String id = UserInputUtil.getUserInput("Print ID:");
        if (VehicleFactory.deleteById(id)) {
            System.out.println("Successful");
        } else System.out.println("Oops... Seems like vehicle with this ID doesn't exist");
    }
}
