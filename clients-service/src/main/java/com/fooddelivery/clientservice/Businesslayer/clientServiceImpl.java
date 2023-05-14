package com.fooddelivery.clientservice.Businesslayer;


import com.fooddelivery.clientservice.Datalayer.Address;
import com.fooddelivery.clientservice.Datalayer.Client;
import com.fooddelivery.clientservice.Datalayer.ClientRepository;
import com.fooddelivery.clientservice.dataMapperlayer.ClientRequestMapper;
import com.fooddelivery.clientservice.dataMapperlayer.ClientResponseMapper;
import com.fooddelivery.clientservice.presentationlayer.ClientRequestModel;
import com.fooddelivery.clientservice.presentationlayer.ClientResponseModel;
import com.fooddelivery.clientservice.utils.exceptions.DuplicateUserNameException;
import com.fooddelivery.clientservice.utils.exceptions.InvalidInputException;
import com.fooddelivery.clientservice.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class clientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private ClientResponseMapper clientResponseMapper;
    private ClientRequestMapper clientRequestMapper;


    public clientServiceImpl(ClientRepository clientRepository, ClientResponseMapper clientResponseMapper, ClientRequestMapper clientRequestMapper) {
        this.clientRepository = clientRepository;
        this.clientResponseMapper = clientResponseMapper;
        this.clientRequestMapper = clientRequestMapper;
    }

/*    public clientServiceImpl(ClientRepository clientRepository, ClientResponseMapper clientResponseMapper) {
        this.clientRepository = clientRepository;
        this.clientResponseMapper = clientResponseMapper;
    }*/

    @Override
    public List<ClientResponseModel> getAllClients() {
        return clientResponseMapper.entityListToResponseModelList(clientRepository.findAll());
    }

/*
    @Override
    public Client getClientsById(String clientId) {
        return clientRepository.findByClientIdentifier_ClientId(clientId);
    }
*/

    @Override
    public ClientResponseModel getClientById(String clientId) {
        return clientResponseMapper.entityToResponseModel(clientRepository.findByClientIdentifier_ClientId(clientId));
    }


/*    @Override
    public Client addNewClient(Client newClient) {

        try{
        return clientRepository.save(newClient);
        }
        catch(Exception ex){
            if(ex.getMessage().contains("constraint [user_name]") ||
                    ex.getCause().toString().contains("ConstraintViolationException")){
                throw new DuplicateUserNameException("An account already contains username:" + newClient.getUserName() + " Please Choose a different one. " );
            }
            throw new InvalidInputException("An unknown error has occurred");
        }

    }*/

    @Override
    public ClientResponseModel addNewClient(ClientRequestModel clientRequestModel) {
        Client client = clientRequestMapper.entityToRequestModel(clientRequestModel);
        Address address = new Address(clientRequestModel.getCountryName(), clientRequestModel.getStreetName(), clientRequestModel.getCityName(), clientRequestModel.getProvinceName(), clientRequestModel.getPostalCode());

        client.setAddress(address);
        Client newClient = clientRepository.save(client);
        ClientResponseModel clientResponseModel = clientResponseMapper.entityToResponseModel(newClient);
        return clientResponseModel;
    }

    @Override
    public ClientResponseModel updateExistingClients(ClientRequestModel clientRequestModel, String clientId) {
        Client client = clientRequestMapper.entityToRequestModel(clientRequestModel);
        Client existingClients = clientRepository.findByClientIdentifier_ClientId(clientId);
        if (existingClients == null){
            throw new NotFoundException("Client with id: " + clientId +" not found");
        }
        client.setId(existingClients.getId());
        client.setClientIdentifier(existingClients.getClientIdentifier());
        Address  address = new Address(clientRequestModel.getCountryName(), clientRequestModel.getStreetName(), clientRequestModel.getCityName(), clientRequestModel.getProvinceName(), clientRequestModel.getPostalCode());
        client.setAddress(address);
        Client updatedClient = clientRepository.save(client);
        ClientResponseModel clientResponseModel = clientResponseMapper.entityToResponseModel(updatedClient);
        return clientResponseModel;
    }

/*    @Override
    public Client updateExistingClients(Client client, String clientId) {
        Client existingClient = clientRepository.findByClientIdentifier_ClientId(clientId);

        if (existingClient == null){
            throw new NotFoundException("Client with id: " + clientId +" not found");
        }

        client.setId(existingClient.getId());
        client.setClientIdentifier(existingClient.getClientIdentifier());

        return clientRepository.save(client);
    }*/

    @Override
    public void deleteExistingClient(String clientId) {
        Client existingClient = clientRepository.findByClientIdentifier_ClientId(clientId);

        if (existingClient == null){
            throw new NotFoundException("Client with id: " + clientId +" not found");
        }
        clientRepository.delete(existingClient);
    }
}
