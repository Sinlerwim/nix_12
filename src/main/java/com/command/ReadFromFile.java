package com.command;

import com.model.Auto;
import com.model.Manufacturer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFromFile implements Command {
    @Override
    public void execute() {
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
}
