package com.wora.helpers;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.UUID;

public class Scanners {
    private final static Scanner scanner = new Scanner(System.in);

    public static int scanInt(String prompt) {
        try {
            System.out.println(prompt);
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NoSuchElementException e) {
            Scanner sc = new Scanner(System.in);
            return Integer.parseInt(sc.nextLine());
        }
    }

    public static double scanDouble(String prompt) {
        System.out.println(prompt);
        return Double.parseDouble(scanner.nextLine().trim());
    }

    public static String scanString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    public static boolean scanBoolean(String prompt) {
        System.out.println(prompt);
        final String input = scanner.nextLine();
        return switch (input.toLowerCase()) {
            case "true", "y", "1", "yes", "oui", "si", "n3am" -> true;
            case "false", "n", "0", "non", "no", "la" -> false;
            default -> throw new IllegalStateException("input is not correct");
        };
    }

    public static UUID scanUUID(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine().trim();

        while (!isValidUUID(input)) {
            System.out.println("Invalid UUID format. Please enter a valid UUID.");
            input = scanner.nextLine().trim();
        }
        return UUID.fromString(input);
    }

    private static boolean isValidUUID(String input) {
        try {
            UUID.fromString(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}