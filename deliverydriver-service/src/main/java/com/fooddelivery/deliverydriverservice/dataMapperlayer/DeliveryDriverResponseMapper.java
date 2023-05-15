package com.fooddelivery.deliverydriverservice.dataMapperlayer;


import com.fooddelivery.deliverydriverservice.datalayer.DeliveryDriver;
import com.fooddelivery.deliverydriverservice.presentationlayer.DeliveryDriverController;
import com.fooddelivery.deliverydriverservice.presentationlayer.DeliveryDriverResponseModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

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

    @AfterMapping
    default void addLinks(@MappingTarget DeliveryDriverResponseModel deliveryDriverResponseModel, DeliveryDriver deliveryDriver) {

        Link selfLink = linkTo(methodOn(DeliveryDriverController.class)
                .getDeliveryDriversById(deliveryDriverResponseModel.getDeliveryDriverId()))
                .withSelfRel();
        deliveryDriverResponseModel.add(selfLink);

        Link deliveryDriverLink = linkTo(methodOn(DeliveryDriverController.class)
                .getDeliveryDrivers())
                .withRel("allDeliveryDrivers");
        deliveryDriverResponseModel.add(deliveryDriverLink);
    }
}
