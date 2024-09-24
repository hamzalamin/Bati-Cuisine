package com.wora.presentation;

import com.wora.models.dtos.MaterialDto;
import com.wora.models.entities.Material;
import com.wora.models.entities.Project;
import com.wora.models.enums.ComponentType;
import com.wora.services.IComponentService;
import com.wora.services.IProjectService;
import com.wora.services.impl.ProjectService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import java.util.UUID;

import static com.wora.helpers.Scanners.*;

public class MaterialUi {
    private final IComponentService service;
    private IProjectService projectService;

    public MaterialUi(IComponentService service) {
        this.service = service;
    }

    public void setProjectService(ProjectService projectService){
        this.projectService = projectService;
    }

    public void findAll() {
        List<Material> materials = service.findAll();
        if (materials.isEmpty()) {
            System.out.println("No materials Found.");
            return;
        }

        for (int i = 0; i < materials.size(); i++) {
            Material material = materials.get(i);
            System.out.println((i + 1) + " -> ID: " + material.getId() +
                    " | TVA: " + material.getTva() +
                    " | Component Type: " + material.getComponentType() +
                    " | Project Name: " + material.getProjectId().getProjectName() +
                    " | Client Name: " + material.getProjectId().getClientId().getName() +
                    " | Client Phone: " + material.getProjectId().getClientId().getPhone() +
                    " | Quantity: " + material.getQuantity() +
                    " | Unit Cost: " + material.getUnitCost() +
                    " | Transport Cost: " + material.getTransportCost() +
                    " | Quality Coefficient: " + material.getQualityCoefficient());
        }

    }

    public void findById() {
        UUID materialId = scanUUID("Enter the UUID of the Material: ");

        Optional<Material> material = null;
        material = service.findById(materialId);

        if (material.isPresent()) {
            Material material1 = material.get();
            System.out.println("ID: " + material1.getId() +
                    " | TVA: " + material1.getTva() +
                    " | Component Type: " + material1.getComponentType() +
                    " | Project Name: " + material1.getProjectId().getProjectName() +
                    " | Client Name: " + material1.getProjectId().getClientId().getName() +
                    " | Client Phone: " + material1.getProjectId().getClientId().getPhone() +
                    " | Quantity : " + material1.getQuantity() +
                    " | Unit Cost: " + material1.getUnitCost() +
                    " | Transport Cost: " + material1.getTransportCost() +
                    " | Quality Coefficient: " + material1.getQualityCoefficient());
        } else {
            System.out.println("Material with this ID " + materialId + "Could not be found!!!");
        }
    }

    public void create(UUID projectId) {
        String name = scanString("enter the name: ");
        double tva = scanDouble("Material TVA: ");
        double quantity = scanDouble("Quantity: ");
        double unitCost = scanDouble("Unit Cost: ");
        double transportCost = scanDouble("Transport Cost: ");
        double qualityCoefficient = scanDouble("Quality Coefficient: ");
        UUID project = projectId;
        MaterialDto dto = new MaterialDto(
                tva,
                name,
                ComponentType.MATERIAL,
                project,
                unitCost,
                transportCost,
                quantity,
                qualityCoefficient
        );
        service.create(dto);

        System.out.println("Material added successfully to the project.");
    }


    public void update() {
        List<Material> materials = null;
        materials = service.findAll();
        if (materials.isEmpty()) {
            System.out.println("No materials found.");
            return;
        }

        for (int i = 0; i < materials.size(); i++) {
            Material material = materials.get(i);
            System.out.println((i + 1) + " -> ID: " + material.getId());
        }

        int index = scanInt("Enter the number of the material to update: ");
        if (index < 1 || index > materials.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Material existingMaterial = materials.get(index - 1);

        String name = updateString("Enter the name: ", existingMaterial.getName());
        double tva = updateDouble("Enter new TVA : ", existingMaterial.getTva());
        UUID projectId = updateUUID("Enter new project id ", existingMaterial.getProjectId().getId());
        ComponentType componentType = updateEnum("Enter new Component Type: ", existingMaterial.getComponentType(), ComponentType.class);
        double quantity = updateDouble("Enter new Quantity ", existingMaterial.getQuantity());
        double unitCost = updateDouble("Enter new Unit Cost ", existingMaterial.getUnitCost());
        double transportCost = updateDouble("Enter new Transport ", existingMaterial.getTransportCost());
        double qualityCoefficient = updateDouble("Enter new Quality ", existingMaterial.getQualityCoefficient());

        MaterialDto dto = new MaterialDto(
                tva,
                name,
                componentType,
                projectId,
                quantity,
                unitCost,
                transportCost,
                qualityCoefficient
        );
        service.update(dto, existingMaterial.getId());

        System.out.println("Material updated successfully.");
    }

    public void delete() {
        List<Material> materials = null;
        materials = service.findAll();
        if (materials.isEmpty()) {
            System.out.println("Ne materials found.");
            return;
        }

        for (int i = 0; i < materials.size(); i++) {
            Material material = materials.get(i);
            System.out.println((i + 1) + " -> ID: " + material.getId());
        }

        int index = scanInt("Enter the number of the material to delete: ");
        if (index < 1 || index > materials.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Material existingMaterial = materials.get(index - 1);

        service.delete(existingMaterial.getId());
        System.out.println("Material deleted successfully.");
    }
}