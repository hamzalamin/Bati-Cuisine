package com.wora.presentation;

import com.wora.models.dtos.WorkerDto;
import com.wora.models.entities.Worker;
import com.wora.models.enums.ComponentType;
import com.wora.services.IComponentService;
import com.wora.helpers.Scanners;
import com.wora.services.IProjectService;

import java.util.*;

import static com.wora.helpers.Scanners.*;

public class WorkerUi {
    private final IComponentService service;
    private IProjectService projectService;

    public WorkerUi(IComponentService service) {
        this.service = service;
    }

    public void setProjectService(IProjectService projectService) {
        this.projectService = projectService;
    }

    public void findAll() {
        List<Worker> workers = service.findAll();
        if (workers.isEmpty()) {
            System.out.println("No workers found.");
            return;
        }

        for (int i = 0; i < workers.size(); i++) {
            Worker worker = workers.get(i);
            System.out.println((i + 1) + " -> ID: " + worker.getId() +
                    " | TVA: " + worker.getTva() +
                    " | Component Type: " + worker.getComponentType() +
                    " | Project Name: " + worker.getProjectId().getProjectName() +
                    " | Project Status: " + worker.getProjectId().getProjectStatus() +
                    " | Client Name: " + worker.getProjectId().getClientId().getName() +
                    " | Client address: " + worker.getProjectId().getClientId().getAddress() +
                    " | Hourly Rate: " + worker.getHourlyRate() +
                    " | Productivity: " + worker.getWorkerProductivity() +
                    " | Work Hours: " + worker.getWorkHours());
        }
    }

    public void findById() {
        try {
            UUID workerId = UUID.fromString(scanString("Enter the UUID of the worker: "));

            Optional<Worker> worker = service.findById(workerId);
            if (worker.isPresent()) {
                Worker worker1 = worker.get();
                System.out.println("ID: " + worker1.getId() +
                        " | TVA: " + worker1.getTva() +
                        " | Component Type: " + worker1.getComponentType() +
                        " | Project Name: " + worker1.getProjectId().getProjectName() +
                        " | Project Status: " + worker1.getProjectId().getProjectStatus() +
                        " | Client Name: " + worker1.getProjectId().getClientId().getName() +
                        " | Client address: " + worker1.getProjectId().getClientId().getAddress() +
                        " | Hourly Rate: " + worker1.getHourlyRate() +
                        " | Productivity: " + worker1.getWorkerProductivity() +
                        " | Work Hours: " + worker1.getWorkHours());
            } else {
                System.out.println("Worker with ID: " + workerId + " not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void create(UUID projectId) {
        String name = scanString("Enter the name: ");
        double tva = scanDouble("Worker TVA: ");
        double hourlyRate = scanDouble("Hourly Rate: ");
        double productivity = scanDouble("Productivity: ");
        double workHour = scanDouble("Work Hours (format: HH.mm): ");

        WorkerDto dto = new WorkerDto(
                tva,
                name,
                ComponentType.WORKER,
                projectId,
                hourlyRate,
                workHour,
                productivity
        );
        service.create(dto);
        System.out.println("Worker added successfully to the project.");
    }


    public void update(UUID projectId) {
        List<Worker> workers = service.findAll();
        if (workers.isEmpty()) {
            System.out.println("No workers found.");
            return;
        }

        for (int i = 0; i < workers.size(); i++) {
            Worker worker = workers.get(i);
            System.out.println((i + 1) + " -> Name: " + worker.getId() + " (ID: " + worker.getId() + ")");
        }

        int index = scanInt("Enter the number of the worker to update: ");
        if (index < 1 || index > workers.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Worker existingWorker = workers.get(index - 1);

        String name = updateString("enter new name : ", existingWorker.getName());
        double tva = updateDouble("Enter new TVA : ", existingWorker.getTva());
        ComponentType componentType = updateEnum("Enter new Component Type: ", existingWorker.getComponentType(), ComponentType.class);
//        UUID projectId = updateUUID("Enter new Project ID : ", existingWorker.getProjectId().getId());
        double hourlyRate = updateDouble("Enter new Hourly Rate : ", existingWorker.getHourlyRate());
        double productivity = updateDouble("Enter new Productivity : ", existingWorker.getWorkerProductivity());
        double workHour = updateDouble("Enter new Work Hours  (HH.mm): ", existingWorker.getWorkHours());

        WorkerDto dto = new WorkerDto(
                tva,
                name,
                componentType,
                projectId,
                hourlyRate,
                workHour,
                productivity
        );
        service.update(dto, existingWorker.getId());

        System.out.println("_________________________________________");
        System.out.println("Updated Worker Information:");
        System.out.println("_________________________________________");
        System.out.println("TVA: " + tva);
        System.out.println("Component Type: " + componentType);
        System.out.println("Project ID: " + projectId);
        System.out.println("Hourly Rate: " + hourlyRate);
        System.out.println("Productivity: " + productivity);
        System.out.println("Work Hours: " + workHour);
        System.out.println("_________________________________________");
    }

    public void delete() {
        try {
            List<Worker> workers = service.findAll();
            if (workers.isEmpty()) {
                System.out.println("No workers found.");
                return;
            }

            for (int i = 0; i < workers.size(); i++) {
                Worker worker = workers.get(i);
                System.out.println((i + 1) + " -> Name: " + worker.getId() + " (ID: " + worker.getId() + ")");
            }

            System.out.print("Enter the number of the worker to delete: ");
            int index = Scanners.scanInt("Enter the number of the worker to delete: ");
            if (index < 1 || index > workers.size()) {
                System.out.println("Invalid choice.");
                return;
            }

            Worker existingWorker = workers.get(index - 1);
            service.delete(existingWorker.getId());
            System.out.println("Worker deleted successfully.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
