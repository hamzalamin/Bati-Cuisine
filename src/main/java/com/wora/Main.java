package com.wora;

import com.wora.mapper.impl.MaterialMapper;
import com.wora.mapper.impl.WorkerMapper;
import com.wora.models.dtos.MaterialDto;
import com.wora.models.dtos.WorkerDto;
import com.wora.models.entities.Material;
import com.wora.models.entities.Worker;
import com.wora.presentation.*;
import com.wora.presentation.menus.*;
import com.wora.repositories.IComponentRepository;
import com.wora.repositories.IEstimateRepository;
import com.wora.repositories.impl.*;
import com.wora.repositories.IClientRepository;
import com.wora.repositories.IProjectRepository;
import com.wora.services.*;
import com.wora.services.CalculatorService;
import com.wora.services.impl.*;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        IClientRepository clientRepository = new ClientRepository();
        IClientService clientService = new ClientService(clientRepository);
        ClientUi clientUi = new ClientUi(clientService);
        ClientMenu clientMenu = new ClientMenu(clientUi);

        WorkerMapper workerMapper = new WorkerMapper();
        IComponentRepository<Worker, WorkerDto> workerRepository = new WorkerRepository(workerMapper);
        IComponentService workerService = new WorkerService(workerRepository);
        WorkerUi workerUi = new WorkerUi(workerService);
        MenuWorker workerMenu = new MenuWorker(workerUi);

        MaterialMapper materialMapper = new MaterialMapper();
        IComponentRepository<Material,MaterialDto> materialRepository = new MaterialRepository(materialMapper);
        IComponentService materialService = new MaterialService(materialRepository);
        MaterialUi materialUi = new MaterialUi(materialService);
        MenuMaterial materialMenu = new MenuMaterial(materialUi);

        IProjectRepository projectRepository = new ProjectRepository();
        IProjectService projectService = new ProjectService(projectRepository);
        ICalculatorService calculatorService = new CalculatorService(materialRepository ,workerRepository);
        ProjectUi projectUi = new ProjectUi(projectService, clientService, calculatorService, clientUi, materialUi, workerUi);
        ProjectMenu projectMenu = new ProjectMenu(projectUi);


        IEstimateRepository estimateRepository = new EstimateRepository();
        IEstimateService estimateService = new EstimateService(estimateRepository);
        EstimateUi estimateUi = new EstimateUi(estimateService, calculatorService, projectService);
        EstimateMenu estimateMenu = new EstimateMenu(estimateUi);

        workerUi.setProjectService(projectService);
        materialUi.setProjectService((ProjectService) projectService);
        MainMenu mainMenu = new MainMenu(clientMenu, projectMenu, workerMenu, materialMenu, estimateMenu);
        clientMenu.setMainMenu(mainMenu);
        projectMenu.setMainMenu(mainMenu);
        workerMenu.setMainMenu(mainMenu);
        materialMenu.setMainMenu(mainMenu);
        estimateMenu.setMainMenu(mainMenu);
        mainMenu.showMenu();

    }
}