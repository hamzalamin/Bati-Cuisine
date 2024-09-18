package com.wora.presentation.menus;

import com.wora.presentation.WorkerUi;
import com.wora.presentation.MainMenu;
import java.sql.SQLException;
import java.util.Scanner;

public class WorkerMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final WorkerUi workerUi;
    private MainMenu mainMenu;

    public WorkerMenu(WorkerUi workerUi) {
        this.workerUi = workerUi;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void workerPresentation() throws SQLException {
        int choice = -1; // Initialize to an invalid value
        do {
            System.out.println("************************** GESTION OF WORKER!!*****************************");
            System.out.println("1 - CREATE WORKER");
            System.out.println("2 - UPDATE WORKER");
            System.out.println("3 - DELETE WORKER");
            System.out.println("4 - FIND ALL WORKERS");
            System.out.println("5 - FIND WORKER BY ID");
            System.out.println("0 - GO BACK TO THE MAIN MENU");
            System.out.print("Enter your choice: ");

            // Check if input is available and valid
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
                continue; // Skip to the next iteration
            }

            switch (choice) {
                case 1 -> workerUi.create();
                case 2 -> workerUi.update();
                case 3 -> workerUi.delete();
                case 4 -> workerUi.findAll();
                case 5 -> workerUi.findById();
                case 0 -> mainMenu.showMenu();
                default -> System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 0);
    }
}
