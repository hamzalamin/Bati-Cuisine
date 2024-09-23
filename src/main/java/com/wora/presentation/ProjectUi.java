package com.wora.presentation;

import com.wora.models.dtos.ProjectDto;
import com.wora.models.entities.Client;
import com.wora.models.entities.Project;
import com.wora.models.enums.ProjectStatus;
import com.wora.services.ICalculatorService;
import com.wora.services.IClientService;
import com.wora.services.IProjectService;

import java.util.*;

import static com.wora.helpers.Scanners.*;

public class ProjectUi {
    private final IProjectService service;
    private final IClientService clientService;
    private final ICalculatorService calculatingService;
    private final ClientUi clientUi;
    private final MaterialUi materialUi;
    private final WorkerUi workerUi;

    public ProjectUi(IProjectService service, IClientService clientService, ICalculatorService calculatingService, ClientUi clientUi, MaterialUi materialUi, WorkerUi workerUi) {
        this.service = service;
        this.clientService = clientService;
        this.calculatingService = calculatingService;
        this.clientUi = clientUi;
        this.materialUi = materialUi;
        this.workerUi = workerUi;
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
        UUID projectId = scanUUID("enter the UUID of Project: ");
        try {
            Project project1 = service.findById(projectId);
            System.out.println("ID: " + project1.getId()
                    + " , Name: " + project1.getProjectName()
                    + " , Profit Margin: " + project1.getProfitMargin()
                    + " , Total Cost: " + project1.getTotalCost()
                    + " , Project Status: " + project1.getProjectStatus()
                    + " , Client ID: " + project1.getClientId().getId() + " => ( Client Name: " + project1.getClientId().getName() + ")");

            Double total = calculatingService.calculateTotalForProject(project1);
            Double totalWithTva = calculatingService.calculateTotalWithTvaForProject(project1);

            System.out.println("Total Cost of Components: " + total);
            System.out.println("Total Cost of Components with TVA: " + totalWithTva);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void create() {
        String clientName = scanString("Enter the client's name to search: ");
        Client client = clientService.searchByName(clientName);

        if (client == null) {
            System.out.println("Client not found.");

            int yOrn = scanInt("Do you want to create a client? (1 -> Yes, 2 -> No): ");
            if (yOrn == 1) {
                client = clientUi.create();
                if (client == null) {
                    System.out.println("Client creation failed. Aborting project creation.");
                    return;
                }            } else {
                System.out.println("No client created. Aborting project creation.");
                return;
            }
        }

        int choice = scanInt("Client found: " + client.getName() + ". Do you want to create a project for this client? (1 -> Yes, 2 -> No): ");

        if (choice != 1) {
            System.out.println("No project created.");
            return;
        }

        String projectName = scanString("Project Name: ");
        Double profitMargin = scanDouble("Profit Margin: ");
        Double totalCost = scanDouble("Total Cost: ");
        System.out.println("Select the project status:");
        System.out.println("1 -> IN_PROGRESS");
        System.out.println("2 -> COMPLETED");
        System.out.println("3 -> CANCELLED");
        int statusChoice = scanInt("Enter the number for the status: ");
        ProjectStatus projectStatus;

        if (statusChoice < 1 || statusChoice > 3) {
            System.out.println("Invalid choice, defaulting to IN_PROGRESS.");
            projectStatus = ProjectStatus.IN_PROGRESS;
        } else {
            projectStatus = ProjectStatus.fromNumber(statusChoice);
        }

        ProjectDto dto = new ProjectDto(projectName, profitMargin, totalCost, projectStatus, client.getId());
        UUID projectId = service.create(dto);

        System.out.println(projectId);
        System.out.println("_________________________________________");
        System.out.println("Project Information");
        System.out.println("_________________________________________");
        System.out.println("Project Name: " + projectName);
        System.out.println("Profit Margin: " + profitMargin);
        System.out.println("Total Cost: " + totalCost);
        System.out.println("Project Status: " + projectStatus);
        System.out.println("Client ID: " + client.getId());
        System.out.println("Project ID: " + projectId);
        System.out.println("_________________________________________");

        System.out.println("Do you want to add materials or workers to this project?");
        System.out.println("1 -> Add Materials");
        System.out.println("2 -> Add Workers");
        System.out.println("3 -> Skip");

        int materialChoice = scanInt("Enter your choice: ");

        switch (materialChoice) {
            case 1:
                materialUi.create(projectId);
                break;
            case 2:
                workerUi.create(projectId);
                break;
            case 3:
                System.out.println("No materials or workers added.");
                break;
            default:
                System.out.println("Invalid choice, skipping.");
        }
        System.out.println("Do you want to add materials or workers to this project?");

        boolean addingMaterialsOrWorkers = true;

        while (addingMaterialsOrWorkers) {
            System.out.println("1 -> Add Materials");
            System.out.println("2 -> Add Workers");
            System.out.println("3 -> Skip");

            int materialChoice1 = scanInt("Enter your choice: ");

            switch (materialChoice1) {
                case 1:
                    materialUi.create(projectId);
                    break;
                case 2:
                    workerUi.create(projectId);
                    break;
                case 3:
                    System.out.println("No materials or workers added.");
                    addingMaterialsOrWorkers = false;
                    break;
                default:
                    System.out.println("Invalid choice, skipping.");
                    continue;
            }

//            if (materialChoice == 1 || materialChoice == 2) {
//                int continueChoice = scanInt("Do you want to add another material or worker? (1 -> Yes, 2 -> No): ");
//                if (continueChoice != 1) {
//                    addingMaterialsOrWorkers = false;
//                }
//            }
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
                    " -- Client Name: " + projects.get(p).getClientId().getName() + ")");
        }

        System.out.println("Enter the number of the project you want to update: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index < 1 || index > projects.size()) {
            System.out.println("Invalid choice!!");
            return;
        }

        Project existingProject = projects.get(index - 1);

        String name = updateString("Enter the project ", existingProject.getProjectName());
        double profitMargin = updateDouble("Enter the profit margin ", existingProject.getProfitMargin());
        double totalCost = updateDouble("Enter the profit margin ", existingProject.getTotalCost());
        System.out.println("Select the project status:");
        System.out.println("1 -> IN_PROGRESS");
        System.out.println("2 -> COMPLETED");
        System.out.println("3 -> CANCELLED");
        int statusChoice = scanInt("Enter the number for the status or press Enter to keep the same: ");
        ProjectStatus projectStatus;
        if (statusChoice < 1 || statusChoice > 3) {
            System.out.println("Invalid choice, keeping the same status.");
            projectStatus = existingProject.getProjectStatus();
        } else {
            switch (statusChoice) {
                case 1:
                    projectStatus = ProjectStatus.IN_PROGRESS;
                    break;
                case 2:
                    projectStatus = ProjectStatus.COMPLETED;
                    break;
                case 3:
                    projectStatus = ProjectStatus.CANCELLED;
                    break;
                default:
                    projectStatus = existingProject.getProjectStatus();
            }
        }

        List<Client> clients = clientService.findAll();
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

