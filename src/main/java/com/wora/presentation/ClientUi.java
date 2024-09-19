package com.wora.presentation;

import com.wora.models.dtos.ClientDto;
import com.wora.models.entities.Client;
import com.wora.services.IClientService;

import java.sql.SQLException;
import java.util.*;

import static com.wora.helpers.Scanners.*;

public class ClientUi {
    private final IClientService service;

    public ClientUi(IClientService service) {
        this.service = service;
    }

    public void findAll(){
        List<Client> clients = service.findAll();
        if (clients.isEmpty()) {
            System.out.println("no Clients found");
            return;
        }

        for (int c = 0; c < clients.size(); c++) {
            System.out.println((c + 1) + " -> " + " (ID: " + clients.get(c).getId() +
                    " -- name: " + clients.get(c).getName() +
                    " -- Address: " + clients.get(c).getAddress() +
                    " -- Phone: " + clients.get(c).getPhone() +
                    " -- is Professional: " + clients.get(c).getProfessional() + ")"
            );
        }
//        clients.forEach(
//                (client) -> System.out.println(client)
//        );

    }

    public void findById() {
            UUID clientId = scanUUID("Enter the UUID of Client: ");

            Optional<Client> client = service.findById(clientId);
            if (client.isPresent()) {
                Client client1 = client.get();
                System.out.println("id : " + client1.getName() + " , Name : " + client1.getName() + " , Address : " + client1.getAddress() + " , Phone: " + client1.getPhone() + " , is professional: " + client1.getProfessional());
            } else {
                System.out.println("Client withe this ID : " + clientId + " Not found!!");
            }

    }

    public void create() {
        String name = scanString("Client name:");
        String address = scanString("Client Address:");
        String phone = scanString("Client phone:");
        Boolean isProfessional = scanBoolean("Is this Client Professional ?");

        ClientDto dto = new ClientDto(
                name,
                address,
                phone,
                isProfessional
        );
        service.create(dto);

        System.out.println("_________________________________________");
        System.out.println("Client Informations");
        System.out.println("_________________________________________");
        System.out.println("Client Name: " + name);
        System.out.println("Client Address: " + address);
        System.out.println("Client Phone: " + phone);
        if (isProfessional == true) {
            System.out.println(name + " is Professional Client");
        } else {
            System.out.println(name + " is not a Professional Client");
        }
        System.out.println("_________________________________________");

    }


    public void update() {
            List<Client> clients = service.findAll();
            if (clients.isEmpty()) {
                System.out.println("no clients found");
            }
            for (int c = 0; c < clients.size(); c++) {
                System.out.println((c + 1) + " -> " + clients.get(c).getName() + " (ID: " + clients.get(c).getId() + ")");
            }
            int index = scanInt("Enter the number that you want to update: ");
            if (index < 1 || index > clients.size()) {
                System.out.println("invalid choice !!");
            }

            Client existClient = clients.get(index - 1);
            String name = scanString("enter the client name or press to keep it the same: ");
            if (name.isEmpty()) {
                name = existClient.getName();
            }

            String address = scanString("enter the client address or press to keep it the same: ");
            if (address.isEmpty()) {
                address = existClient.getName();
            }

            String phone = scanString("enter the client phone or press to keep it the same: ");
            if (phone.isEmpty()) {
                phone = existClient.getName();
            }

            Boolean isProfessional = scanBoolean("Is this client Professional :");
            if (isProfessional == null) {
                isProfessional = existClient.getProfessional();
            }

            ClientDto dto = new ClientDto(
                    name,
                    address,
                    phone,
                    isProfessional
            );
            service.update(dto, existClient.getId());

            System.out.println("_________________________________________");
            System.out.println("Client Informations");
            System.out.println("_________________________________________");
            System.out.println("Client Name: " + name);
            System.out.println("Client Address: " + address);
            System.out.println("Client Phone: " + phone);
            if (isProfessional == true) {
                System.out.println(name + " is Professional Client");
            } else {
                System.out.println(name + " is not a Professional Client");
            }
            System.out.println("_________________________________________");


    }


    public void delete() {
            List<Client> clients = service.findAll();
            if (clients.isEmpty()) {
                System.out.println("no clients found");
            }
            for (int c = 0; c < clients.size(); c++) {
                System.out.println((c + 1) + " -> " + clients.get(c).getName() + " (ID: " + clients.get(c).getId() + ")");
            }
            System.out.println();

            int index = scanInt("Enter the number that you want to update: ");
            if (index < 1 || index > clients.size()) {
                System.out.println("invalid choice !!");
            }

            Client existClient = clients.get(index - 1);
            service.delete(existClient.getId());
            System.out.println("client deleted Successfully");

    }

    public Client searchByName() {
        String name = scanString("Search by Name: ");

        Client client = service.searchByName(name);
        if (client != null) {
            System.out.println("id : " + client.getId() + " , Name : " + client.getName() + " , Address : " + client.getAddress() + " , Phone: " + client.getPhone() + " , is professional: " + client.getProfessional());
        } else {
            System.out.println("Client withe this ID : " + name + " Not found!!");
        }
        return client;
    }

}


