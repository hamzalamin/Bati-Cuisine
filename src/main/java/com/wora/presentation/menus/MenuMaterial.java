package com.wora.presentation.menus;

import com.wora.models.entities.Project;
import com.wora.presentation.MainMenu;
import com.wora.presentation.MaterialUi;

import java.sql.SQLException;
import java.util.Scanner;

import static com.wora.helpers.Scanners.scanInt;

public class MenuMaterial {
    private final Scanner scanner = new Scanner(System.in);
    private final MaterialUi materialUi;
    private MainMenu mainMenu;

    public MenuMaterial(MaterialUi materialUi) {
        this.materialUi = materialUi;
    }

    public void setMainMenu(MainMenu mainMenu){
        this.mainMenu = mainMenu;
    }
    public void materialPresentation(){
        int choice;
        do {
            System.out.println("************************** MATERIAL OF CLIENT!!*****************************");
            System.out.println("1-CREATE MATERIAL");
            System.out.println("2-UPDATE MATERIAL");
            System.out.println("3-DELETE MATERIAL");
            System.out.println("4-FIND ALL MATERIALS");
            System.out.println("5-FIND MATERIAL BY ID");

            System.out.println("0- GO BACK TO THE MAIN MENU");
            choice = scanInt("Enter your choice:");

            switch (choice) {
//                case 1 -> materialUi.create();
//                case 2 -> materialUi.update();
//                case 3 -> materialUi.delete();
//                case 4 -> materialUi.findAll();
//                case 5 -> materialUi.findById();
                case 0 -> mainMenu.showMenu();
                default -> System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        } while (choice != 0);
    }


}
