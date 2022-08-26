package com.util;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class UserInputUtil {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private UserInputUtil() {
    }

    @SneakyThrows
    public static int getUserInput(String line, List<String> names) {
        int userInput;
        do {
            System.out.println(line);
            for (int i = 0; i < names.size(); i++) {
                System.out.printf("%d) %s%n", i, names.get(i));
            }
            final String s = READER.readLine();
            userInput = StringUtils.isNumeric(s) ? Integer.parseInt(s) : -1;
        } while (userInput < 0 || userInput >= names.size());
        return userInput;
    }

    @SneakyThrows
    public static String getUserInput(String line) {
        System.out.println(line);
        return READER.readLine();
    }
}