package com.wora.presentation;

import java.sql.SQLException;
import java.util.Scanner;

public class ProjectMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ProjectUi projectUi;
    private MainMenu mainMenu;

    public ProjectMenu(ProjectUi projectUi) {
        this.projectUi = projectUi;
    }
    public void setMainMenu(MainMenu mainMenu){
        this.mainMenu = mainMenu;
    }

    public void projectPresentation() throws SQLException {
        int choice;
        do {
            System.out.println("************************** GESTION OF PROJECT!!*****************************");
            System.out.println("1-CREATE PROJECT");
            System.out.println("2-UPDATE PROJECT");
            System.out.println("3-DELETE PROJECT");
            System.out.println("4-FIND ALL PROJECTS");
            System.out.println("5-FIND PROJECT BY ID");

            System.out.println("0- GO BACK TO THE MAIN MENU");
            System.out.print("Enter your choice:");
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> projectUi.create();
                case 2 -> projectUi.update();
                case 3 -> projectUi.delete();
                case 4 -> projectUi.findAll();
                case 5 -> projectUi.findById();

                case 0 -> mainMenu.showMenu();
                default -> System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 0);
    }
}
