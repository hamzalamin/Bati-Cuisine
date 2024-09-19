package com.wora;

import com.wora.mapper.impl.MaterialMapper;
import com.wora.mapper.impl.WorkerMapper;
import com.wora.models.dtos.MaterialDto;
import com.wora.models.dtos.WorkerDto;
import com.wora.models.entities.Material;
import com.wora.models.entities.Worker;
import com.wora.presentation.*;
import com.wora.presentation.menus.ClientMenu;
import com.wora.presentation.menus.MenuMaterial;
import com.wora.presentation.menus.ProjectMenu;
import com.wora.presentation.menus.MenuWorker;
import com.wora.repositories.IComponentRepository;
import com.wora.repositories.impl.*;
import com.wora.repositories.IClientRepository;
import com.wora.repositories.IProjectRepository;
import com.wora.services.IComponentService;
import com.wora.services.impl.ClientService;
import com.wora.services.IClientService;
import com.wora.services.IProjectService;
import com.wora.services.impl.MaterialService;
import com.wora.services.impl.ProjectService;
import com.wora.services.impl.WorkerService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        IClientRepository clientRepository = new ClientRepository();
        IClientService clientService = new ClientService(clientRepository);
        ClientUi clientUi = new ClientUi(clientService);
        ClientMenu clientMenu = new ClientMenu(clientUi);

        IProjectRepository projectRepository = new ProjectRepository();
        IProjectService projectService = new ProjectService(projectRepository);
        ProjectUi projectUi = new ProjectUi(projectService, clientService);
        ProjectMenu projectMenu = new ProjectMenu(projectUi);

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

        MainMenu mainMenu = new MainMenu(clientMenu, projectMenu, workerMenu, materialMenu);
        clientMenu.setMainMenu(mainMenu);
        projectMenu.setMainMenu(mainMenu);
        workerMenu.setMainMenu(mainMenu);
        materialMenu.setMainMenu(mainMenu);
        mainMenu.showMenu();

    }
}