package com.wora.presentation;

import com.wora.presentation.menus.ClientMenu;
import com.wora.presentation.menus.MenuMaterial;
import com.wora.presentation.menus.ProjectMenu;
import com.wora.presentation.menus.MenuWorker;

import java.sql.SQLException;
import java.util.Scanner;

import static com.wora.helpers.Scanners.scanInt;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final ClientMenu clientMenu;
    private final ProjectMenu projectMenu;
    private final MenuWorker workerMenu;
    private final MenuMaterial materialMenu;

    public MainMenu(ClientMenu clientMenu, ProjectMenu projectMenu, MenuWorker workerMenu, MenuMaterial materialMenu) {
        this.clientMenu = clientMenu;
        this.projectMenu = projectMenu;
        this.workerMenu = workerMenu;
        this.materialMenu = materialMenu;
    }

    public void showMenu(){
        while (true) {

            System.out.println("************************** WELCOME BACK !!*****************************");
            System.out.println("1 - GESTION OF CLIENTS");
            System.out.println("2 - GESTION OF PROJECTS");
            System.out.println("3 - GESTION OF WORKERS");
            System.out.println("4 - GESTION OF MATERIALS");
            System.out.println("0 - EXIT");

            final int choice = scanInt("Enter your choice: ");
            switch (choice) {
                case 1 -> clientMenu.clientPresentation();
                case 2 -> projectMenu.projectPresentation();
                case 3 -> workerMenu.workerPresentation();
                case 4 -> materialMenu.materialPresentation();
                case 0 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            };
        }
    }
}
