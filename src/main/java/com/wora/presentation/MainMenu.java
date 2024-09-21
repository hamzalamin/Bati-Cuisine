package com.wora.presentation;

import com.wora.presentation.menus.*;

import java.util.Scanner;

import static com.wora.helpers.Scanners.scanInt;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final ClientMenu clientMenu;
    private final ProjectMenu projectMenu;
    private final MenuWorker workerMenu;
    private final MenuMaterial materialMenu;
    private final EstimateMenu estimateMenu;

    public MainMenu(ClientMenu clientMenu, ProjectMenu projectMenu, MenuWorker workerMenu, MenuMaterial materialMenu, EstimateMenu estimateMenu) {
        this.clientMenu = clientMenu;
        this.projectMenu = projectMenu;
        this.workerMenu = workerMenu;
        this.materialMenu = materialMenu;
        this.estimateMenu = estimateMenu;
    }

    public void showMenu(){
        while (true) {

            System.out.println("************************** WELCOME BACK !!*****************************");
            System.out.println("1 - GESTION OF CLIENTS");
            System.out.println("2 - GESTION OF PROJECTS");
            System.out.println("3 - GESTION OF WORKERS");
            System.out.println("4 - GESTION OF MATERIALS");
            System.out.println("5 - GESTION OF ESTIMATES");

            System.out.println("0 - EXIT");

            final int choice = scanInt("Enter your choice: ");
            switch (choice) {
                case 1 -> clientMenu.clientPresentation();
                case 2 -> projectMenu.projectPresentation();
                case 3 -> workerMenu.workerPresentation();
                case 4 -> materialMenu.materialPresentation();
                case 5 -> estimateMenu.estimatePresentation();
                case 0 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            };
        }
    }
}
