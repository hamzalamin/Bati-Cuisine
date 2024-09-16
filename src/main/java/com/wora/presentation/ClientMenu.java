package com.wora.presentation;

import java.sql.SQLException;
import java.util.Scanner;

public class ClientMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ClientUi clientUi;
    private MainMenu mainMenu;
    public ClientMenu(ClientUi clientUi) {
        this.clientUi = clientUi;
    }

    public void setMainMenu(MainMenu mainMenu){
        this.mainMenu = mainMenu;
    }

    public void clientPresentation() throws SQLException {
        int choice;
        do {
            System.out.println("************************** GESTION OF CLIENT!!*****************************");
            System.out.println("1-CREATE CLIENT");
            System.out.println("2-UPDATE CLIENT");
            System.out.println("3-DELETE CLIENT");
            System.out.println("4-FIND ALL CLIENTS");
            System.out.println("5-FIND CLIENT BY ID");

            System.out.println("0- GO BACK TO THE MAIN MENU");
            System.out.print("Enter your choice:");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> clientUi.create();
                case 2 -> clientUi.update();
                case 3 -> clientUi.delete();
                case 4 -> clientUi.findAll();
                case 5 -> clientUi.findById();

                case 0 -> mainMenu.showMenu();
                default -> System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 0);
    }
}
