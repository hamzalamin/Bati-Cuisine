package com.wora.presentation;

import com.wora.presentation.menus.ClientMenu;
import com.wora.presentation.menus.ProjectMenu;
import com.wora.presentation.menus.WorkerMenu;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final ClientMenu clientMenu;
    private final ProjectMenu projectMenu;
    private final WorkerMenu workerMenu;

    public MainMenu(ClientMenu clientMenu, ProjectMenu projectMenu, WorkerMenu workerMenu) {
        this.clientMenu = clientMenu;
        this.projectMenu = projectMenu;
        this.workerMenu = workerMenu;
    }

    public void showMenu() throws SQLException {
        int choice;
        do {
            System.out.println("************************** WELCOME BACK !!*****************************");
            System.out.println("1 - GESTION OF CLIENTS");
            System.out.println("2 - GESTION OF PROJECTS");
            System.out.println("3 - GESTION OF WORKERS");

            System.out.println("0 - EXIT");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> clientMenu.clientPresentation();
                case 2 -> projectMenu.projectPresentation();
                case 3 -> workerMenu.workerPresentation();

                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 0);
    }
}
