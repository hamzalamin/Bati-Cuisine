package com.wora.presentation.menus;

import com.wora.presentation.ClientUi;
import com.wora.presentation.MainMenu;

import java.sql.SQLException;

import static com.wora.helpers.Scanners.scanInt;

public class ClientMenu {
    private final ClientUi clientUi;
    private MainMenu mainMenu;

    public ClientMenu(ClientUi clientUi) {
        this.clientUi = clientUi;
    }

    public void setMainMenu(MainMenu mainMenu){
        this.mainMenu = mainMenu;
    }

    public void clientPresentation() {
        int choice;
        do {
            System.out.println("************************** GESTION OF CLIENT!!*****************************");
            System.out.println("1-CREATE CLIENT");
            System.out.println("2-UPDATE CLIENT");
            System.out.println("3-DELETE CLIENT");
            System.out.println("4-FIND ALL CLIENTS");
            System.out.println("5-FIND CLIENT BY ID");
            System.out.println("6-SEARCH FOR CLIENT");

            System.out.println("0- GO BACK TO THE MAIN MENU");
            choice = scanInt("Enter your choice:");
            switch (choice) {
                case 1 -> clientUi.create();
                case 2 -> clientUi.update();
                case 3 -> clientUi.delete();
                case 4 -> clientUi.findAll();
                case 5 -> clientUi.findById();
                case 6 -> clientUi.searchByName();

                case 0 -> mainMenu.showMenu();
                default -> System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 0);
    }
}
