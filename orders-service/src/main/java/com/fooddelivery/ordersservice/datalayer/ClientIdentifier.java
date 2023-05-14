package com.fooddelivery.ordersservice.datalayer;



import java.util.UUID;


public class ClientIdentifier {

    private String clientId;

    public ClientIdentifier(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }
}
