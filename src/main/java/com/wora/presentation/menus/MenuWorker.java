package com.wora.presentation.menus;

import com.wora.presentation.MainMenu;
import com.wora.presentation.WorkerUi;

import java.sql.SQLException;

import static com.wora.helpers.Scanners.scanInt;

public class MenuWorker {
    private final WorkerUi workerUi;
    private MainMenu mainMenu;

    public MenuWorker(WorkerUi workerUi) {
        this.workerUi = workerUi;
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void workerPresentation()  {
        int choice;
        do {
            System.out.println("************************** MANAGEMENT OF WORKERS **************************");
            System.out.println("1 - CREATE WORKER");
            System.out.println("2 - UPDATE WORKER");
            System.out.println("3 - DELETE WORKER");
            System.out.println("4 - FIND ALL WORKERS");
            System.out.println("5 - FIND WORKER BY ID");
            System.out.println("0 - GO BACK TO THE MAIN MENU");
            System.out.println("*****************************************************************************");

            choice = scanInt("Enter your choice:");
            switch (choice) {
                case 1 -> workerUi.create();
                case 2 -> workerUi.update();
                case 3 -> workerUi.delete();
                case 4 -> workerUi.findAll();
                case 5 -> workerUi.findById();
                case 0 -> {
                    mainMenu.showMenu();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 0);
    }

}
