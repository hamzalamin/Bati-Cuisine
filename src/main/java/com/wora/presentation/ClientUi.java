package com.wora.presentation;

import com.wora.models.dtos.ClientDto;
import com.wora.models.entities.Client;
import com.wora.services.IClientService;

import java.util.Scanner;
import java.util.UUID;

public class ClientUi {
    private final IClientService service;

    public ClientUi(IClientService service) {
        this.service = service;
    }

    public void create() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Client name:");
        String name = scanner.nextLine();

        System.out.println("Client Address:");
        String address = scanner.nextLine();

        System.out.println("Client Phone:");
        String phone = scanner.nextLine();

        System.out.println("Client Professional press at 'True' if its not professional press at 'False'");
        Boolean isProfessional = scanner.nextBoolean();


        ClientDto dto = new ClientDto(
                name,
                address,
                phone,
                isProfessional
        );
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
}
