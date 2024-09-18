package com.wora.presentation;

import com.wora.models.dtos.WorkerDto;
import com.wora.models.entities.Worker;
import com.wora.models.enums.ComponentType;
import com.wora.services.IComponentService;
import java.sql.SQLException;
import java.util.*;

public class WorkerUi {
    private final IComponentService service;

    public WorkerUi(IComponentService service) {
        this.service = service;
    }

    public void findAll() throws SQLException {
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
                    " | Project ID: " + worker.getProjectId() +
                    " | Hourly Rate: " + worker.getHourlyRate() +
                    " | Productivity: " + worker.getWorkerProductivity() +
                    " | Work Hours: " + worker.getWorkHours());
        }
    }

    public void findById() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the UUID of the worker: ");
            UUID workerId = UUID.fromString(scanner.nextLine());

            Optional<Worker> worker = service.findById(workerId);
            if (worker.isPresent()) {
                Worker worker1 = worker.get();
                System.out.println("ID: " + worker1.getId() +
                        " | TVA: " + worker1.getTva() +
                        " | Component Type: " + worker1.getComponentType() +
                        " | Project ID: " + worker1.getProjectId() +
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

    public void create() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Worker TVA: ");
            double tva = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Component Type: ");
            ComponentType componentType = ComponentType.valueOf(scanner.nextLine().toUpperCase().trim());

            System.out.print("Project ID: ");
            UUID projectId = UUID.fromString(scanner.nextLine());

            System.out.print("Hourly Rate: ");
            double hourlyRate = scanner.nextDouble();

            System.out.print("Productivity: ");
            double productivity = scanner.nextDouble();

            scanner.nextLine();
            System.out.print("Work Hours (HH,mm): ");
            double workHour = scanner.nextDouble();

            WorkerDto dto = new WorkerDto(
                    tva,
                    componentType,
                    projectId,
                    hourlyRate,
                    workHour,
                    productivity
            );
            service.create(dto);

            System.out.println("_________________________________________");
            System.out.println("Worker Information:");
            System.out.println("_________________________________________");
            System.out.println("TVA: " + tva);
            System.out.println("Component Type: " + componentType);
            System.out.println("Project ID: " + projectId);
            System.out.println("Hourly Rate: " + hourlyRate);
            System.out.println("Productivity: " + productivity);
            System.out.println("Work Hours: " + workHour);
            System.out.println("_________________________________________");
        }
    }

    public void update() {
        try (Scanner scanner = new Scanner(System.in)) {
            List<Worker> workers = service.findAll();
            if (workers.isEmpty()) {
                System.out.println("No workers found.");
                return;
            }

            for (int i = 0; i < workers.size(); i++) {
                Worker worker = workers.get(i);
                System.out.println((i + 1) + " -> Name: " + worker.getId() + " (ID: " + worker.getId() + ")");
            }

            System.out.print("Enter the number of the worker to update: ");
            int index = scanner.nextInt();
            scanner.nextLine();
            if (index < 1 || index > workers.size()) {
                System.out.println("Invalid choice.");
                return;
            }

            Worker existingWorker = workers.get(index - 1);

            System.out.print("Enter new TVA or press Enter to keep it the same: ");
            double tva = scanner.hasNextDouble() ? scanner.nextDouble() : existingWorker.getTva();
            scanner.nextLine();

            System.out.print("Enter new Component Type or press Enter to keep it the same: ");
            ComponentType componentType = ComponentType.valueOf(scanner.nextLine().toUpperCase().trim());
            if (componentType != null) componentType = existingWorker.getComponentType();

            System.out.print("Enter new Project ID or press Enter to keep it the same: ");
            UUID projectId = scanner.hasNextLine() ? UUID.fromString(scanner.nextLine().trim()) : existingWorker.getProjectId().getId();

            System.out.print("Enter new Hourly Rate or press Enter to keep it the same: ");
            double hourlyRate = scanner.hasNextDouble() ? scanner.nextDouble() : existingWorker.getHourlyRate();
            scanner.nextLine();

            System.out.print("Enter new Productivity or press Enter to keep it the same: ");
            double productivity = scanner.hasNextDouble() ? scanner.nextDouble() : existingWorker.getWorkerProductivity();
            scanner.nextLine();

            System.out.print("Enter new Work Hours or press Enter to keep it the same (HH,mm): ");
            double workHour = scanner.hasNextDouble() ? scanner.nextDouble() : existingWorker.getWorkHours();
            scanner.nextLine();

            WorkerDto dto = new WorkerDto(
                    tva,
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete() {
        try (Scanner scanner = new Scanner(System.in)) {
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
            int index = scanner.nextInt();
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
