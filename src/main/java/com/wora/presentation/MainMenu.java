package com.wora.presentation;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final ClientUi clientUi;

    public MainMenu(ClientUi clientUi) {
        this.clientUi = clientUi;
    }

    public void showMenu() throws SQLException {
        int choice;
        do {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> clientUi.create();

                    case 0 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            System.out.println();
        } while (choice != 0);
    }
}
