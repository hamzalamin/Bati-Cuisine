package com.wora.presentation;

import com.wora.models.dtos.MaterialDto;
import com.wora.models.entities.Material;
import com.wora.models.enums.ComponentType;
import com.wora.services.IComponentService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import java.util.UUID;

import static com.wora.helpers.Scanners.*;

public class MaterialUi {
    private final IComponentService service;

    public MaterialUi(IComponentService service) {
        this.service = service;
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

    public void create() {
        Double tva = scanDouble("Material TVA: ");

        ComponentType componentType = ComponentType.valueOf(scanString("Component type"));

        UUID projectId = scanUUID("Project ID: ");
        double quantity = scanDouble("Quantity: ");
        double unitCost = scanDouble("Unit Cost: ");
        double transportCost = scanDouble("transport Cost: ");
        double qualityCoefficient = scanDouble("Quality Coefficient: ");
        MaterialDto dto = new MaterialDto(
                tva,
                componentType,
                projectId,
                unitCost,
                transportCost,
                quantity,
                qualityCoefficient
        );
        service.create(dto);

        System.out.println("Material created successfully.");
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

        double tva = scanDouble("Enter new TVA or press Enter to keep it the same: ");
        String componentTypeStr = scanString("Enter new component type or press enter to keep it the same: ");
        ComponentType componentType = componentTypeStr.isEmpty() ? existingMaterial.getComponentType() : ComponentType.valueOf(componentTypeStr.toUpperCase());

        String projectIdStr = scanString("Enter new project id or press enter to keep it the same: ");
        UUID projectId = projectIdStr.isEmpty() ? existingMaterial.getProjectId().getId() : UUID.fromString(projectIdStr);

        double quantity = scanDouble("Enter new Quantity or press Enter to keep it the same: ");

        double unitCost = scanDouble("Enter new Unit Cost or press Enter to keep it the same: ");

        double transportCost = scanDouble("Enter new Transport Cost or press Enter to keep it the same: ");

        double qualityCoefficient = scanDouble("Enter new Quality Coefficient or press Enter to keep it the same: ");

        MaterialDto dto = new MaterialDto(
                tva,
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