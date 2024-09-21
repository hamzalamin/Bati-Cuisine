package com.wora.presentation.menus;

import com.wora.presentation.EstimateUi;
import com.wora.presentation.MainMenu;

import static com.wora.helpers.Scanners.scanInt;

public class EstimateMenu {
    private final EstimateUi estimateUi;
    private MainMenu mainMenu;

    public EstimateMenu(EstimateUi estimateUi) {
        this.estimateUi = estimateUi;
    }

    public void setMainMenu(MainMenu mainMenu){
        this.mainMenu = mainMenu;
    }

    public void estimatePresentation(){
        int choice;
        do {
            System.out.println("************************** GESTION OF ESTIMATE!!*****************************");
            System.out.println("1-CREATE ESTIMATE");
            System.out.println("2-UPDATE ESTIMATE");
            System.out.println("3-DELETE ESTIMATE");
            System.out.println("4-FIND ALL ESTIMATES");
            System.out.println("5-FIND ESTIMATE BY ID");
            System.out.println("6-SEARCH FOR ESTIMATE BY CLIENT ID");

            System.out.println("0- GO BACK TO THE MAIN MENU");
            choice = scanInt("Enter your choice:");
            switch (choice) {
                case 1 -> estimateUi.create();
                case 2 -> estimateUi.update();
                case 3 -> estimateUi.delete();
                case 4 -> estimateUi.findAll();
                case 5 -> estimateUi.findById();
                case 6 -> estimateUi.findClientEstimate();

                case 0 -> mainMenu.showMenu();
                default -> System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 0);
    }

}
