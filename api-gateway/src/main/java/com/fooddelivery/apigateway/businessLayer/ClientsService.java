package com.fooddelivery.apigateway.businessLayer;

import com.fooddelivery.apigateway.presentationLayer.ClientRequestModel;
import com.fooddelivery.apigateway.presentationLayer.ClientResponseModel;

public interface ClientsService {

    ClientResponseModel getClient(String clientId);

    ClientResponseModel[] getAllClientsAggregate();

    ClientResponseModel addClient(ClientRequestModel clientRequestModel);
    void updateClient(String clientId, ClientRequestModel clientRequestModel);
    void deleteClient(String clientId);

}
