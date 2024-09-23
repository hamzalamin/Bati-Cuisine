package com.wora.presentation;

import com.wora.models.dtos.EstimateDto;
import com.wora.models.entities.Estimate;
import com.wora.models.entities.Project;
import com.wora.services.ICalculatorService;
import com.wora.services.IEstimateService;
import com.wora.services.IProjectService;

import java.time.LocalDateTime;
import java.util.List;
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
                        " -- Issue Date: " + estimates.get(e).getIssueDate() +
                        " -- Validity Date: " + estimates.get(e).getValidityDate() + ")" +
                        " -- Project Name: " + estimates.get(e).getProjectId().getProjectName() + ")" +
                        " -- Client Name: " + estimates.get(e).getProjectId().getClientId().getName() + ")"

                );
            }
        }
    }

    public void findById() {
        UUID estimateId = scanUUID("Enter the Id of estimate: ");
        try {
            Estimate estimate = service.findById(estimateId);
            if (estimate != null) {
                System.out.println(
                        "ID: " + estimate.getId()
                                + " , Estimated Amount: " + estimate.getEstimatedAmount()
                                + " , Issue date: " + estimate.getIssueDate()
                                + " , Validity date: " + estimate.getValidityDate()
                                + " , is accept : " + estimate.getAccept()
                                + " , Project Name: " + estimate.getProjectId().getProjectName()
                                + " , Client name: " + estimate.getProjectId().getClientId().getName()
                );
            } else {
                System.out.println("no estimate found with this Id " + estimateId);
            }
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
                LocalDateTime issueDate = scanDate("Enter the issue date (yyyy-MM-dd):", "yyyy-MM-dd").atStartOfDay();
                LocalDateTime validityDate = scanDate("Enter the validity date (yyyy-MM-dd):", "yyyy-MM-dd").atStartOfDay();
                if (validityDate.isAfter(issueDate)) {

                }
                Boolean isAccept = scanBoolean("Is this estimate accepted (y/n): ");
                EstimateDto dto = new EstimateDto(estimatedAmount, issueDate, validityDate, isAccept, projectId.getId());
                if (isAccept == true) {
                    service.create(dto);
                    System.out.println("Estimate created successfully!");

                    System.out.println("_________________________________________");
                    System.out.println("Estimate Information");
                    System.out.println("_________________________________________");
                    System.out.println("Estimated amount: " + estimatedAmount);
                    System.out.println("Issue Date: " + issueDate);
                    System.out.println("Validity Date: " + validityDate);
                    System.out.println("is accept: " + isAccept);
                    System.out.println("Project Name: " + projectId.getProjectName());
                    System.out.println("_________________________________________");
                } else {
                    System.out.println("Estimate not created because is not accepted !");

                    System.out.println("_________________________________________");
                    System.out.println("Estimate Information");
                    System.out.println("_________________________________________");
                    System.out.println("Estimated amount: " + estimatedAmount);
                    System.out.println("Issue Date: " + issueDate);
                    System.out.println("Validity Date: " + validityDate);
                    System.out.println("is accept: " + isAccept);
                    System.out.println("Project Name: " + projectId.getProjectName());
                    System.out.println("_________________________________________");
                }

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

        Double estimatedAmount = updateDouble("Enter the estimated amount: ", existingEstimate.getEstimatedAmount());
        LocalDateTime issueDate = updateDate("Enter the issue date (yyyy-MM-dd): ", "yyyy-MM-dd", existingEstimate.getIssueDate());
        LocalDateTime validityDate = updateDate("Enter the validity date (yyyy-MM-dd)", "yyyy-MM-dd", existingEstimate.getValidityDate());
        Boolean isAccept = updateBoolean("Is this estimate accepted (y/n) ", existingEstimate.getAccept());

        List<Project> projects = projectService.findAll();
        Project projectId = null;
        if (projects.isEmpty()) {
            System.out.println("No projects found.");
        } else {
            for (int c = 0; c < projects.size(); c++) {
                System.out.println((c + 1) + " -> " + projects.get(c).getProjectName() + " (ID: " + projects.get(c).getId() + ")");
            }

            int index1 = scanInt("Select a project for this estimate: ");

            projectId = null;

            if (index1 < 1 || index1 > projects.size()) {
                System.out.println("Invalid choice!!");
            } else {
                projectId = projects.get(index1 - 1);
            }

        }
        EstimateDto dto = new EstimateDto(estimatedAmount, issueDate, validityDate, isAccept, projectId.getId());
        service.update(dto, existingEstimate.getId());

        System.out.println("Estimate updated successfully!");
        System.out.println("_________________________________________");
        System.out.println("Estimate Information");
        System.out.println("_________________________________________");
        System.out.println("Estimated amount: " + estimatedAmount);
        System.out.println("Issue Date: " + issueDate);
        System.out.println("Validity Date: " + validityDate);
        System.out.println("is accept: " + isAccept);
        System.out.println("Project ID: " + projectId.getId());
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

    public void findClientEstimate() {
        UUID userId = scanUUID("Enter the Id of User: ");
        List<Estimate> estimates = service.findClientEstimate(userId);
        if (estimates.isEmpty()) {
            System.out.println("No estimate found");
            return;
        } else {
            for (int e = 0; e < estimates.size(); e++) {
                System.out.println((e + 1) + " -> " + " (ID: " + estimates.get(e).getId() +
                        " -- Client Name: " + estimates.get(e).getProjectId().getClientId().getName() +
                        " -- Project Name: " + estimates.get(e).getProjectId().getProjectName() +
                        " -- Estimated amount: " + estimates.get(e).getEstimatedAmount() +
                        " -- Issue Date: " + estimates.get(e).getIssueDate() +
                        " -- Validity Date: " + estimates.get(e).getValidityDate() + ")" +
                        " -- Accepted: " + estimates.get(e).getAccept() + ")"
                );
            }
        }
    }

}
