package com.wora.presentation;

import com.wora.helpers.Scanners;
import com.wora.models.dtos.ProjectDto;
import com.wora.models.entities.Client;
import com.wora.models.entities.Project;
import com.wora.models.enums.ComponentType;
import com.wora.models.enums.ProjectStatus;
import com.wora.services.IClientService;
import com.wora.services.IProjectService;

import java.sql.SQLException;
import java.util.*;

import static com.wora.helpers.Scanners.*;

public class ProjectUi {
    private final IProjectService service;
    private final IClientService cService;

    public ProjectUi(IProjectService service, IClientService cService) {
        this.service = service;
        this.cService = cService;
    }

    public void findAll() {
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

    public void findById() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the UUID of Project: ");
        UUID projectId = UUID.fromString(scanner.next());

        Optional<Project> project = service.findById(projectId);
        if (project.isPresent()) {
            Project project1 = project.get();
            System.out.println("ID: " + project1.getId() + " , Name: " + project1.getProjectName() + " , Profit margin: " + project1.getProfitMargin() + " , Total Cost: " + project1.getTotalCost() + " , Project status: " + project1.getProjectStatus() + " , client :" + project1.getClientId());
        } else {
            System.out.println("Project withe this ID : " + projectId + " Not found!!");
        }

    }


    public void create() {
        String name = scanString("Project Name: ");
        Double profitMargin = scanDouble("Profit Margin: ");
        Double totalCost = scanDouble("Total Cost: ");
        ProjectStatus projectStatus = ProjectStatus.valueOf(Scanners.scanString("Status Of Project: ").toUpperCase().trim());
        System.out.println("Available Clients:");
        List<Client> clients = cService.findAll();
        if (clients.isEmpty()) {
            System.out.println("No clients found.");
        } else {
            for (int c = 0; c < clients.size(); c++) {
                System.out.println((c + 1) + " -> " + clients.get(c).getName() + " (ID: " + clients.get(c).getId() + ")");
            }
            int index = scanInt("Select a client for this project:");
            UUID clientId = null;
            if (index < 1 || index > clients.size()) {
                System.out.println("Invalid choice!!");
            } else {
                Client selectedClient = clients.get(index - 1);
                clientId = selectedClient.getId();
            }

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
    }


    public void update() {
        Scanner scanner = new Scanner(System.in);
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
        if (projectStatus != null) projectStatus = existingProject.getProjectStatus();

        List<Client> clients = cService.findAll();
        if (clients.isEmpty()) {
            System.out.println("No clients found.");
            return;
        }

        for (int c = 0; c < clients.size(); c++) {
            System.out.println((c + 1) + " -> " + clients.get(c).getName() + " (ID: " + clients.get(c).getId() + ")");
        }

        int clientIndex = scanInt("Select a client for this project or press Enter to keep the same:");
        UUID clientId = null;

        if (clientIndex > 0 && clientIndex <= clients.size()) {
            Client selectedClient = clients.get(clientIndex - 1);
            clientId = selectedClient.getId();
        } else {
            clientId = existingProject.getClientId().getId();
        }
        ProjectDto dto = new ProjectDto(name, profitMargin, totalCost, projectStatus, clientId);
        service.update(dto, existingProject.getId());

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


    public void delete() {
        Scanner scanner = new Scanner(System.in);
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

    }
}

