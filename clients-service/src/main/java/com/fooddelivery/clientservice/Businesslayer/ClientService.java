package com.fooddelivery.clientservice.Businesslayer;




import com.fooddelivery.clientservice.Datalayer.Client;
import com.fooddelivery.clientservice.presentationlayer.ClientRequestModel;
import com.fooddelivery.clientservice.presentationlayer.ClientResponseModel;

import java.util.List;

public interface ClientService {

List<ClientResponseModel> getAllClients();
//change all to response models
/*Client getClientsById(String clientId);*/

ClientResponseModel getClientById(String clientId);

/*Client addNewClient(Client newClient);*/
ClientResponseModel addNewClient(ClientRequestModel clientRequestModel);


ClientResponseModel updateExistingClients(ClientRequestModel clientRequestModel, String clientId);
/*Client updateExistingClients(Client client,String clientId );*/

void deleteExistingClient(String clientId);

}
