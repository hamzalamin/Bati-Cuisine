package com.wora.presentation;

import com.wora.models.dtos.EstimateDto;
import com.wora.models.entities.Client;
import com.wora.models.entities.Estimate;
import com.wora.models.entities.Project;
import com.wora.services.ICalculatorService;
import com.wora.services.IComponentService;
import com.wora.services.IEstimateService;
import com.wora.services.IProjectService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static com.wora.helpers.Scanners.*;

public class EstimateUi {
    private final IEstimateService service;
    private final ICalculatorService calculatingService;
    private final IProjectService projectService;


    public EstimateUi(IEstimateService service, ICalculatorService calculatingService, IProjectService projectService) {
        this.service = service;
        this.calculatingService = calculatingService;
        this.projectService = projectService;
    }

    public void findAll() {
        List<Estimate> estimates = service.findAll();
        if (estimates.isEmpty()) {
            System.out.println("No estimate found");
            return;
        } else {
            for (int e = 0; e < estimates.size(); e++) {
                System.out.println((e + 1) + " -> " + " (ID: " + estimates.get(e).getId() +
                        " -- Estimated amount: " + estimates.get(e).getEstimatedAmount() +
                        " -- Profit margin: " + estimates.get(e).getIssueDate() +
                        " -- total cost: " + estimates.get(e).getValidityDate() + ")" +
                        " -- Project Status: " + estimates.get(e).getValidityDate() + ")" +
                        " -- Client: " + estimates.get(e).getProjectId().getProjectName() + ")" +
                        " -- Client: " + estimates.get(e).getProjectId().getClientId().getName() + ")"

                );
            }
        }
    }

    public void findById() {
        UUID estimateId = scanUUID("Enter the Id of estimate: ");
        try {
            Estimate estimate = service.findById(estimateId);
            System.out.println(
                    "ID: " + estimate.getId()
                            + " , Estimated Amount: " + estimate.getEstimatedAmount()
                            + " , Issue date: " + estimate.getIssueDate()
                            + " , Validity date: " + estimate.getValidityDate()
                            + " , is accept : " + estimate.getAccept()
                            + " , Project Name: " + estimate.getProjectId().getProjectName()
                            + " , Client name: " + estimate.getProjectId().getClientId().getName()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void create() {
        List<Project> projects = projectService.findAll();
        if (projects.isEmpty()) {
            System.out.println("No projects found.");
        } else {
            for (int c = 0; c < projects.size(); c++) {
                System.out.println((c + 1) + " -> " + projects.get(c).getProjectName() + " (ID: " + projects.get(c).getId() + ")");
            }

            int index = scanInt("Select a project for this estimate: ");
            Project projectId = null;
            Double estimatedAmount = null;

            if (index < 1 || index > projects.size()) {
                System.out.println("Invalid choice!!");
            } else {
                projectId = projects.get(index - 1);
                estimatedAmount = calculatingService.calculateTotalWithTvaForProject(projectId);
                System.out.println("The estimated amount is: " + estimatedAmount);
            }

            if (estimatedAmount != null) {
                LocalDateTime issueDate = scanDate("Enter the issue date (yyyy-MM-dd HH:mm):", "yyyy-MM-dd HH:mm");
                LocalDateTime validityDate = scanDate("Enter the validity date (yyyy-MM-dd HH:mm):", "yyyy-MM-dd HH:mm");
                Boolean isAccept = scanBoolean("Is this estimate accepted (y/n): ");
                EstimateDto dto = new EstimateDto(estimatedAmount, issueDate, validityDate, isAccept, projectId);
                service.create(dto);

                System.out.println("Estimate created successfully!");

                System.out.println("_________________________________________");
                System.out.println("Estimate Information");
                System.out.println("_________________________________________");
                System.out.println("Estimated amount: " + estimatedAmount);
                System.out.println("Issue Date: " + issueDate);
                System.out.println("Validity Date: " + validityDate);
                System.out.println("is accept: " + isAccept);
                System.out.println("Project ID: " + projectId);
                System.out.println("_________________________________________");
            } else {
                System.out.println("Estimate creation failed due to invalid project selection.");
            }
        }
    }

    public void update() {
        List<Estimate> estimates = service.findAll();
        if (estimates.isEmpty()) {
            System.out.println("No estimates found.");
            return;
        }
        for (int e = 0; e < estimates.size(); e++) {
            Estimate estimate = estimates.get(e);
            System.out.println((e + 1) + " -> " + " (ID: " + estimate.getId() +
                    " -- Estimated Amount: " + estimate.getEstimatedAmount() +
                    " -- Issue Date: " + estimate.getIssueDate() +
                    " -- Validity Date: " + estimate.getValidityDate() +
                    " -- Accepted: " + estimate.getAccept() + ")");
        }


        int index = scanInt("Enter the number of the estimate you want to update: ");

        if (index < 1 || index > estimates.size()) {
            System.out.println("Invalid choice!!");
            return;
        }

        Estimate existingEstimate = estimates.get(index - 1);

        String estimatedAmountStr = scanString("Enter the estimated amount or press Enter to keep it the same: ");
        double estimatedAmount = existingEstimate.getEstimatedAmount();
        if (!estimatedAmountStr.isEmpty()) {
            estimatedAmount = Double.parseDouble(estimatedAmountStr);
        }
        LocalDateTime issueDate = scanDate("Enter the issue date (yyyy-MM-dd HH:mm) or press Enter to keep it the same:", "yyyy-MM-dd HH:mm");
        if (issueDate == null) {
            issueDate = existingEstimate.getIssueDate();
        }

        LocalDateTime validityDate = scanDate("Enter the validity date (yyyy-MM-dd HH:mm) or press Enter to keep it the same:", "yyyy-MM-dd HH:mm");
        if (validityDate == null) {
            validityDate = existingEstimate.getValidityDate();
        }

        Boolean isAccept = scanBoolean("Is this estimate accepted (y/n) or press Enter to keep it the same: ");
        if (isAccept == null) {
            isAccept = existingEstimate.getAccept();
        }


        EstimateDto dto = new EstimateDto(estimatedAmount, issueDate, validityDate, isAccept, existingEstimate.getProjectId());
        service.update(dto, existingEstimate.getId());

        System.out.println("Estimate updated successfully!");
        System.out.println("_________________________________________");
        System.out.println("Estimate Information");
        System.out.println("_________________________________________");
        System.out.println("Estimated amount: " + estimatedAmount);
        System.out.println("Issue Date: " + issueDate);
        System.out.println("Validity Date: " + validityDate);
        System.out.println("is accept: " + isAccept);
        System.out.println("Project ID: " +  existingEstimate.getProjectId());
        System.out.println("_________________________________________");
    }

    public void delete() {
        List<Estimate> estimates = service.findAll();
        if (estimates.isEmpty()) {
            System.out.println("No estimates found.");
            return;
        }


        for (int e = 0; e < estimates.size(); e++) {
            Estimate estimate = estimates.get(e);
            System.out.println((e + 1) + " -> " + " (ID: " + estimate.getId() +
                    " -- Estimated Amount: " + estimate.getEstimatedAmount() +
                    " -- Issue Date: " + estimate.getIssueDate() +
                    " -- Validity Date: " + estimate.getValidityDate() +
                    " -- Accepted: " + estimate.getAccept() + ")");
        }

        int index = scanInt("Enter the number of the estimate you want to delete: ");


        if (index < 1 || index > estimates.size()) {
            System.out.println("Invalid choice!!");
            return;
        }

        Estimate existingEstimate = estimates.get(index - 1);
        service.delete(existingEstimate.getId());
        System.out.println("Estimate deleted successfully.");
    }

}
