package com.wora.presentation;

import com.wora.models.dtos.ClientDto;
import com.wora.models.entities.Client;
import com.wora.services.IClientService;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;

public class ClientUi {
    private final IClientService service;

    public ClientUi(IClientService service) {
        this.service = service;
    }

    public void findAll() throws SQLException {
        List<Client> clients = service.findAll();
        if(clients.isEmpty()){
            System.out.println("no Clients found");
            return;
        }

        for (int c = 0; c < clients.size(); c++){
            System.out.println((c + 1 ) + " -> "+ " (ID: " +  clients.get(c).getId() +
                    " -- name: " + clients.get(c).getName() +
                    " -- Address: " +  clients.get(c).getAddress() +
                    " -- Phone: " + clients.get(c).getPhone() +
                    " -- is Professional: " + clients.get(c).getProfessional() + ")"
            );
        }
//        clients.forEach(
//                (client) -> System.out.println(client)
//        );

    }

    public void findById(){
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the UUID of Client: ");
            UUID clientId = UUID.fromString(scanner.next());

            Optional<Client> client = service.findById(clientId);
            if (client.isPresent()){
                Client client1 = client.get();
                System.out.println("id : " + client1.getName() + " , Name : " +client1.getName()+ " , Address : " +client1.getAddress()+ " , Phone: " +client1.getPhone()+ " , is professional: " + client1.getProfessional());
            } else {
                System.out.println("Client withe this ID : " + clientId + " Not found!!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void create() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Client name:");
        String name = scanner.nextLine().trim();
        while (name.isEmpty()) {
            System.out.println("Client name is important:");
            name = scanner.nextLine().trim();
        }

        System.out.println("Client Address:");
        String address = scanner.nextLine().trim();
        while (address.isEmpty()){
            System.out.println("Client Address is important:");
            address = scanner.nextLine().trim();
        }

        System.out.println("Client Phone:");
        String phone = scanner.nextLine().trim();
        while (phone.isEmpty()){
            System.out.println("Client Address is important:");
            phone = scanner.nextLine().trim();
        }
        System.out.println("Client Professional press at 'True' if its not professional press at 'False'");
        Boolean isProfessional = scanner.nextBoolean();
        while (isProfessional == null){
            System.out.println("Client Professional press at 'True' if its not professional press at 'False'");
            isProfessional = scanner.nextBoolean();
        }
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
        if (isProfessional == true){
            System.out.println(name + " is Professional Client");
        } else {
            System.out.println(name + " is not a Professional Client");
        }
        System.out.println("_________________________________________");

    }


    public void update(){
        Scanner scanner = new Scanner(System.in);
        try {
            List<Client> clients =  service.findAll();
            if (clients.isEmpty()){
                System.out.println("no clients found");
            }
            for (int c = 0; c < clients.size(); c++){
                System.out.println((c + 1 ) + " -> " + clients.get(c).getName() + " (ID: " +  clients.get(c).getId() + ")");
            }
            System.out.println("Enter the number that you want to update: ");
            int index = scanner.nextInt();
            if (index < 1 || index > clients.size()){
                System.out.println("invalid choice !!");
            }

            Client existClient = clients.get(index - 1);

            System.out.println("enter the client name or press to keep it the same: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()){
                name = existClient.getName();
            }

            System.out.println("enter the client address or press to keep it the same: ");
            String address = scanner.nextLine().trim();
            if (address.isEmpty()){
                address = existClient.getName();
            }

            System.out.println("enter the client phone or press to keep it the same: ");
            String phone = scanner.nextLine().trim();
            if (phone.isEmpty()){
                phone = existClient.getName();
            }

            System.out.println("if it is professional write 'true' if not write 'false': ");
            Boolean isProfessional = scanner.nextBoolean();
            if (isProfessional == null){
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
            if (isProfessional == true){
                System.out.println(name + " is Professional Client");
            } else {
                System.out.println(name + " is not a Professional Client");
            }
            System.out.println("_________________________________________");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public void delete(){
        Scanner scanner = new Scanner(System.in);
        try {
            List<Client> clients =  service.findAll();
            if (clients.isEmpty()){
                System.out.println("no clients found");
            }
            for (int c = 0; c < clients.size(); c++){
                System.out.println((c + 1 ) + " -> " + clients.get(c).getName() + " (ID: " +  clients.get(c).getId() + ")");
            }
            System.out.println("Enter the number that you want to update: ");
            int index = scanner.nextInt();
            if (index < 1 || index > clients.size()){
                System.out.println("invalid choice !!");
            }

            Client existClient = clients.get(index - 1);
            service.delete(existClient.getId());
            System.out.println("client deleted Successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


