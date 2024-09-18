package com.wora;

import com.wora.mapper.impl.WorkerMapper;
import com.wora.models.dtos.WorkerDto;
import com.wora.models.entities.Worker;
import com.wora.presentation.*;
import com.wora.presentation.menus.ClientMenu;
import com.wora.presentation.menus.ProjectMenu;
import com.wora.presentation.menus.WorkerMenu;
import com.wora.repositories.IComponentRepository;
import com.wora.repositories.impl.ClientRepository;
import com.wora.repositories.IClientRepository;
import com.wora.repositories.IProjectRepository;
import com.wora.repositories.impl.ComponentRepository;
import com.wora.repositories.impl.ProjectRepository;
import com.wora.repositories.impl.WorkerRepository;
import com.wora.services.IComponentService;
import com.wora.services.impl.ClientService;
import com.wora.services.IClientService;
import com.wora.services.IProjectService;
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
        WorkerMenu workerMenu = new WorkerMenu(workerUi);

        MainMenu mainMenu = new MainMenu(clientMenu, projectMenu, workerMenu);
        clientMenu.setMainMenu(mainMenu);
        projectMenu.setMainMenu(mainMenu);
        workerMenu.setMainMenu(mainMenu);
        mainMenu.showMenu();
    }
}