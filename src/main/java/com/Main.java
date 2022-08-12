package com;

import com.model.*;
import com.repository.AutoRepository;
import com.repository.BinaryTree;
import com.repository.BusRepository;
import com.repository.TruckRepository;
import com.service.*;
import com.utils.AutoBuilder;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    private static final AutoService AUTO_SERVICE = new AutoService(new AutoRepository());
    private static final BusService BUS_SERVICE = new BusService(new BusRepository());
    private static final TruckService TRUCK_SERVICE = new TruckService(new TruckRepository());
    protected static final Random RANDOM = new Random();
    protected static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private static final Predicate<List<Vehicle>> IS_ALL_PRICES_EXIST = list -> list
            .stream()
            .allMatch(vehicle -> vehicle.getPrice() != null);

    private static final Function<Map<String, Object>, Vehicle> mapToAutoConverter = (map) ->
            new Auto(
                    (String) map.getOrDefault("model", "model-1"),
                    (Manufacturer) map.getOrDefault("manufacturer", Manufacturer.KIA),
                    (BigDecimal) map.getOrDefault("price", BigDecimal.ZERO),
                    (String) map.getOrDefault("bodyType", "bodyType-1"),
                    (int) map.getOrDefault("count", 1));

    private static void printAllServices() {
        System.out.println("\nList of all vehicles:");
        AUTO_SERVICE.printAll();
        BUS_SERVICE.printAll();
        TRUCK_SERVICE.printAll();
        System.out.println();
    }

    private static boolean navigation() throws IOException {
        String input = READER.readLine();
        switch (input) {
            case "1":
                Creating.start(READER, AUTO_SERVICE, BUS_SERVICE, TRUCK_SERVICE);
                break;
            case "2":
                Updating.start(READER, AUTO_SERVICE, BUS_SERVICE, TRUCK_SERVICE);
                break;
            case "3":
                Deleting.start(READER, AUTO_SERVICE, BUS_SERVICE, TRUCK_SERVICE);
                break;
            case "4":
                comparatorExample();
                System.out.println("Input any symbol");
                READER.readLine();
            case "5":
                printAllServices();
                break;
            case "6":
                tree();
                break;
            case "7":
                streamApiExample();
                break;
            case "8":
                fileReaderExample();
                break;
            case "9":
                autoBuilderExample();
                break;
            case "0":
                return false;
            default:
                System.out.println("Unexpected value: \"" + input + "\". Try 1-3 for levels or 0 to exit");
        }
        return true;
    }

    private static Auto createAuto() {
        return new Auto(
                "Model-" + RANDOM.nextInt(1000),
                Manufacturer.KIA,
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                "Model-" + RANDOM.nextInt(1000), RANDOM.nextInt(1, 20));
    }

    private static Bus createBus() {
        return new Bus(
                "Model-" + RANDOM.nextInt(1000),
                Manufacturer.KIA,
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                RANDOM.nextInt(6, 24),
                RANDOM.nextInt(1, 20));
    }

    private static Truck createTruck() {
        return new Truck(
                "Model-" + RANDOM.nextInt(1000),
                Manufacturer.KIA,
                BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                RANDOM.nextInt(5000, 12000),
                RANDOM.nextInt(1, 20));
    }

    private static void comparatorExample() {
        System.out.println("There is example of comparator's work");
        final List<Auto> autos = new LinkedList<>();
        System.out.println("Not sorted list:");
        for (int i = 0; i < 6; i++) {
            final Auto auto = createAuto();
            System.out.println(auto);
            autos.add(auto);
        }
        final Comparator<Auto> comparator = Comparator.comparing(Auto::getPrice).reversed()
                .thenComparing(auto -> auto.getClass().getSimpleName())
                .thenComparingInt(Auto::getCount);
        autos.sort(comparator);
        System.out.println("Sorted list:");
        for (Auto auto : autos) {
            System.out.println(auto);
        }

    }

    private static void tree() throws IOException {
        System.out.println("This is example of binary tree");
        BinaryTree tree = new BinaryTree();
        for (int i = 0; i < 6; i++) {
            final Auto auto = createAuto();
            tree.add(auto);
        }
        System.out.println("As tree:");
        tree.printToConsole();
        System.out.println("Sum of left = " + tree.sumLeft());
        System.out.println("Sum of right = " + tree.sumRight());
        System.out.println("Print any symbol to exit");
        READER.readLine();
    }

    private static void streamApiExample() throws IOException {
        final List<Vehicle> vehiclesList = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            final Auto auto = createAuto();
            System.out.println(auto);
            vehiclesList.add(auto);
            System.out.println(auto);
            vehiclesList.add(auto);
            final Bus bus = createBus();
            System.out.println(bus);
            vehiclesList.add(bus);
            final Truck truck = createTruck();
            System.out.println(truck);
            vehiclesList.add(truck);
        }

        System.out.println("Sum of all prices:");
        System.out.println(vehiclesList.stream()
                .map(Vehicle::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Input X for low border of prices:");
        BigDecimal x = BigDecimal.valueOf(Integer.parseInt(READER.readLine()));
        System.out.println("Ok. Here the list of autos which prices higher then " + x);
        vehiclesList.stream()
                .filter(vehicle -> vehicle.getPrice().compareTo(x) >= 0)
                .forEach(System.out::println);

        final Comparator<Vehicle> comparator = Comparator.comparing(vehicle -> vehicle.getClass().getSimpleName());

        Map<String, Object> vehiclesMap = vehiclesList.stream()
                .sorted(comparator)
                .collect(Collectors.toMap(
                        Vehicle::getId,
                        Vehicle::getClass, (o1, o2) -> o1));

        System.out.println("List as Map:");
        System.out.println(vehiclesMap);

        System.out.println("Input any symbol to continue");
        READER.readLine();

        System.out.println("Detail-1 has been added to random vehicle");
        List<String> details = new LinkedList<>();
        details.add("Detail-1");
        vehiclesList.get(RANDOM.nextInt(0, 6)).setDetails(details);
        System.out.println("Is any vehicle in List contains Detail-1?\t" + vehiclesList.stream()
                .map(Vehicle::getDetails)
//                .map(List::toArray)
                .anyMatch((list) -> {
                    if (list == null) return false;
                    else return list.contains("Detail-1");
                }));
        System.out.println("Is all vehicles in List have prices?\t" + IS_ALL_PRICES_EXIST.test(vehiclesList));

        System.out.println("Statistics of prices:");
        System.out.println(vehiclesList.stream()
                .mapToDouble((auto) -> auto.getPrice().doubleValue())
                .summaryStatistics());

    }

    private static void fileReaderExample() throws FileNotFoundException {
        final Pattern JSON_PATTERN = Pattern.compile("\"(.*)\":+ +\"(.*)\"");
        final Pattern XML_PATTERN = Pattern.compile("<(\\w*)?\\s*(\\w*)?=*\"*(.)*?\"*>+(.*)</.*>");

        Map<String, String> properties = new HashMap<String, String>();
        initPropertiesMap(properties);

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream jsonAsInputStream = loader.getResourceAsStream("Auto.json");
        InputStream xmlAsInputStream = loader.getResourceAsStream("Auto.xml");

        try (final InputStreamReader streamReader = new InputStreamReader(jsonAsInputStream);
             final BufferedReader fileBufferedReader = new BufferedReader(streamReader)) {
            String line;
            while ((line = fileBufferedReader.readLine()) != null) {
                final Matcher matcher = JSON_PATTERN.matcher(line);
                if (matcher.find()) {
                    if (properties.containsKey(matcher.group(1))) {
                        properties.replace(matcher.group(1), matcher.group(2));
                        System.out.println("JSON: " + matcher.group(1) + '\t' + matcher.group(2));
                    }

                }
            }
            System.out.println("Created auto depends on json:");
            System.out.println(createAutoFromMap(properties));
        } catch (final IOException | ParseException e) {
            e.printStackTrace();
        }

        initPropertiesMap(properties);

        try (final InputStreamReader streamReader = new InputStreamReader(xmlAsInputStream);
             BufferedReader fileBufferedReader = new BufferedReader(streamReader)) {
            String line;
            while ((line = fileBufferedReader.readLine()) != null) {
                final Matcher matcher = XML_PATTERN.matcher(line);
                if (matcher.find()) {
                    if (properties.containsKey(matcher.group(1))) {
                        properties.replace(matcher.group(1), matcher.group(4));
                        System.out.println("XML: " + matcher.group(1) + '\t' + matcher.group(4));
                    }
                    if (properties.containsKey(matcher.group(2))) {
                        properties.replace(matcher.group(2), matcher.group(3));
                        System.out.println("XML: " + matcher.group(2) + '\t' + matcher.group(3));
                    }
                }
            }

            System.out.println("Created auto depends on xml:");
            System.out.println(createAutoFromMap(properties));

        } catch (final IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    private static void initPropertiesMap(Map<String, String> properties) {
        properties.put("model", null);
        properties.put("manufacturer", null);
        properties.put("price", null);
        properties.put("currency", null);
        properties.put("bodyType", null);
        properties.put("created", null);
        properties.put("count", null);
        properties.put("volume", null);
        properties.put("brand", null);
    }

    private static Auto createAutoFromMap(Map<String, String> properties) throws ParseException {
        Auto auto = new Auto(properties.get("model"),
                Manufacturer.valueOf(properties.get("manufacturer")),
                BigDecimal.valueOf(Double.parseDouble(properties.get("price"))),
                properties.get("currency"),
                properties.get("bodyType"),
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(properties.get("created")),
                Integer.parseInt(properties.get("count")),
                Integer.parseInt(properties.get("volume")),
                properties.get("brand"));
        return auto;
    }

    private static void autoBuilderExample() {
        AutoBuilder builder = new AutoBuilder("TestModel", Manufacturer.KIA, BigDecimal.TEN, "$");
        Auto testAuto = builder.bodyType("qwertyuiop[]asdfghjkl;")
                .count(0)
                .engine(2, "brand-1")
                .build();
        System.out.println("Auto built by builder:");
        System.out.println(testAuto);
    }


    public static void main(String[] args) {
        System.out.println("\n\n\nWelcome to homework #10!\n\nHere will be the list of your vehicles.\n");
        AUTO_SERVICE.createAndSaveAutos(5);
        try {
            do {
                System.out.println("Choose what you want to do:");
                System.out.println("1. To CREATE the new vehicle");
                System.out.println("2. To UPDATE the price by id");
                System.out.println("3. To DELETE one by id ");
                System.out.println("4. To check comparator's work");
                System.out.println("5. To PRINT list of existing vehicles");
                System.out.println("6. To check Tree work example");
                System.out.println("7. To check Stream API work example");
                System.out.println("8. To check FileReader example");
                System.out.println("9. To check AutoBuilder example");
                System.out.println("Choose the action you need (1-7) or 0 to exit:");
            } while (navigation());
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}