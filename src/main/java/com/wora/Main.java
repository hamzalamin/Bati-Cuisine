package com.wora;

import com.wora.presentation.*;
import com.wora.repositories.ClientRepository;
import com.wora.repositories.IClientRepository;
import com.wora.repositories.IProjectRepository;
import com.wora.repositories.ProjectRepository;
import com.wora.services.ClientService;
import com.wora.services.IClientService;
import com.wora.services.IProjectService;
import com.wora.services.ProjectService;

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

        MainMenu mainMenu = new MainMenu(clientMenu, projectMenu);
        clientMenu.setMainMenu(mainMenu);
        mainMenu.showMenu();
    }
}