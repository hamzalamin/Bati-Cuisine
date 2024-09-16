package com.wora.presentation;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final ClientMenu clientMenu;

    public MainMenu(ClientMenu clientMenu) {
        this.clientMenu = clientMenu;
    }

    public void showMenu() throws SQLException {
        int choice;
        do {
            System.out.println("************************** WELCOME BACK !!*****************************");
            System.out.println("1 - GESTION OF CLIENT");
            System.out.println("0 - EXIT");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> clientMenu.clientPresentation();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 0);
    }
}
