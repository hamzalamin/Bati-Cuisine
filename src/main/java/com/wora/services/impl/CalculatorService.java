package com.wora.services;

import com.wora.models.dtos.MaterialDto;
import com.wora.models.dtos.WorkerDto;
import com.wora.models.entities.Component;
import com.wora.models.entities.Material;
import com.wora.models.entities.Project;
import com.wora.models.entities.Worker;
import com.wora.repositories.IComponentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CalculatorService implements ICalculatorService {
    private final IComponentRepository<Material, MaterialDto> materialRepository;
    private final IComponentRepository<Worker, WorkerDto> workerRepository;

    public CalculatorService(IComponentRepository<Material, MaterialDto> materialRepository, IComponentRepository<Worker, WorkerDto> workerRepository) {
        this.materialRepository = materialRepository;
        this.workerRepository = workerRepository;

    }

    private List<Component> getComponentsForProject(Project project) {
        UUID projectId = project.getId();

        List<Material> materials = materialRepository.findAllByProjectId(projectId);
        List<Worker> workers = workerRepository.findAllByProjectId(projectId);

        List<Component> components = new ArrayList<>();
        components.addAll(materials);
        components.addAll(workers);
        return components;
    }

    @Override
    public Double calculateTotalForProject(Project project) {
        return getComponentsForProject(project).stream()
                .mapToDouble(Component::total)
                .reduce(0.0, Double::sum);
    }

    @Override
    public Double calculateTotalWithTvaForProject(Project project) {
        return getComponentsForProject(project).stream()
                .mapToDouble(Component::totalWithTva)
                .reduce(0.0, (a, b) -> a+b);
    }
}
