package com.command;

//import com.model.*;
//import com.util.UserInputUtil;
//import com.util.VehicleFactory;
//
//import java.math.BigDecimal;
//import java.util.*;
//import java.util.function.Function;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;

public class ShowStream implements Command {
//    private static final Random RANDOM = new Random();
//    private static final Predicate<List<Vehicle>> IS_ALL_PRICES_EXIST = list -> list
//            .stream()
//            .allMatch(vehicle -> vehicle.getPrice() != null);
//
//    private static final Function<Map<String, Object>, Vehicle> mapToAutoConverter = (map) ->
//            new Auto(
//                    (String) map.getOrDefault("model", "model-1"),
//                    (Manufacturer) map.getOrDefault("manufacturer", Manufacturer.KIA),
//                    (BigDecimal) map.getOrDefault("price", BigDecimal.ZERO),
//                    (String) map.getOrDefault("bodyType", "bodyType-1"),
//                    (int) map.getOrDefault("count", 1));

    @Override
    public void execute() {
//        final List<Vehicle> vehiclesList = new LinkedList<>();
//        for (int i = 0; i < 2; i++) {
//            final Auto auto = (Auto) VehicleFactory.build(VehicleType.AUTO);
//            System.out.println(auto);
//            vehiclesList.add(auto);
//            System.out.println(auto);
//            vehiclesList.add(auto);
//            final Bus bus = (Bus) VehicleFactory.build(VehicleType.BUS);
//            System.out.println(bus);
//            vehiclesList.add(bus);
//            final Truck truck = (Truck) VehicleFactory.build(VehicleType.TRUCK);
//            System.out.println(truck);
//            vehiclesList.add(truck);
//        }
//
//        System.out.println("Sum of all prices:");
//        System.out.println(vehiclesList.stream()
//                .map(Vehicle::getPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add));
//
//        BigDecimal x = new BigDecimal(UserInputUtil.getUserInput("Input X for low border of prices:"));
//        System.out.println("Ok. Here the list of autos which prices higher then " + x);
//        vehiclesList.stream()
//                .filter(vehicle -> vehicle.getPrice().compareTo(x) >= 0)
//                .forEach(System.out::println);
//
//        final Comparator<Vehicle> comparator = Comparator.comparing(vehicle -> vehicle.getClass().getSimpleName());
//
//        Map<String, Object> vehiclesMap = vehiclesList.stream()
//                .sorted(comparator)
//                .collect(Collectors.toMap(
//                        Vehicle::getId,
//                        Vehicle::getClass, (o1, o2) -> o1));
//
//        System.out.println("List as Map:");
//        System.out.println(vehiclesMap);
//
//        System.out.println("Detail-1 has been added to random vehicle");
//        List<String> details = new LinkedList<>();
//        details.add("Detail-1");
//        vehiclesList.get(RANDOM.nextInt(0, 6)).setDetails(details);
//        System.out.println("Is any vehicle in List contains Detail-1?\t" + vehiclesList.stream()
//                .map(Vehicle::getDetails)
//                .anyMatch((list) -> {
//                    if (list == null) return false;
//                    else return list.contains("Detail-1");
//                }));
//        System.out.println("Is all vehicles in List have prices?\t" + IS_ALL_PRICES_EXIST.test(vehiclesList));
//
//        System.out.println("Statistics of prices:");
//        System.out.println(vehiclesList.stream()
//                .mapToDouble((auto) -> auto.getPrice().doubleValue())
//                .summaryStatistics());
    }
}
