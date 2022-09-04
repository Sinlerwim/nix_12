package com.command;

import com.model.VehicleType;
import com.util.UserInputUtil;
import com.util.VehicleFactory;

import java.util.ArrayList;
import java.util.List;

public class Create implements Command {
    private static final VehicleFactory VEHICLE_FACTORY = VehicleFactory.getInstance();

    @Override
    public void execute() {
        final VehicleType[] values = VehicleType.values();
        final List<String> names = getNames(values);
        final int userInput = UserInputUtil.getUserInput("What you want to create:", names);
        final VehicleType value = values[userInput];
        VehicleFactory.buildAndSave(value);
    }

    private static List<String> getNames(VehicleType[] values) {
        final List<String> names = new ArrayList<>(values.length);
        for (VehicleType type : values) {
            names.add(type.name());
        }
        return names;
    }
}
