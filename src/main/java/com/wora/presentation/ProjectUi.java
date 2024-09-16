package com.wora.presentation;

import com.wora.models.dtos.ProjectDto;
import com.wora.models.entities.Client;
import com.wora.models.entities.Project;
import com.wora.models.enums.ProjectStatus;
import com.wora.services.IClientService;
import com.wora.services.IProjectService;

import java.sql.SQLException;
import java.util.*;

public class ProjectUi {
    private final IProjectService service;
    private final IClientService cService;

    public ProjectUi(IProjectService service, IClientService cService) {
        this.service = service;
        this.cService = cService;
    }

    public void findAll() throws SQLException {
        List<Project> projects = service.findAll();
        if (projects.isEmpty()) {
            System.out.println("No projects found");
            return;
        }

        for (int p = 0; p < projects.size(); p++) {
            System.out.println((p + 1) + " -> " + " (ID: " + projects.get(p).getId() +
                    " -- Name: " + projects.get(p).getProjectName() +
                    " -- Profit margin: " + projects.get(p).getProfitMargin() +
                    " -- total cost: " + projects.get(p).getTotalCost() + ")" +
                    " -- Project Status: " + projects.get(p).getProjectStatus() + ")" +
                    " -- Client: " + projects.get(p).getClientId() + ")"
            );
        }
    }

    public void findById(){
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the UUID of Project: ");
            UUID projectId = UUID.fromString(scanner.next());

            Optional<Project> project = service.findById(projectId);
            if (project.isPresent()){
                Project project1 = project.get();
                System.out.println("ID: " + project1.getId() + " , Name: " + project1.getProjectName() + " , Profit margin: " + project1.getProfitMargin() + " , Total Cost: " + project1.getTotalCost() + " , Project status: " + project1.getProjectStatus() + " , client :" + project1.getClientId());
            } else {
                System.out.println("Project withe this ID : " + projectId + " Not found!!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void create() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Project Name:");
        String name = scanner.nextLine().trim();
        while (name.isEmpty()) {
            System.out.println("Project name is required:");
            name = scanner.nextLine().trim();
        }

        System.out.println("Profit Margin:");
        double profitMargin = Double.parseDouble(scanner.nextLine().trim());

        System.out.println("Total Cost:");
        double totalCost = Double.parseDouble(scanner.nextLine().trim());

        System.out.println("Project Status (e.g., 'Active', 'Inactive'):");
        ProjectStatus projectStatus = ProjectStatus.valueOf(scanner.nextLine().toUpperCase());
//        while (projectStatus == null) {
//            System.out.println("Project status is required:");
//            ProjectStatus projectStatus = ProjectStatus.valueOf(scanner.nextLine().toUpperCase());
//        }

        System.out.println("Client ID:");
        UUID clientId = UUID.fromString(scanner.nextLine().toString());

        ProjectDto dto = new ProjectDto(name, profitMargin, totalCost, projectStatus, clientId);
        service.create(dto);

        System.out.println("_________________________________________");
        System.out.println("Project Information");
        System.out.println("_________________________________________");
        System.out.println("Project Name: " + name);
        System.out.println("Profit Margin: " + profitMargin);
        System.out.println("Total Cost: " + totalCost);
        System.out.println("Project Status: " + projectStatus);
        System.out.println("Client ID: " + clientId);
        System.out.println("_________________________________________");
    }


    public void update() {
        Scanner scanner = new Scanner(System.in);
        try {
            List<Project> projects = service.findAll();
            if (projects.isEmpty()) {
                System.out.println("No projects found");
                return;
            }

            for (int p = 0; p < projects.size(); p++) {
                System.out.println((p + 1) + " -> " + " (ID: " + projects.get(p).getId() +
                        " -- Name: " + projects.get(p).getProjectName() +
                        " -- Profit Margin: " + projects.get(p).getProfitMargin() +
                        " -- Total Cost: " + projects.get(p).getTotalCost() +
                        " -- Project Status: " + projects.get(p).getProjectStatus() +
                        " -- Client ID: " + projects.get(p).getClientId() + ")");
            }

            System.out.println("Enter the number of the project you want to update: ");
            int index = scanner.nextInt();
            scanner.nextLine();

            if (index < 1 || index > projects.size()) {
                System.out.println("Invalid choice!!");
                return;
            }

            Project existingProject = projects.get(index - 1);

            System.out.println("Enter the project name or press Enter to keep it the same: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                name = existingProject.getProjectName();
            }

            System.out.println("Enter the profit margin or press Enter to keep it the same: ");
            String profitMarginStr = scanner.nextLine().trim();
            double profitMargin = profitMarginStr.isEmpty() ? existingProject.getProfitMargin() : Double.parseDouble(profitMarginStr);

            System.out.println("Enter the total cost or press Enter to keep it the same: ");
            String totalCostStr = scanner.nextLine().trim();
            double totalCost = totalCostStr.isEmpty() ? existingProject.getTotalCost() : Double.parseDouble(totalCostStr);

            System.out.println("Enter the project status or press Enter to keep it the same: ");
            ProjectStatus projectStatus = ProjectStatus.valueOf(scanner.nextLine().toUpperCase());
//            if (status == null) {
//                ProjectStatus staus = ProjectStatus.valueOf(scanner.nextLine().toUpperCase());
//            }
            List<Client> clients =  cService.findAll();
            if (clients.isEmpty()){
                System.out.println("no clients found");
            }
            for (int c = 0; c < clients.size(); c++){
                System.out.println((c + 1 ) + " -> " + clients.get(c).getName() + " (ID: " +  clients.get(c).getId() + ")");
            }
            System.out.println("Enter the number that you want to update: ");
            int index2 = scanner.nextInt();
            if (index2 < 1 || index2 > clients.size()){
                System.out.println("invalid choice !!");
            }
            Client existClient = clients.get(index2 - 1);

            ProjectDto dto = new ProjectDto(name, profitMargin, totalCost, projectStatus, existClient.getId());
            service.update(dto, existingProject.getId());

            System.out.println("_________________________________________");
            System.out.println("Project Information");
            System.out.println("_________________________________________");
            System.out.println("Project Name: " + name);
            System.out.println("Profit Margin: " + profitMargin);
            System.out.println("Total Cost: " + totalCost);
            System.out.println("Project Status: " + projectStatus);
            System.out.println("Client ID: " + existingProject.getClientId());
            System.out.println("_________________________________________");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void delete() {
        Scanner scanner = new Scanner(System.in);
        try {
            List<Project> projects = service.findAll();
            if (projects.isEmpty()) {
                System.out.println("No projects found");
                return;
            }

            for (int p = 0; p < projects.size(); p++) {
                System.out.println((p + 1) + " -> " + projects.get(p).getProjectName() + " (ID: " + projects.get(p).getId() + ")");
            }

            System.out.println("Enter the number of the project you want to delete: ");
            int index = scanner.nextInt();
            scanner.nextLine();

            if (index < 1 || index > projects.size()) {
                System.out.println("Invalid choice!!");
                return;
            }

            Project existingProject = projects.get(index - 1);
            service.delete(existingProject.getId());
            System.out.println("Project deleted successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

