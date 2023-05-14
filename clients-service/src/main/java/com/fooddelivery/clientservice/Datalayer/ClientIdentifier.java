package com.fooddelivery.clientservice.Datalayer;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class ClientIdentifier {

    private String clientId;

    public ClientIdentifier(){
        this.clientId = UUID.randomUUID().toString();
    }

    public String getClientId() {
        return clientId;
    }
}
