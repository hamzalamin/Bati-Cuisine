package com.wora;

import com.wora.presentation.ClientMenu;
import com.wora.presentation.ClientUi;
import com.wora.presentation.MainMenu;
import com.wora.repositories.ClientRepository;
import com.wora.repositories.IClientRepository;
import com.wora.services.ClientService;
import com.wora.services.IClientService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        IClientRepository clientRepository = new ClientRepository();
        IClientService clientService = new ClientService(clientRepository);
        ClientUi clientUi = new ClientUi(clientService);
        ClientMenu clientMenu = new ClientMenu(clientUi);

        MainMenu mainMenu = new MainMenu(clientMenu);
        clientMenu.setMainMenu(mainMenu);
        mainMenu.showMenu();
    }
}