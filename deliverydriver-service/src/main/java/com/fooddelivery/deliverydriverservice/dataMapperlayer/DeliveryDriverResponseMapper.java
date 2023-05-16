package com.fooddelivery.deliverydriverservice.dataMapperlayer;


import com.fooddelivery.deliverydriverservice.datalayer.DeliveryDriver;
import com.fooddelivery.deliverydriverservice.presentationlayer.DeliveryDriverController;
import com.fooddelivery.deliverydriverservice.presentationlayer.DeliveryDriverResponseModel;
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
public interface DeliveryDriverResponseMapper {

    @Mapping(expression = "java(deliveryDriver.getDeliveryDriverIdentifier().getDeliveryDriverId())", target = "deliveryDriverId")
    @Mapping(expression = "java(deliveryDriver.getAddress().getCountryName())", target = "countryName")
    @Mapping(expression = "java(deliveryDriver.getAddress().getStreetName())", target = "streetName")
    @Mapping(expression = "java(deliveryDriver.getAddress().getCityName())", target = "cityName")
    @Mapping(expression = "java(deliveryDriver.getAddress().getProvinceName())", target = "provinceName")
    @Mapping(expression = "java(deliveryDriver.getAddress().getPostalCode())", target = "postalCode")
    DeliveryDriverResponseModel entityToResponseModel(DeliveryDriver deliveryDriver);

    List<DeliveryDriverResponseModel> entityListToResponseModelList(List<DeliveryDriver> deliveryDrivers);

/*    @AfterMapping
    default void addLinks(@MappingTarget DeliveryDriverResponseModel model, DeliveryDriver deliveryDriver) {

        Link selfLink = linkTo(methodOn(DeliveryDriverController.class)
                .getDeliveryDriversById(model.getDeliveryDriverId()))
                .withSelfRel();
        model.add(selfLink);

        Link deliveryDriverLink = linkTo(methodOn(DeliveryDriverController.class)
                .getDeliveryDrivers())
                .withRel("allDeliveryDrivers");
        model.add(deliveryDriverLink);
    }*/

    @AfterMapping
    default void addLinks(@MappingTarget DeliveryDriverResponseModel deliveryDriverResponseModel, DeliveryDriver deliveryDriver) {

        URI baseUri = URI.create("http://localhost:8080");

        Link selfLink = Link.of(
                ServletUriComponentsBuilder
                        .fromUri(baseUri)
                        .pathSegment("api", "v1", "deliveryDrivers", deliveryDriverResponseModel.getDeliveryDriverId())
                        .toUriString(),
                "self");

        Link clientLink = Link.of(
                ServletUriComponentsBuilder
                        .fromUri(baseUri)
                        .pathSegment("api", "v1", "deliveryDrivers")
                        .toUriString(),
                "allClients");

        deliveryDriverResponseModel.add(selfLink);
        deliveryDriverResponseModel.add(clientLink);
    }
}
