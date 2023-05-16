package com.fooddelivery.clientservice.dataMapperlayer;


import com.fooddelivery.clientservice.Datalayer.Client;
import com.fooddelivery.clientservice.presentationlayer.ClientController;
import com.fooddelivery.clientservice.presentationlayer.ClientResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

/*    @AfterMapping
    default void addLinks(@MappingTarget ClientResponseModel clientResponseModel, Client client) {

        Link selfLink = linkTo(methodOn(ClientController.class)
                .getClientByClientId(clientResponseModel.getClientId()))
                .withSelfRel();
        clientResponseModel.add(selfLink);

        Link clientLink = linkTo(methodOn(ClientController.class)
                .getClients())
                .withRel("allClients");
        clientResponseModel.add(clientLink);
    }*/

    @AfterMapping
    default void addLinks(@MappingTarget ClientResponseModel clientResponseModel, Client client) {

        URI baseUri = URI.create("http://localhost:8080");

        Link selfLink = Link.of(
                ServletUriComponentsBuilder
                        .fromUri(baseUri)
                        .pathSegment("api", "v1", "clients", clientResponseModel.getClientId())
                        .toUriString(),
                        "self");

        Link clientLink = Link.of(
                ServletUriComponentsBuilder
                        .fromUri(baseUri)
                        .pathSegment("api", "v1", "clients")
                        .toUriString(),
                "allClients");

        clientResponseModel.add(selfLink);
        clientResponseModel.add(clientLink);
    }


}