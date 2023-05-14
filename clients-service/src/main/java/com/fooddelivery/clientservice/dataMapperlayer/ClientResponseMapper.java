package com.fooddelivery.clientservice.dataMapperlayer;


import com.fooddelivery.clientservice.Datalayer.Client;
import com.fooddelivery.clientservice.presentationlayer.ClientResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientResponseMapper {


    @Mapping(expression = "java(client.getClientIdentifier().getClientId())", target = "clientId")
    @Mapping(expression = "java(client.getAddress().getCountryName())", target = "countryName")
    @Mapping(expression = "java(client.getAddress().getStreetName())", target = "streetName")
    @Mapping(expression = "java(client.getAddress().getCityName())", target = "cityName")
    @Mapping(expression = "java(client.getAddress().getProvinceName())", target = "provinceName")
    @Mapping(expression = "java(client.getAddress().getPostalCode())", target = "postalCode")
    ClientResponseModel entityToResponseModel(Client client);

    List<ClientResponseModel> entityListToResponseModelList(List<Client> clients);

}